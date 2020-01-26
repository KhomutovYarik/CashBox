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

public class NewOrderActivity extends AppCompatActivity {
    private TextView store_label, cashbox_label, problem_label, problemDescription_label;
    private EditText problemDescription;
    private Spinner cashbox, problem, store;
    private Button continue_Button;
    private ConstraintLayout myLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        prepare();
    }
    private void prepare() {
        store_label = findViewById(R.id.problemStore_label);
        cashbox_label = findViewById(R.id.cashbox_label);
        problem_label = findViewById(R.id.problem_label);
        problemDescription_label = findViewById(R.id.problemDescription_label);
        store = findViewById(R.id.problemStore);
        problemDescription = findViewById(R.id.problemDescription);
        cashbox = findViewById(R.id.cashbox);
        problem = findViewById(R.id.problem);
        continue_Button = findViewById(R.id.continueButton);
        myLayout = findViewById(R.id.constraintLayout);

        ArrayAdapter<String> store_adapter = new ArrayAdapter<>(NewOrderActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.store));
        store_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        store.setAdapter(store_adapter);

        ArrayAdapter<String> cashbox_adapter = new ArrayAdapter<>(NewOrderActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.cashbox));
        cashbox_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cashbox.setAdapter(cashbox_adapter);

        ArrayAdapter<String> problem_adapter = new ArrayAdapter<>(NewOrderActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.problem));
        problem_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        problem.setAdapter(problem_adapter);

        problemDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    problemDescription_label.setTextColor(getResources().getColor(R.color.colorPrimary));
                    problemDescription.setSelection(problemDescription.getText().length());
                    problemDescription.setBackground(getResources().getDrawable(R.drawable.multiline_active));

                    cashbox_label.setTextColor(getResources().getColor(R.color.inactiveColor));
                    problem_label.setTextColor(getResources().getColor(R.color.inactiveColor));
                    store_label.setTextColor(getResources().getColor(R.color.inactiveColor));
                    cashbox.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                    problem.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                    store.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                }
                else {
                    problemDescription_label.setTextColor(getResources().getColor(R.color.inactiveColor));
                    problemDescription.setBackground(getResources().getDrawable(R.drawable.multiline_inactive));
                }
            }
        });

        store.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                myLayout.requestFocus();
                store_label.setTextColor(getResources().getColor(R.color.colorPrimary));
                store.setBackground(getResources().getDrawable(R.drawable.spinner_active));

                cashbox_label.setTextColor(getResources().getColor(R.color.inactiveColor));
                problem_label.setTextColor(getResources().getColor(R.color.inactiveColor));
                cashbox.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                problem.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });

        cashbox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                myLayout.requestFocus();
                cashbox_label.setTextColor(getResources().getColor(R.color.colorPrimary));
                cashbox.setBackground(getResources().getDrawable(R.drawable.spinner_active));

                store_label.setTextColor(getResources().getColor(R.color.inactiveColor));
                problem_label.setTextColor(getResources().getColor(R.color.inactiveColor));
                store.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                problem.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });

        problem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                myLayout.requestFocus();
                problem_label.setTextColor(getResources().getColor(R.color.colorPrimary));
                problem.setBackground(getResources().getDrawable(R.drawable.spinner_active));

                store_label.setTextColor(getResources().getColor(R.color.inactiveColor));
                cashbox_label.setTextColor(getResources().getColor(R.color.inactiveColor));
                store.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                cashbox.setBackground(getResources().getDrawable(R.drawable.spinner_inactive));
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });

        store.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                check();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cashbox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                check();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        problem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                check();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        problemDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                check();
            }
        });

        continue_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allorders = new Intent();
                allorders.putExtra("store", store.getSelectedItem().toString());
                allorders.putExtra("cashbox", cashbox.getSelectedItem().toString());
                allorders.putExtra("problem", problem.getSelectedItem().toString());
                allorders.putExtra("problemdesc", String.valueOf(problemDescription.getText()));
                setResult(RESULT_OK, allorders);
                finish();
            }
        });
    }


    private void check () {
        if (store.getSelectedItemId() != 0 && cashbox.getSelectedItemId() != 0 &&
                problem.getSelectedItemId() != 0 && (problem.getSelectedItemId() != 5  ||
                problemDescription.getText().length() >= 10)) {
            continue_Button.setTextColor(getResources().getColor(R.color.colorPrimary));
            continue_Button.setBackground(getResources().getDrawable(R.drawable.whitebuttonstyle));
            continue_Button.setEnabled(true);
        }
        else {
            continue_Button.setTextColor(getResources().getColor(R.color.inactiveColor));
            continue_Button.setBackground(getResources().getDrawable(R.drawable.notenabledbutton));
            continue_Button.setEnabled(false);
        }
    }
}
