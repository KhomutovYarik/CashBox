package com.example.cashbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
        StoresAdapter adapter = new StoresAdapter(this, R.layout.storeelement, storesArray);
        storeList.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
