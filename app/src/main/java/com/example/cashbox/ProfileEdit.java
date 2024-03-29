package com.example.cashbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class ProfileEdit extends AppCompatActivity {

    EditText profile_name, profile_email, old_password, new_password, new_password_again;
    TextView phoneNumber;
    Button saveButton;
    String name = "", email = "";
    Intent intent;
    AppCompatImageView lock1, lock2, lock3;
    private ProgressBar progressBar;

    DatabaseReference userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        prepare();
    }

    private void prepare() {

        profile_name = findViewById(R.id.profile_name);
        phoneNumber = findViewById(R.id.phoneNumber);
        saveButton = findViewById(R.id.saveButton);
        profile_email = findViewById(R.id.profile_email);
        old_password = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        new_password_again = findViewById(R.id.new_password_again);
        lock1 = findViewById(R.id.lock);
        lock2 = findViewById(R.id.lock2);
        lock3 = findViewById(R.id.lock3);
        progressBar = findViewById(R.id.progressBar);
        final CharSequence defHint = profile_name.getHint();
        progressBar.setVisibility(View.VISIBLE);
        userInfo = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userInfo");

        userInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("name").exists()) {
                    //profile_name.setText("");
                    profile_name.setText(dataSnapshot.child("name").getValue().toString());
                    profile_name.setSelection(profile_name.getText().length());
                }
                else {
                    profile_name.setText("");
                }
                name = profile_name.getText().toString();
                if (dataSnapshot.child("email").exists())
                    profile_email.setText(dataSnapshot.child("email").getValue().toString());
                else
                    profile_email.setText("");
                email = profile_email.getText().toString();
                phoneNumber.setText(User.phone);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        phoneNumber.setText(User.phone);


        profile_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (profile_email.getText().length() > 35)
                {
                    String str = String.valueOf(profile_email.getText());
                    str = str.substring(0, 35);
                    profile_email.setText(str);
                    profile_email.setSelection(profile_email.getText().length());
                }
                else
                {
                    if (check()) {
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
        profile_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(profile_name.getText().length()>5)
//                {
//                    profile_name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
//                }
//                else profile_name.setWidth(250);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (profile_name.getText().length() == 0)
                    profile_name.setHint(defHint);
                else
                    profile_name.setHint("");
                if (profile_name.getText().length() > 15)
                {
                    String str = String.valueOf(profile_name.getText());
                    str = str.substring(0, 15);
                    profile_name.setText(str);
                    profile_name.setSelection(profile_name.getText().length());
                }
                else
                {
                    if (check()) {
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


        old_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (old_password.getText().length() > 20)
                {
                    String str = String.valueOf(old_password.getText());
                    str = str.substring(0, 20);
                    old_password.setText(str);
                    old_password.setSelection(old_password.getText().length());
                }
                else
                {
                    if (check()) {
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
        new_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (new_password.getText().length() > 20)
                {
                    String str = String.valueOf(new_password.getText());
                    str = str.substring(0, 20);
                    new_password.setText(str);
                    new_password.setSelection(new_password.getText().length());
                }
                else
                {
                    if (check()) {
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
        new_password_again.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (profile_name.getText().length() > 20)
                {
                    String str = String.valueOf(profile_name.getText());
                    str = str.substring(0, 20);
                    profile_name.setText(str);
                    profile_name.setSelection(profile_name.getText().length());
                }
                else
                {
                    if (check()) {
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

        intent = new Intent();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!profile_name.getText().toString().equals(name)) {
                    if (profile_name.getText().length() >= 3) {
                        //сохранить имя
                        intent.putExtra("name", profile_name.getText().toString());
                    }
                    else {
                        if (profile_name.getText().length() != 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileEdit.this);
                            builder.setTitle("Вы ошиблись при вводе имени!")
                                    .setMessage("Имя должно содержать хотя бы 3 символа")
                                    .setCancelable(false)
                                    .setPositiveButton("ОК",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                            return;
                        }
                    }
                }
                if (profile_email.getText().length() != 0) {
                    if (isValidEmail(profile_email.getText().toString())) {
                        intent.putExtra("email", profile_email.getText().toString());
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileEdit.this);
                        builder.setTitle("Введён некорректный email!")
                                .setMessage("Проверьте ввод и повторите ещё раз")
                                .setCancelable(false)
                                .setPositiveButton("ОК",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                        return;
                    }
                }

//                if (old_password.getText().length() != 0 && new_password.getText().length() != 0 &&
//                        new_password_again.getText().length() != 0
//                        && new_password.getText().toString().equals(new_password_again.getText().toString())) {
//                    if (isPassChanged()) {
//                        intent.putExtra("new_password", new_password.getText().toString());
//                    }
//                    else {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileEdit.this);
//                        builder.setTitle("Вы ошиблись при вводе пароля!")
//                                .setMessage("Проверьте ввод и повторите ещё раз")
//                                .setCancelable(false)
//                                .setPositiveButton("ОК",
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                dialog.cancel();
//                                            }
//                                        });
//                        AlertDialog alert = builder.create();
//                        alert.show();
//                        return;
//                    }
//                }
                String newName = null;
                String newEmail = null;

                if (profile_name.getText().length() != 0)
                {
                    newName = profile_name.getText().toString();
                    getIntent().putExtra("name", newName);
                }
                if (profile_email.getText().length() != 0)
                {
                    newEmail = profile_email.getText().toString();
                }

                userInfo.setValue(new UserInfo(newName, newEmail));

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        old_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    lock1.setImageResource(R.drawable.ic_lock_active);
                    old_password.setSelection(old_password.getText().length());
                    old_password.setBackground(getResources().getDrawable(R.drawable.fields_profile_active));
                }
                else {
                    lock1.setImageResource(R.drawable.ic_lock);
                    old_password.setBackground(getResources().getDrawable(R.drawable.fields_profile_inactive));
                }
            }
        });
        new_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    lock2.setImageResource(R.drawable.ic_lock_active);
                    new_password.setSelection(new_password.getText().length());
                    new_password.setBackground(getResources().getDrawable(R.drawable.fields_profile_active));
                }
                else {
                    lock2.setImageResource(R.drawable.ic_lock);
                    new_password.setBackground(getResources().getDrawable(R.drawable.fields_profile_inactive));
                }
            }
        });
        new_password_again.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    lock3.setImageResource(R.drawable.ic_lock_active);
                    new_password_again.setSelection(new_password_again.getText().length());
                    new_password_again.setBackground(getResources().getDrawable(R.drawable.fields_profile_active));
                }
                else {
                    lock3.setImageResource(R.drawable.ic_lock);
                    new_password_again.setBackground(getResources().getDrawable(R.drawable.fields_profile_inactive));
                }
            }
        });


    }
    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean isNameChanged () {
        return (!name.equals(profile_name.getText().toString()));
    }

    private boolean isEmailChanged () {
        return (!email.equals(profile_email.getText().toString()));
    }

    private boolean check () {
        return (isNameChanged() || (profile_email.getText().length() >= 8 || profile_email.getText().length() == 0)
                && isEmailChanged());
    }

    private boolean isPassChanged () {
        return (old_password.getText().length() >= 8
                && new_password.getText().length() >= 8 && new_password_again.getText().length() >= 8
                && new_password.getText().length() == new_password_again.getText().length());
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
}
