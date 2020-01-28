package com.example.cashbox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
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
    private ArrayList<String> cashboxModels;
    private MaskImpl mi;
    TextView cashbox_name_label, factory_number_label, model_label;
    EditText cashbox_name, factory_number;
    Button saveButton;
    EditText model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cashbox);
        prepare();
    }

    private void prepare () {
        cashboxModels = new ArrayList<>();
        cashboxModels.add("1"); cashboxModels.add("2"); cashboxModels.add("3"); cashboxModels.add("4"); cashboxModels.add("5"); cashboxModels.add("6");
        cashbox_name = findViewById(R.id.cashboxName);
        cashbox_name_label = findViewById(R.id.cashboxName_label);
        factory_number = findViewById(R.id.factory_number);
        factory_number_label = findViewById(R.id.factory_number_label);
        saveButton = findViewById(R.id.saveButton);
        model = findViewById(R.id.model);
        model_label = findViewById(R.id.model_label);

        model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(add_cashbox.this, SelectCashbox.class);
                intent.putExtra("cashboxModels", cashboxModels);
                startActivityForResult(intent, 1);
            }
        });

        Slot[] slots = new UnderscoreDigitSlotsParser().parseSlots("____ ____ ____ ____ ____");
        mi = MaskImpl.createTerminated(slots);
        FormatWatcher formatWatcher = new MaskFormatWatcher( // форматировать текст будет вот он
                mi
        );
        formatWatcher.installOn(factory_number); // устанавливаем форматтер на любой TextView

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mi.clear();
                mi.insertFront(factory_number.getText());
                factory_number_label.setText(mi.toUnformattedString());
            }
        });

        factory_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    factory_number_label.setTextColor(getResources().getColor(R.color.colorPrimary));
                    factory_number.setSelection(factory_number.getText().length());
                    factory_number.setBackground(getResources().getDrawable(R.drawable.fields2_active));
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
                Toast.makeText(this,modelName,Toast.LENGTH_SHORT).show();
                model.setText(modelName);
            }

        }
    }
}
