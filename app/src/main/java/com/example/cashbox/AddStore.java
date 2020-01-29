package com.example.cashbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class AddStore extends AppCompatActivity {
    private EditText storeName, address, addressComment;
    private TextView storeLabel, tempLabel, regionLabel, cityLabel, addressCommentLabel;
    private Spinner region, city;
    private ConstraintLayout myLayout;
    private Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);
        prepare();
    }
    private void prepare() {
        ArrayList<String> regionList = new ArrayList<String>();
        ArrayList<String> cityList = new ArrayList<String>();

        regionList.add("Выберите регион");
        regionList.add("Ленинградская область");
        regionList.add("Московская область");
        regionList.add("Пермский край");

        cityList.add("Выберите город");
        cityList.add("Санкт-Петербург");
        cityList.add("Москва");
        cityList.add("Пермь");

        region = findViewById(R.id.region);
        city = findViewById(R.id.city);
        myLayout = findViewById(R.id.constraintLayout);
        storeName = findViewById(R.id.storeName);
        storeLabel = findViewById(R.id.storeName_label);
        address = findViewById(R.id.address);
        tempLabel = findViewById(R.id.address_label);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddStore.this,
                android.R.layout.simple_list_item_1, regionList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        region.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(AddStore.this,
                android.R.layout.simple_list_item_1, cityList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(adapter2);

        regionLabel = findViewById(R.id.region_label);

        cityLabel = findViewById(R.id.city_label);
        addressComment = findViewById(R.id.addressComment);
        addressCommentLabel = findViewById(R.id.addressComment_label);
        saveButton = findViewById(R.id.saveButton);

        if (getIntent().getStringExtra("title") != null) {
            this.setTitle(getIntent().getStringExtra("title"));
            storeName.setText(getIntent().getStringExtra("name"));
            region.setSelection(regionList.indexOf(getIntent().getStringExtra("region")));
            city.setSelection(cityList.indexOf(getIntent().getStringExtra("city")));
            address.setText(getIntent().getStringExtra("address"));
            addressComment.setText(getIntent().getStringExtra("comment"));
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allStore = new Intent();
                allStore.putExtra("name", storeName.getText().toString());
                allStore.putExtra("region", region.getSelectedItem().toString());
                allStore.putExtra("city", city.getSelectedItem().toString());
                allStore.putExtra("address", address.getText().toString());
                allStore.putExtra("comment", addressComment.getText().toString());
                if (getIntent().getStringExtra("id") != null)
                    allStore.putExtra("id", getIntent().getStringExtra("id"));
                setResult(RESULT_OK, allStore);
                finish();
            }
        });

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

        region.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                myLayout.requestFocus();
                regionLabel.setTextColor(getResources().getColor(R.color.colorPrimary));
                region.setBackground(getResources().getDrawable(R.drawable.spinner_active));

                cityLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                city.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });

        city.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                myLayout.requestFocus();
                cityLabel.setTextColor(getResources().getColor(R.color.colorPrimary));
                city.setBackground(getResources().getDrawable(R.drawable.spinner_active));

                regionLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                region.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });

        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tempLabel.setTextColor(getResources().getColor(R.color.colorPrimary));
                    address.setSelection(address.getText().length());
                    address.setBackground(getResources().getDrawable(R.drawable.fields2_active));

                    regionLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    region.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                    cityLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    city.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                }
                else {
                    tempLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    address.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                }
            }
        });

        addressComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    addressCommentLabel.setTextColor(getResources().getColor(R.color.colorPrimary));
                    addressComment.setSelection(addressComment.getText().length());
                    addressComment.setBackground(getResources().getDrawable(R.drawable.multiline_active));

                    regionLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    region.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                    cityLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    city.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                }
                else {
                    addressCommentLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    addressComment.setBackground(getResources().getDrawable(R.drawable.multiline_inactive));
                }
            }
        });

        storeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (storeName.getText().length() > 25)
                {
                    String str = String.valueOf(storeName.getText());
                    str = str.substring(0, 25);
                    storeName.setText(str);
                    storeName.setSelection(storeName.getText().length());
                }
                else
                {
                    check();
                }
            }
        });

        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (address.getText().length() > 30)
                {
                    String str = String.valueOf(address.getText());
                    str = str.substring(0, 30);
                    address.setText(str);
                    address.setSelection(address.getText().length());
                }
                else
                {
                    check();
                }
            }
        });

        region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                check();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                check();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void check() {
        if (storeName.getText().length() >= 3 && address.getText().length() >= 5 &&
                region.getSelectedItemId() != 0 && city.getSelectedItemId() != 0) {
            saveButton.setTextColor(getResources().getColor(R.color.colorPrimary));
            saveButton.setBackground(getResources().getDrawable(R.drawable.whitebuttonstyle));
            saveButton.setEnabled(true);
        }
        else {
            saveButton.setTextColor(getResources().getColor(R.color.inactiveColor));
            saveButton.setBackground(getResources().getDrawable(R.drawable.notenabledbutton));
            saveButton.setEnabled(false);
        }
    }
}
