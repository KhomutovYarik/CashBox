package com.example.cashbox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllCashboxes extends AppCompatActivity {

    ArrayList<Cashbox> cbArray;
    CashboxAdapter adapter;
    ListView cbListView;
    DatabaseReference database, databaseCB;
    ArrayAdapter<String> store_adapter;
    ArrayList<String> storesList;
    ArrayList<Cashbox> [] allLists;
    ArrayList<Cashbox> cashboxes;
    Spinner storeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cashboxes);
        preparing();
    }

    private void preparing()
    {
        cashboxes = new ArrayList<>();
        storeName = findViewById(R.id.storeName);
        cbListView = findViewById(R.id.cbListView);
        storesList = new ArrayList<>();
        storesList.add("Выберите торговую точку");
        store_adapter = new CustomArrayAdapter(AllCashboxes.this,
                android.R.layout.simple_list_item_1, storesList, 0);
        store_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeName.setAdapter(store_adapter);

        cbArray = new ArrayList<>();
        adapter = new CashboxAdapter(AllCashboxes.this, R.layout.cashboxelement, cbArray);
        cbListView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("stores");

        storeName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    adapter.clear();
                    adapter.addAll(allLists[i-1]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                storesList.clear();
                cbArray.clear();
                storesList.add("Выберите торговую точку");
                int i = (int)dataSnapshot.getChildrenCount();
                allLists = new ArrayList[i];
                for (int j = 0; j < i; j++)
                {
                    allLists[j] = new ArrayList<>();
                }
                int j = 0;
                for (DataSnapshot snap1 : dataSnapshot.getChildren())
                {
                    String id = snap1.child("id").getValue().toString();
                    storesList.add(snap1.child("name").getValue().toString());
                    for (DataSnapshot snap2 : dataSnapshot.child(id).child("cashboxes").getChildren())
                    {
                        allLists[j].add(new Cashbox(null, null, snap2.child("name").getValue().toString(), snap2.child("model").getValue().toString(), snap2.child("serialNumber").getValue().toString()));
                        cashboxes.add(new Cashbox(null, null, snap2.child("name").getValue().toString(), snap2.child("model").getValue().toString(), snap2.child("serialNumber").getValue().toString()));
                    }
                    j++;
                }
                store_adapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
//                for (DataSnapshot data : dataSnapshot.getChildren())
//                {
//                    storesList.add(data.child("name").getValue().toString());
//                }
//                store_adapter.notifyDataSetChanged();

//                for (DataSnapshot cbSnapshot : dataSnapshot.getChildren())
//                {
//                    String id = cbSnapshot.child("id").getValue().toString();
//                    String parentId = cbSnapshot.child("parentId").getValue().toString();
//                    String name = cbSnapshot.child("name").getValue().toString();
//                    String model = cbSnapshot.child("model").getValue().toString();
//                    String serial = cbSnapshot.child("serialNumber").getValue().toString();
//                    cbArray.add(new Cashbox(id, parentId, name, model, serial));
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_add:
                //TODO добавление кассового аппарата
//                Intent newCashbox = new Intent(AllCashboxes.this, add_cashbox.class);
//                if (storeName.getSelectedItemId() != 0) {
//                    databaseCB = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("stores").child(getIntent().getStringExtra("parentId")).child("cashboxes");
//                    newCashbox.putExtra("selected", String.valueOf(storeName.getSelectedItemId() - 1));
//                }
//                startActivityForResult(newCashbox, 1);
                break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_add, menu);
        return true;
    }

    //TODO скопировано
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK)
            {
                String id = databaseCB.push().getKey();
                Cashbox newCashbox = new Cashbox(id, getIntent().getStringExtra("parentId"), data.getStringExtra("name"), data.getStringExtra("model"), data.getStringExtra("serial"));
                databaseCB.child(id).setValue(newCashbox);
            }
        }
        else
        {
            if (requestCode == 2)
            {
                if (resultCode == RESULT_OK)
                {
                    databaseCB.child(data.getStringExtra("id")).setValue(new Cashbox(data.getStringExtra("id"), getIntent().getStringExtra("parentId"), data.getStringExtra("name"), data.getStringExtra("model"), data.getStringExtra("serial")));
                }
            }
        }
    }
}
