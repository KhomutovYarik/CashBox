package com.example.cashbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.cashbox.interfaces.OnSuggestionsListener;
import com.example.cashbox.utils.ServerUtils;

import java.util.ArrayList;
import java.util.List;

public class TestDadata extends AppCompatActivity implements TextWatcher, OnSuggestionsListener {

    private static final List<String> EMPTY = new ArrayList<>();
    private DaDataArrayAdapter<String> adapter;
    private AutoCompleteTextView textView;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dadata);

        textView = findViewById(R.id.autocompletetextview_activitymain);
        adapter = new DaDataArrayAdapter<>(this, android.R.layout.simple_list_item_1, EMPTY);

        textView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        textView.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(final Editable s) {
        ServerUtils.query(s.toString(), this);
    }

    @Override
    public synchronized void onSuggestionsReady(List<String> suggestions) {
        // Clear current suggestions
        adapter.clear();

        // If current device is 3.0 and higher,
        // we use addAll(..) method
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            adapter.addAll(suggestions);
        } else {
            for (String s : suggestions) {
                adapter.add(s);
            }
        }

        // Notify about the change
        adapter.notifyDataSetChanged();
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
