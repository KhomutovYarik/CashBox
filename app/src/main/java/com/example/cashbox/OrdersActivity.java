package com.example.cashbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cashbox.ui.main.SectionsPagerAdapter;

public class OrdersActivity extends AppCompatActivity {

    TextView ordersText, profileText;
    ImageView ordersIcon, profileIcon, addOrderButton;
    LinearLayout ordersSection, profileSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        preparing();
    }

    private void preparing() {
        addOrderButton = findViewById(R.id.fab);
        addOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent neworder = new Intent(OrdersActivity.this, NewOrderActivity.class);
                startActivityForResult(neworder, 1);
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
                ActiveOrdersFragment.activeOrdersList.add(0, new ActiveOrder("#228228", data.getStringExtra("cashbox") + ", ", data.getStringExtra("store"), data.getStringExtra("problem"), "5 предложений", "от 1000 р"));
                ActiveOrdersFragment.adapter.notifyDataSetChanged();
            }
        }
    }

    /*private void preparing()
    {
        ordersText = findViewById(R.id.myOrdersText);
        profileText = findViewById(R.id.myProfileText);
        ordersIcon = findViewById(R.id.ordersIcon);
        profileIcon = findViewById(R.id.profileIcon);
        addOrderButton = findViewById(R.id.addOrderButton);
        ordersSection = findViewById(R.id.ordersSection);
        profileSection = findViewById(R.id.profileSection);

        ordersSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ordersText.setTextColor(getResources().getColor(R.color.colorPrimary));
                ordersIcon.setImageResource(R.drawable.ic_ordersactive);
                profileText.setTextColor(getResources().getColor(R.color.inactiveColor));
                profileIcon.setImageResource(R.drawable.ic_profile);
            }
        });

        profileSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileText.setTextColor(getResources().getColor(R.color.colorPrimary));
                profileIcon.setImageResource(R.drawable.ic_profileactive);
                ordersText.setTextColor(getResources().getColor(R.color.inactiveColor));
                ordersIcon.setImageResource(R.drawable.ic_orders);
            }
        });

        addOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newOrderPage = new Intent(OrdersActivity.this, NewOrderActivity.class);
                startActivity(newOrderPage);
            }
        });
    }*/
}