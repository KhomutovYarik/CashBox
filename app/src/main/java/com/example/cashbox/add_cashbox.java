package com.example.cashbox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class add_cashbox extends AppCompatActivity {
    private MaskImpl mi;
    TextView cashbox_name_label, factory_number_label, model_label, storeName_label;
    EditText cashbox_name, factory_number;
    Spinner storeName;
    Button saveButton;
    EditText model;
    ArrayAdapter<String> store_adapter;
    ArrayList<String> storesList;
    ConstraintLayout myLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cashbox);
        prepare();
    }

    private void prepare () {
        storeName_label = findViewById(R.id.storeName_label);
        storeName = findViewById(R.id.storeName);
        cashbox_name = findViewById(R.id.cashboxName);
        cashbox_name_label = findViewById(R.id.cashboxName_label);
        factory_number = findViewById(R.id.factory_number);
        factory_number_label = findViewById(R.id.factory_number_label);
        saveButton = findViewById(R.id.saveButton);
        model = findViewById(R.id.model);
        model_label = findViewById(R.id.model_label);
        myLayout = findViewById(R.id.constraintLayout);


        storesList = new ArrayList<>();
        store_adapter = new ArrayAdapter<String>(add_cashbox.this,
                android.R.layout.simple_list_item_1, storesList);
        store_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeName.setAdapter(store_adapter);
        //TODO подгрузить торговые точки

        if (getIntent().getStringExtra("title") != null)
        {
            this.setTitle(getIntent().getStringExtra("title"));
            cashbox_name.setText(getIntent().getStringExtra("name"));
            model.setText(getIntent().getStringExtra("model"));
            factory_number.setText(getIntent().getStringExtra("serial"));
        }

        storeName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                myLayout.requestFocus();
                storeName_label.setTextColor(getResources().getColor(R.color.colorPrimary));
                storeName.setBackground(getResources().getDrawable(R.drawable.spinner_active));

//              model_label.setTextColor(getResources().getColor(R.color.inactiveColor));
//              cashbox_name_label.setTextColor(getResources().getColor(R.color.inactiveColor));
//              factory_number_label.setTextColor(getResources().getColor(R.color.inactiveColor));
//              cashbox_name.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
//              factory_number.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });

        model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(add_cashbox.this, SelectCashbox.class);
                startActivityForResult(intent, 1);
            }
        });

        Slot[] slots = new UnderscoreDigitSlotsParser().parseSlots("____ ____ ____ ____ ____");
        mi = MaskImpl.createTerminated(slots);
        FormatWatcher formatWatcher = new MaskFormatWatcher( // форматировать текст будет вот он
                mi
        );
        formatWatcher.installOn(factory_number); // устанавливаем форматтер на любой TextView

        factory_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    factory_number_label.setTextColor(getResources().getColor(R.color.colorPrimary));
                    factory_number.setSelection(factory_number.getText().length());
                    factory_number.setBackground(getResources().getDrawable(R.drawable.fields2_active));
                    storeName_label.setTextColor(getResources().getColor(R.color.inactiveColor));
                    storeName.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                }
                else {
                    factory_number_label.setTextColor(getResources().getColor(R.color.inactiveColor));
                    factory_number.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                }
            }
        });

        model.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    storeName_label.setTextColor(getResources().getColor(R.color.inactiveColor));
                    storeName.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                    model.setInputType(InputType.TYPE_NULL);
                    model_label.setTextColor(getResources().getColor(R.color.colorPrimary));
                    model.setSelection(model.getText().length());
                    model.setBackground(getResources().getDrawable(R.drawable.fields2_active));
                    model.performClick();
                }
                else {
                    model_label.setTextColor(getResources().getColor(R.color.inactiveColor));
                    model.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                }
            }
        });

        cashbox_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cashbox_name.performClick();
                    storeName_label.setTextColor(getResources().getColor(R.color.inactiveColor));
                    storeName.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                    cashbox_name_label.setTextColor(getResources().getColor(R.color.colorPrimary));
                    cashbox_name.setSelection(cashbox_name.getText().length());
                    cashbox_name.setBackground(getResources().getDrawable(R.drawable.fields2_active));
                }
                else {
                    cashbox_name_label.setTextColor(getResources().getColor(R.color.inactiveColor));
                    cashbox_name.setBackground(getResources().getDrawable(R.drawable.fields2_inactive));
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mi.clear();
                mi.insertFront(factory_number.getText());
                Intent allCashboxes = new Intent();
                allCashboxes.putExtra("name", cashbox_name.getText().toString());
                allCashboxes.putExtra("model", model.getText().toString());
                allCashboxes.putExtra("serial", mi.toUnformattedString());
                if (getIntent().getStringExtra("id") != null)
                {
                    allCashboxes.putExtra("id", getIntent().getStringExtra("id"));
                }
                setResult(RESULT_OK, allCashboxes);
                finish();
            }
        });

        cashbox_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (cashbox_name.getText().length() > 25)
                {
                    String str = String.valueOf(cashbox_name.getText());
                    str = str.substring(0, 25);
                    cashbox_name.setText(str);
                    cashbox_name.setSelection(cashbox_name.getText().length());
                }
                else
                {
                    if (model.getText().length() > 0 && cashbox_name.getText().length() >= 3 &&
                            factory_number.getText().length() == 24) {
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

        factory_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (model.getText().length() > 0 && cashbox_name.getText().length() >= 3 &&
                        factory_number.getText().length() == 24) {
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
        });

        model.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (model.getText().length() > 0 && cashbox_name.getText().length() >= 3 &&
                        factory_number.getText().length() == 24) {
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
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK)
            {
                String modelName = data.getStringExtra("model_name");
                model.setText(modelName);
            }

        }
    }
}
