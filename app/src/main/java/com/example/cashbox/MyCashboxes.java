package com.example.cashbox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class MyCashboxes extends AppCompatActivity {

    ArrayList<Cashbox> cbArray;
    CashboxAdapter adapter;
    ListView cbListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cashboxes);
        this.setTitle(getIntent().getStringExtra("title"));
        preparing();
    }

    private void preparing()
    {
        cbArray = new ArrayList<Cashbox>();
        cbArray.add(new Cashbox("Касса №1", "МЕРКУРИЙ-180Ф", "123454524234235"));

        cbListView = findViewById(R.id.cbListView);
        adapter = new CashboxAdapter(this, R.layout.cashboxelement, cbArray);
        cbListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_add:
                Intent newCashbox = new Intent(MyCashboxes.this, add_cashbox.class);
                startActivityForResult(newCashbox, 1);
                break;
        }
        return false;
    }

}
