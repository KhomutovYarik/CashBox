package com.example.cashbox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cashbox.ui.main.SectionsPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MyStores extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    LinearLayout storesLayout;
    ListView storeList;

    ArrayList<Store> storesArray;
    StoresAdapter adapter;
    DatabaseReference database;
    HashMap<String, ArrayList<Cashbox>> storeCashboxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stores);
        preparing();
    }

    private void preparing() {
        storeCashboxes = new HashMap<String, ArrayList<Cashbox>>();

        storesLayout = findViewById(R.id.linearLayout_forStores);
        storeList = findViewById(R.id.myStoresList);
        storesArray = new ArrayList<Store>();
        adapter = new StoresAdapter(this, R.layout.storeelement, storesArray);
        storeList.setAdapter(adapter);

        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("stores");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                storesArray.clear();
                storeCashboxes.clear();
                for (DataSnapshot storesSnapshot : dataSnapshot.getChildren())
                {
                    String id = storesSnapshot.child("id").getValue().toString();
                    String name = storesSnapshot.child("name").getValue().toString();
                    String region = storesSnapshot.child("region").getValue().toString();
                    String city = storesSnapshot.child("city").getValue().toString();
                    String address = storesSnapshot.child("address").getValue().toString();
                    String comment = storesSnapshot.child("comment").getValue().toString();
                    if (storesSnapshot.child("cashboxes").exists()) {
                        ArrayList<Cashbox> listCB = new ArrayList<Cashbox>();
                        for (DataSnapshot snapCB : storesSnapshot.child("cashboxes").getChildren())
                        {
                            Cashbox cashbox = new Cashbox(snapCB.child("id").getValue().toString(), snapCB.child("parentId").getValue().toString(), snapCB.child("name").getValue().toString(), snapCB.child("model").getValue().toString(), snapCB.child("serialNumber").getValue().toString());
                            listCB.add(cashbox);
                        }
                        storeCashboxes.put(id, listCB);
                    }
                    else
                        storeCashboxes.put(id, null);
                    storesArray.add(new Store(id, name, region, city, address, comment));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        storeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myCashboxes = new Intent(MyStores.this, MyCashboxes.class);
                myCashboxes.putExtra("parentId", adapter.getItem(i).getId());
                myCashboxes.putExtra("title", adapter.getItem(i).getName());
                startActivity(myCashboxes);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK)
            {
                String id = database.push().getKey();
                Store newStore = new Store(id, data.getStringExtra("name"), data.getStringExtra("region"), data.getStringExtra("city"), data.getStringExtra("address"), data.getStringExtra("comment"));
                database.child(id).setValue(newStore);
            }
        }
        else
        {
            if (requestCode == 2)
            {
                if (resultCode == RESULT_OK)
                {
                    database.child(data.getStringExtra("id")).setValue(new Store(data.getStringExtra("id"), data.getStringExtra("name"), data.getStringExtra("region"), data.getStringExtra("city"), data.getStringExtra("address"), data.getStringExtra("comment")));
                    if (storeCashboxes.get((data.getStringExtra("id"))) != null) {
                        for (int i = 0; i < storeCashboxes.get(data.getStringExtra("id")).size(); i++)
                            database.child(data.getStringExtra("id")).child("cashboxes").child(storeCashboxes.get(data.getStringExtra("id")).get(i).getId()).setValue(storeCashboxes.get(data.getStringExtra("id")).get(i));
                    }
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_add:
                Intent newStore = new Intent(MyStores.this, AddStore.class);
                startActivityForResult(newStore, 1);
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

}
