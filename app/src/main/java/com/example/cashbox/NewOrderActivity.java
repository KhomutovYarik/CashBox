package com.example.cashbox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class NewOrderActivity extends AppCompatActivity {
    TextView store_label, cashbox_label, problem_label, problemDescription_label;
    EditText problemDescription;
    Spinner cashbox, problem, store;
    Button continue_Button;
    ImageView [] addImage;
    ConstraintLayout myLayout;
    ArrayList<Uri> imguri;

    DatabaseReference database;
    ArrayList<String> storesList, cbsList;
    ArrayList<String> [] allLists;
    ArrayList<Store> stores;
    ArrayList<Cashbox> cashboxes;
    ArrayAdapter<String> store_adapter, cashbox_adapter;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        mProgressDialog = new ProgressDialog(this, R.style.Theme_AppCompat_Light_NoActionBar_FullScreen);
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.progress);
        prepare();
    }

    private void prepare() {
        storesList = new ArrayList<>();
        cbsList = new ArrayList<>();
        stores = new ArrayList<>();
        cashboxes = new ArrayList<>();
        imguri = new ArrayList<>();
        addImage = new ImageView[3];

        store_label = findViewById(R.id.problemStore_label);
        cashbox_label = findViewById(R.id.cashbox_label);
        problem_label = findViewById(R.id.problem_label);
        problemDescription_label = findViewById(R.id.problemDescription_label);
        store = findViewById(R.id.problemStore);
        problemDescription = findViewById(R.id.problemDescription);
        cashbox = findViewById(R.id.cashbox);
        cashbox.setEnabled(false);
        problem = findViewById(R.id.problem);
        continue_Button = findViewById(R.id.continueButton);
        addImage[0] = findViewById(R.id.attachImg_icon1);
        addImage[1] = findViewById(R.id.attachImg_icon2);
        addImage[2] = findViewById(R.id.attachImg_icon3);
        myLayout = findViewById(R.id.constraintLayout);

        store_adapter = new ArrayAdapter<String>(NewOrderActivity.this,
                android.R.layout.simple_list_item_1, storesList);
        store_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        store.setAdapter(store_adapter);

        cashbox_adapter = new ArrayAdapter<String>(NewOrderActivity.this,
                android.R.layout.simple_list_item_1, cbsList);
        cashbox_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cashbox.setAdapter(cashbox_adapter);

        ArrayAdapter<String> problem_adapter = new ArrayAdapter<>(NewOrderActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.problem));
        problem_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        problem.setAdapter(problem_adapter);

        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("stores");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                storesList.clear();
                cbsList.clear();
                stores.clear();
                cashboxes.clear();
                storesList.add("Выберите торговую точку");
                cbsList.add("Выберите кассовый аппарат");
                int i = (int)dataSnapshot.getChildrenCount();
                allLists = new ArrayList[i];
                for (int j = 0; j < i; j++)
                {
                    allLists[j] = new ArrayList<String>();
                }
                int j = 0;
                for (DataSnapshot snap1 : dataSnapshot.getChildren())
                {
                    String id = snap1.child("id").getValue().toString();
                    storesList.add(snap1.child("name").getValue().toString());
                    stores.add(new Store(null, null, null, snap1.child("city").getValue().toString(), snap1.child("address").getValue().toString(), null));
                    for (DataSnapshot snap2 : dataSnapshot.child(id).child("cashboxes").getChildren())
                    {
                        allLists[j].add(snap2.child("name").getValue().toString());
                        cashboxes.add(new Cashbox(null, null, null, snap2.child("model").getValue().toString(), snap2.child("serialNumber").getValue().toString()));
                    }
                    j++;
                }
                storesList.add("+ Добавить точку");
                store_adapter.notifyDataSetChanged();
                cashbox_adapter.notifyDataSetChanged();
                mProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
                cbsList.clear();
                cbsList.add("Выберите кассовый аппарат");
                if (i!=0 && i!=storesList.size() - 1) {
                    cbsList.addAll(allLists[i - 1]);
                    cbsList.add("+ Добавить ККТ");
                    cashbox.setEnabled(true);
                    check();
                }
                else
                {
                    cashbox.setEnabled(false);
                    if (i == storesList.size() - 1)
                    {
                        Intent newStore = new Intent(NewOrderActivity.this, AddStore.class);
                        startActivityForResult(newStore, 1);
                    }
                }
                cashbox_adapter.notifyDataSetChanged();
                cashbox.setSelection(0);
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

        for (int i = 0; i < 3; i++)
            ImageSetOnClick(addImage[i], i);

        continue_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allorders = new Intent();
                allorders.putExtra("store", store.getSelectedItem().toString());
                allorders.putExtra("cashbox", cashbox.getSelectedItem().toString());
                allorders.putExtra("problem", problem.getSelectedItem().toString());
                allorders.putExtra("problemDesc", String.valueOf(problemDescription.getText()));
                allorders.putExtra("city", stores.get(store.getSelectedItemPosition() - 1).getCity());
                allorders.putExtra("address", stores.get(store.getSelectedItemPosition() - 1).getAddress());
                allorders.putExtra("model", cashboxes.get(cashbox.getSelectedItemPosition() - 1).getModel());
                allorders.putExtra("serialNumber", cashboxes.get(cashbox.getSelectedItemPosition() - 1).getSerialNumber());
                for (int i = 0; i < imguri.size(); i++)
                    allorders.putExtra("photo" + String.valueOf(i+1), imguri.get(i).toString());
                setResult(RESULT_OK, allorders);
                finish();
            }
        });
    }

    void forImageAdd(final int i)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Выбрать фото"), 3 + i);
    }

    void ImageSetOnClick(final ImageView img, final int i)
    {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forImageAdd(i);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK)
            {
                mProgressDialog.show();
                String id = database.push().getKey();
                Store newStore = new Store(id, data.getStringExtra("name"), data.getStringExtra("region"), data.getStringExtra("city"), data.getStringExtra("address"), data.getStringExtra("comment"));
                database.child(id).setValue(newStore);
                cashbox.setEnabled(true);
            }
            else
            {
                store.setSelection(0);
            }
        }
        else
        {
            if (requestCode == 2)
            {
                if (resultCode == RESULT_OK)
                {

                }
            }
            else
            {
                if (requestCode == 3)
                {
                    if (resultCode == RESULT_OK && data != null)
                    {
                        if (imguri.size() == 0)
                            imguri.add(data.getData());
                        else
                            imguri.set(0, data.getData());
                        addImage[0].setImageURI(imguri.get(0));
                        addImage[1].setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    if (requestCode == 4)
                    {
                        if (resultCode == RESULT_OK && data != null)
                        {
                            if (imguri.size() == 1)
                                imguri.add(data.getData());
                            else
                                imguri.set(1, data.getData());
                            addImage[1].setImageURI(imguri.get(1));
                            addImage[2].setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        if (requestCode == 5)
                        {
                            if (resultCode == RESULT_OK && data != null)
                            {
                                if (imguri.size() == 2)
                                    imguri.add(data.getData());
                                else
                                    imguri.set(2, data.getData());
                                addImage[2].setImageURI(imguri.get(2));
                            }
                        }
                    }
                }
            }
        }
    }
}
