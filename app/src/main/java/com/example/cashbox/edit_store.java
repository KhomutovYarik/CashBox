package com.example.cashbox;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class edit_store extends AppCompatActivity {
    EditText storeName, temp;
    TextView storeLabel, tempLabel, regionLabel, cityLabel;
    Spinner region, city;
    ConstraintLayout myLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_store);
        prepare();
    }
    private void prepare() {
        Spinner spinner = findViewById(R.id.region);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(edit_store.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.region));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner2 = findViewById(R.id.city);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(edit_store.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.city));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        myLayout = findViewById(R.id.constraintLayout);
        storeName = findViewById(R.id.storeName);
        storeLabel = findViewById(R.id.storeName_label);
        temp = findViewById(R.id.temp);
        tempLabel = findViewById(R.id.temp_label);
        region = findViewById(R.id.region);
        regionLabel = findViewById(R.id.region_label);
        city = findViewById(R.id.city);
        cityLabel = findViewById(R.id.city_label);

        storeName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    storeLabel.setTextColor(getResources().getColor(R.color.colorPrimary));
                    storeName.setSelection(storeName.getText().length());
                    storeName.setBackground(getResources().getDrawable(R.drawable.fields2_active));
                    regionLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    region.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                    cityLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    city.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                }
                else {
                    storeLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    storeName.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                }
            }
        });
        temp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tempLabel.setTextColor(getResources().getColor(R.color.colorPrimary));
                    temp.setSelection(temp.getText().length());
                    temp.setBackground(getResources().getDrawable(R.drawable.fields2_active));
                    regionLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    region.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                    cityLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    city.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                    storeLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    storeName.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                }
                else {
                    tempLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    temp.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                }
            }
        });
        region.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                storeName.clearFocus();
                region.clearFocus();
                myLayout.requestFocus();
                storeLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                storeName.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                tempLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                temp.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                cityLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                city.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                regionLabel.setTextColor(getResources().getColor(R.color.colorPrimary));
                region.setBackground(getResources().getDrawable(R.drawable.spinner_active));
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });
        city.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                storeName.clearFocus();
                region.clearFocus();
                myLayout.requestFocus();
                storeLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                storeName.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                tempLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                temp.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                regionLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                region.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                cityLabel.setTextColor(getResources().getColor(R.color.colorPrimary));
                city.setBackground(getResources().getDrawable(R.drawable.spinner_active));
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });
    }
}
