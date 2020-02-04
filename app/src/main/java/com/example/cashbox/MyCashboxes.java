package com.example.cashbox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCashboxes extends AppCompatActivity {

    ArrayList<Cashbox> cbArray;
    CashboxAdapter adapter;
    ListView cbListView;
    DatabaseReference database;
    String storePos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cashboxes);
        this.setTitle("ККТ " + getIntent().getStringExtra("title"));
        storePos = getIntent().getStringExtra("selected");
        preparing();
    }

    private void preparing()
    {
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("stores").child(getIntent().getStringExtra("parentId")).child("cashboxes");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cbArray.clear();
                for (DataSnapshot cbSnapshot : dataSnapshot.getChildren())
                {
                    String id = cbSnapshot.child("id").getValue().toString();
                    String parentId = cbSnapshot.child("parentId").getValue().toString();
                    String name = cbSnapshot.child("name").getValue().toString();
                    String model = cbSnapshot.child("model").getValue().toString();
                    String serial = cbSnapshot.child("serialNumber").getValue().toString();
                    cbArray.add(new Cashbox(id, parentId, name, model, serial));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        cbArray = new ArrayList<Cashbox>();
        cbListView = findViewById(R.id.cbListView);
        adapter = new CashboxAdapter(this, R.layout.cashboxelement, cbArray);
        cbListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_add:
                Intent newCashbox = new Intent(MyCashboxes.this, add_cashbox.class);
                newCashbox.putExtra("selected", storePos);
                startActivityForResult(newCashbox, 1);
                break;
        }
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK)
            {
                String id = database.push().getKey();
                Cashbox newCashbox = new Cashbox(id, getIntent().getStringExtra("parentId"), data.getStringExtra("name"), data.getStringExtra("model"), data.getStringExtra("serial"));
                database.child(id).setValue(newCashbox);
            }
        }
        else
        {
            if (requestCode == 2)
            {
                if (resultCode == RESULT_OK)
                {
                    database.child(data.getStringExtra("id")).setValue(new Cashbox(data.getStringExtra("id"), getIntent().getStringExtra("parentId"), data.getStringExtra("name"), data.getStringExtra("model"), data.getStringExtra("serial")));
                }
            }
        }
    }

}
