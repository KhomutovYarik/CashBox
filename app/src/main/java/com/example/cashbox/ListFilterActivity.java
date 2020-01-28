package com.example.cashbox;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListFilterActivity extends ListActivity {

    private List<String> list = new ArrayList<String>();
    List<String> mOriginalValues;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cashbox);

        final MyAdapter adapter = new MyAdapter(this, getModel());
        setListAdapter(adapter);

        EditText filterEditText = findViewById(R.id.input);

        // Add Text Change Listener to EditText
        filterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                // Call back the Adapter with current character to Filter
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private List<String> getModel() {
        list.add("Linux");
        list.add("Windows7");
        list.add("Suse");
        list.add("Eclipse");
        list.add("Ubuntu");
        list.add("Solaris");
        list.add("Android");
        list.add("iPhone");
        list.add("Windows XP");
        return list;
    }
}




