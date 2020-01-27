package com.example.cashbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cashbox.ui.main.SectionsPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.cashbox.ActiveOrdersFragment.activeOrdersList;
import static com.example.cashbox.FinishedOrdersFragment.finishedOrdersList;

public class OrdersActivity extends AppCompatActivity {

    TextView ordersText, profileText;
    ImageView ordersIcon, profileIcon, addOrderButton;
    LinearLayout ordersSection, profileSection;

    FirebaseUser user;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveOrdersFragment.adapter = new ActiveOrderAdapter(this, R.layout.orderslistelement, activeOrdersList);
        FinishedOrdersFragment.adapter = new FinishedOrderAdapter(this, R.layout.finishorderslistelement, finishedOrdersList);
        setContentView(R.layout.activity_orders);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        preparing();
    }

    private void preparing() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference().child("orders");
        //String id = database.push().getKey();

        //database.child(id).setValue(new User("sada", "asdas"));

        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ordersSnapshot : dataSnapshot.getChildren())
                {
                    String number = ordersSnapshot.child("number").getValue().toString();
                    String storeName = ordersSnapshot.child("storeName").getValue().toString();
                    String cbName = ordersSnapshot.child("cashboxName").getValue().toString();
                    String problemDesc = ordersSnapshot.child("problemDesc").getValue().toString();
                    String minPrice = ordersSnapshot.child("minPrice").getValue().toString();
                    String offers = ordersSnapshot.child("offersNumber").getValue().toString();
                    ActiveOrder order = new ActiveOrder(number, cbName, storeName, problemDesc, offers, minPrice);
                    activeOrdersList.add(order);
                    ActiveOrdersFragment.adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //database.child(id).setValue(new User("sada", "asdas"));


        addOrderButton = findViewById(R.id.fab);
        addOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent neworder = new Intent(OrdersActivity.this, NewOrderActivity.class);
                startActivityForResult(neworder, 1);
            }
        });
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.myOrders:
                                //bottomNavigationView.getMenu().findItem(R.id.myProfile).setChecked(true);
                                break;
                            case R.id.addOrder:
                                addOrderButton.performClick();   //нажатие на пустое место за кнопкой
                                break;
                            case R.id.myProfile:
                                Intent profile = new Intent(OrdersActivity.this, ProfileActivity.class);
                                startActivity(profile);
                                break;
                        }
                        return true;
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
                ActiveOrder newOrder = new ActiveOrder("#228228", data.getStringExtra("cashbox") + ", ", data.getStringExtra("store"), data.getStringExtra("problem"), "5 предложений", "от 1000 р");
                database.push().setValue(newOrder);
                activeOrdersList.add(0, newOrder);
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