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

import java.util.ArrayList;

public class SelectCashbox extends AppCompatActivity {
    ArrayList<String> names;
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
        names = new ArrayList<>();
        names.add("1"); names.add("2"); names.add("3"); names.add("4"); names.add("5"); names.add("6");
        listOfModels = findViewById(R.id.listOfModels);
        filter = findViewById(R.id.input);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);

        listOfModels.setAdapter(adapter);
        listOfModels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                intent = new Intent();

                intent.putExtra("model_id", String.valueOf(position));
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
