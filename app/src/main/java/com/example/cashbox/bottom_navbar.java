package com.example.cashbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class bottom_navbar extends AppCompatActivity {
    private ImageView fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navbar);
        prepare();

        //bottomNavigationView.setItemIconTintList(null);
    }
    private void prepare(){
        final Context context = bottom_navbar.this;
        fab = findViewById(R.id.fab);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.myOrders:
                                //selectedFragment = new OrdersFragment();
                                Intent intent1 = new Intent(bottom_navbar.this, OrdersActivity.class);//ACTIVITY_NUM = 0
                                startActivity(intent1);
                                break;
                            case R.id.addOrder:
                                fab.performClick();   //нажатие на пустое место за кнопкой
                                break;
                            case R.id.myProfile:
                                selectedFragment = new ProfileFragment();
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                        selectedFragment).commit();
                                break;
                        }

                        return true;
                    }
                });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(bottom_navbar.this);
                builder.setTitle("Ку!")
                        .setMessage("Привет!")
                        .setCancelable(false)
                        .setNegativeButton("ОК",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}
