package com.example.cashbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
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


public class AddStore extends AppCompatActivity {
    EditText storeName, address, addressComment;
    TextView storeLabel, tempLabel, regionLabel, cityLabel, addressCommentLabel;
    Spinner region, city;
    ConstraintLayout myLayout;
    Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);
        prepare();
    }
    private void prepare() {
        Spinner spinner = findViewById(R.id.region);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddStore.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.region));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner2 = findViewById(R.id.city);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(AddStore.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.city));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        myLayout = findViewById(R.id.constraintLayout);
        storeName = findViewById(R.id.storeName);
        storeLabel = findViewById(R.id.storeName_label);
        address = findViewById(R.id.address);
        tempLabel = findViewById(R.id.address_label);
        region = findViewById(R.id.region);
        regionLabel = findViewById(R.id.region_label);
        city = findViewById(R.id.city);
        cityLabel = findViewById(R.id.city_label);
        addressComment = findViewById(R.id.addressComment);
        addressCommentLabel = findViewById(R.id.addressComment_label);
        saveButton = findViewById(R.id.saveButton);

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
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
