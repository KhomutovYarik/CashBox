package com.example.cashbox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.Base64Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

import java.util.Collections;

import static com.example.cashbox.ActiveOrdersFragment.activeOrdersList;
import static com.example.cashbox.FinishedOrdersFragment.finishedOrdersList;

public class OrdersActivity extends AppCompatActivity {

    TextView ordersText, profileText;
    ImageView ordersIcon, profileIcon, addOrderButton;
    LinearLayout ordersSection, profileSection;
    Button dada;
    DatabaseReference database, userInfo;
    ProgressDialog mProgressDialog;
    public static ProgressDialog profileProgress;
    Fragment selectedFragment = null;

    BottomNavigationView bottomNavigationView;
    LinearLayout ordersLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this, R.style.Theme_AppCompat_Light_NoActionBar_FullScreen);
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.progress);
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

        User.phone = phoneTransform(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("orders");

        userInfo = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userInfo");

        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                activeOrdersList.clear();
                finishedOrdersList.clear();
                for (DataSnapshot ordersSnapshot : dataSnapshot.getChildren())
                {
                    String id = ordersSnapshot.child("id").getValue().toString();
                    String number = ordersSnapshot.child("number").getValue().toString();
                    String storeName = ordersSnapshot.child("storeName").getValue().toString();
                    String cbName = ordersSnapshot.child("cashboxName").getValue().toString();
                    String problem = ordersSnapshot.child("problem").getValue().toString();
                    String problemDesc = ordersSnapshot.child("problemDesc").getValue().toString();
                    String status = ordersSnapshot.child("status").getValue().toString();
                    if (status.equals("1"))
                    {
                        ActiveOrder order = new ActiveOrder(id, number , cbName, storeName, problem, problemDesc, "1",null, null);
                        activeOrdersList.add(order);
                    }
                    else
                    {
                        FinishedOrder order = new FinishedOrder(id, number, cbName, storeName, problem, problemDesc, status, 0);
                        finishedOrdersList.add(order);
                    }
                }
                Collections.reverse(activeOrdersList);
                ActiveOrdersFragment.adapter.notifyDataSetChanged();
                FinishedOrdersFragment.adapter.notifyDataSetChanged();
                mProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("name").exists())
                    User.name = dataSnapshot.child("name").getValue().toString();
                else
                    User.name = null;
                if (dataSnapshot.child("email").exists())
                    User.email = dataSnapshot.child("email").getValue().toString();
                else
                    User.email = null;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ordersLayout = findViewById(R.id.linearLayout_forOrders);
        addOrderButton = findViewById(R.id.fab);
        addOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent neworder = new Intent(OrdersActivity.this, NewOrderActivity.class);
                startActivityForResult(neworder, 1);
            }
        });
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.myOrders);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.myOrders:
                                ordersLayout.setVisibility(View.VISIBLE);
                                //bottomNavigationView.getMenu().findItem(R.id.myProfile).setChecked(true);
                                break;
                            case R.id.addOrder:
                                addOrderButton.performClick();   //нажатие на пустое место за кнопкой
                                break;
                            case R.id.myProfile:
                                if (selectedFragment == null) {
                                    profileProgress = new ProgressDialog(OrdersActivity.this, R.style.Theme_AppCompat_Light_NoActionBar_FullScreen);
                                    profileProgress.show();
                                    profileProgress.setContentView(R.layout.progress);
                                    selectedFragment = new ProfileFragment();
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                            selectedFragment).commit();
                                }
                                ordersLayout.setVisibility(View.INVISIBLE);
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
                double number = Math.random() * 1000000;
                int num = (int)number;
                String id = database.push().getKey();
                Order newOrder = new Order(id, String.valueOf(num), data.getStringExtra("cashbox"), data.getStringExtra("store"), data.getStringExtra("problem"), data.getStringExtra("problemDesc"), "1");
                database.child(id).setValue(newOrder);
//                activeOrdersList.add(0, newOrder);
//                ActiveOrdersFragment.adapter.notifyDataSetChanged();
                    String name = "", email = "", desc = "";
                    if (User.name != null)
                        name = "\n\nИмя пользователя: " + User.name;
                    if (User.email != null)
                        email = "\n\nE-mail пользователя: " + User.email;
                    if (!data.getStringExtra("problemDesc").equals(""))
                        desc = "\n\nПодробное описание проблемы: " + data.getStringExtra("problemDesc");
                    JavaMailAPI sendMessage = new JavaMailAPI(this, "sos@cttp.ru", "Новая заявка", "Поступила новая заявка.\n\nДанные заявки:\n\nНомер заявки: " + num + "\n\nНомер телефона пользователя: " + User.phone + name + email + "\n\nАдрес пользователя: " + "г. " + data.getStringExtra("city") + ", " + data.getStringExtra("address") + "\n\nМодель ККТ: " + data.getStringExtra("model") + "\n\nСерийный номер ККТ: " + data.getStringExtra("serialNumber") + "\n\nОписание проблемы: " + data.getStringExtra("problem") + desc, true);
                    sendMessage.execute();
            }
        }
    }

    private String phoneTransform(String number)
    {
        String newNumber = "+7 (";
        char [] num = new char[number.length()];
        number.getChars(0, number.length(), num, 0);
        newNumber = newNumber + num[2] + num[3] + num[4] + ") " + num[5] + num[6] + num[7] + "-" + num[8] + num[9] + "-" + num[10] + num[11];
        return newNumber;
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