package com.example.cashbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectCashbox extends AppCompatActivity {
    private List<String> models;
    private ListView listOfModels;
    private EditText filter;
    private ArrayAdapter<String> adapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cashbox);
        prepare();
    }

    private void prepare() {
        models = Arrays.asList(getResources().getStringArray(R.array.models));
        listOfModels = findViewById(R.id.listOfModels);
        filter = findViewById(R.id.input);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, models);

        listOfModels.setAdapter(adapter);
        listOfModels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String s = adapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("model_name", s);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (SelectCashbox.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
