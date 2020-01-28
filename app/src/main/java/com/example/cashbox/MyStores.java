package com.example.cashbox;

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

import com.example.cashbox.ui.main.SectionsPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MyStores extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    LinearLayout storesLayout;
    ListView storeList;

    ArrayList<Store> storesArray;
    StoresAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stores);
        preparing();
    }

    private void preparing() {
        storesLayout = findViewById(R.id.linearLayout_forStores);
        storeList = findViewById(R.id.myStoresList);
        storesArray = new ArrayList<Store>();
        storesArray.add(new Store("Партнер ККМ №1", "Пермский край", "Пермь", "ул. Шоссе Космонавтов 65", "Вход со двора"));
        adapter = new StoresAdapter(this, R.layout.storeelement, storesArray);
        storeList.setAdapter(adapter);

        storeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myCashboxes = new Intent(MyStores.this, MyCashboxes.class);
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
                Store newStore = new Store(data.getStringExtra("name"), data.getStringExtra("region"), data.getStringExtra("city"), data.getStringExtra("address"), data.getStringExtra("comment"));
                storesArray.add(newStore);
                adapter.notifyDataSetChanged();
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
