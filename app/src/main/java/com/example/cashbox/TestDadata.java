package com.example.cashbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.cashbox.interfaces.OnSuggestionsListener;
import com.example.cashbox.utils.ServerUtils;

import java.util.ArrayList;
import java.util.List;

public class TestDadata extends AppCompatActivity implements TextWatcher, OnSuggestionsListener {

    private static final List<String> EMPTY = new ArrayList<>();
    private static final List<String> EMPTY2 = new ArrayList<>();
    private DaDataArrayAdapter<String> adapter, adapter2;
    private AutoCompleteTextView region, city;
    private Toast toast;
    private boolean type;
    private final OnSuggestionsListener listener = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dadata);

        region = findViewById(R.id.region);
        adapter = new DaDataArrayAdapter<>(this, android.R.layout.simple_list_item_1, EMPTY);

        region.setAdapter(adapter);

        city = findViewById(R.id.city);
        adapter2 = new DaDataArrayAdapter<>(this, android.R.layout.simple_list_item_1, EMPTY2);

        city.setAdapter(adapter2);

        region.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                //String selected = (String) parent.getItemAtPosition(position);
                //int pos = Arrays.asList(EMPTY).indexOf(selected);

                adapter2.clear();
                city.setEnabled(true);
            }
        });
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
                city.setEnabled(false);
                city.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
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

            }

            @Override
            public void afterTextChanged(Editable s) {
                String regionName = region.getText().toString();
                ServerUtils.query(s.toString(), listener, false, regionName);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(final Editable s) {
        //ServerUtils.query(s.toString(), this);
    }

    @Override
    public synchronized void onSuggestionsReady(List<String> suggestions) {
        // Clear current suggestions
        adapter.clear();
        adapter2.clear();

        // If current device is 3.0 and higher,
        // we use addAll(..) method
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            adapter.addAll(suggestions);
            adapter2.addAll(suggestions);
        } else {
            for (String s : suggestions) {
                adapter.add(s);
                adapter2.add(s);
            }
        }

        // Notify about the change
        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
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
