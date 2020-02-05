package com.example.cashbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cashbox.interfaces.OnSuggestionsListener;
import com.example.cashbox.utils.ServerUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class AddStore extends AppCompatActivity implements OnSuggestionsListener {
    private static final List<String> regionArray = new ArrayList<>();
    private static final List<String> cityArray = new ArrayList<>();
    private DaDataArrayAdapter<String> regionAdapter, cityAdapter;
    private Toast toast;
    private final OnSuggestionsListener listener = this;
    private boolean regionSelected = false, citySelected = false;
    private EditText storeName, address, addressComment;
    private TextView storeLabel, tempLabel, regionLabel, cityLabel, addressCommentLabel;
    //private Spinner region, city;
    private AutoCompleteTextView region, city;
    private ConstraintLayout myLayout;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);
        prepare();
    }
    private void prepare() {
        region = findViewById(R.id.region);
        city = findViewById(R.id.city);
        myLayout = findViewById(R.id.constraintLayout);
        storeName = findViewById(R.id.storeName);
        storeLabel = findViewById(R.id.storeName_label);
        address = findViewById(R.id.address);
        tempLabel = findViewById(R.id.address_label);
        regionLabel = findViewById(R.id.region_label);
        cityLabel = findViewById(R.id.city_label);
        addressComment = findViewById(R.id.addressComment);
        addressCommentLabel = findViewById(R.id.addressComment_label);
        saveButton = findViewById(R.id.saveButton);

        regionAdapter = new DaDataArrayAdapter<>(this, android.R.layout.simple_list_item_1, regionArray);
        region.setAdapter(regionAdapter);

        cityAdapter = new DaDataArrayAdapter<>(this, android.R.layout.simple_list_item_1, cityArray);
        city.setAdapter(cityAdapter);

        region.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                //String selected = (String) parent.getItemAtPosition(position);
                //int pos = Arrays.asList(regionArray).indexOf(selected);
                regionSelected = true;
                regionAdapter.clear();
                cityAdapter.clear();
                //city.setEnabled(true);
                check();
            }
        });

        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                citySelected = true;
                regionAdapter.clear();
                cityAdapter.clear();
                check();
            }
        });

        if (getIntent().getStringExtra("title") != null) {
            citySelected = true;
            regionSelected = true;
            this.setTitle(getIntent().getStringExtra("title"));
            storeName.setText(getIntent().getStringExtra("name"));
            region.setText(getIntent().getStringExtra("region"));
            city.setText(getIntent().getStringExtra("city"));
            address.setText(getIntent().getStringExtra("address"));
            addressComment.setText(getIntent().getStringExtra("comment"));
            //city.setEnabled(true);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allStore = new Intent();
                allStore.putExtra("name", storeName.getText().toString());
                allStore.putExtra("region", region.getText().toString());
                allStore.putExtra("city", city.getText().toString());
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
                    region.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                    cityLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    city.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                }
                else {
                    storeLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    storeName.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                }
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
                    region.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                    cityLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    city.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
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
                    region.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                    cityLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    city.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
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

//        region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                check();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                check();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        region.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    regionLabel.setTextColor(getResources().getColor(R.color.colorPrimary));
                    region.setBackground(getResources().getDrawable(R.drawable.fields2_active));

                    cityLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    city.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                }
            }
        });

        city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    cityLabel.setTextColor(getResources().getColor(R.color.colorPrimary));
                    city.setBackground(getResources().getDrawable(R.drawable.fields2_active));

                    regionLabel.setTextColor(getResources().getColor(R.color.inactiveColor));
                    region.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                }
            }
        });

//        city.setOnTouchListener(new View.OnTouchListener() {
//            @SuppressLint("ClickableViewAccessibility")
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (regionSelected == false) {
//                    toast = Toast.makeText(getApplicationContext(), "Сначала заполните поле Регион", Toast.LENGTH_SHORT);
//                    toast.show();
//                }
//                return false;
//            }
//        });
    }

    private void check() {
        if (storeName.getText().length() >= 3 && address.getText().length() >= 5 && (
                regionSelected && citySelected || region.getText().length() >= 5 && city.getText().length() >= 2)) {
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
    protected void onResume() {
        super.onResume();
        region.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //city.setEnabled(false);
                city.setText("");
                regionSelected = false;
            }

            @Override
            public void afterTextChanged(Editable s) {
                check();
                ServerUtils.query(s.toString(), listener, true,null);
            }
        });
        //city.addTextChangedListener(this);
        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                citySelected = false;
            }

            @Override
            public void afterTextChanged(Editable s) {
                check();
                regionAdapter.clear();
                String regionName = region.getText().toString();
                if (regionSelected)
                    ServerUtils.query(s.toString(), listener, false, regionName);
                else
                    ServerUtils.query(s.toString(), listener,false,null);
            }
        });
    }

    @Override
    public synchronized void onSuggestionsReady(List<String> suggestions) {
        // Clear current suggestions
        regionAdapter.clear();
        cityAdapter.clear();

        // If current device is 3.0 and higher,
        // we use addAll(..) method
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            regionAdapter.addAll(suggestions);
            cityAdapter.addAll(suggestions);
        } else {
            for (String s : suggestions) {
                regionAdapter.add(s);
                cityAdapter.add(s);
            }
        }

        // Notify about the change
        regionAdapter.notifyDataSetChanged();
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String message) {
        // Cancel current toast for
        // showing only one toast at a time
        if (toast != null)
            toast.cancel();

        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
