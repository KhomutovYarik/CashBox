package com.example.cashbox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class register extends AppCompatActivity {
    private EditText phoneNumber, code, name, password;
    private TextView btnLog, codeLabel, phoneLabel;
    private MaskImpl inputMask;
    private Button recCode, register, resend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        preparing();
    }

    private void preparing()
    {
        resend = findViewById(R.id.resend);
        phoneLabel = findViewById(R.id.phoneNumberText);
        codeLabel = findViewById(R.id.codeLabel);
        code = findViewById(R.id.code);
        recCode = findViewById(R.id.joinButton);
        register = findViewById(R.id.regButton);
        password = findViewById(R.id.password);
        phoneNumber = findViewById(R.id.phoneNumber);
        name = findViewById(R.id.name);
        btnLog = findViewById(R.id.backToAuthButton);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        inputMask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER);
        FormatWatcher formatWatcher = new MaskFormatWatcher(inputMask);
        formatWatcher.installOn(phoneNumber);
        
        phoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String phone = phoneNumber.getText().toString();
                if (hasFocus) {
                    if (phone.length() == 0) {
                        phoneNumber.setText("7");
                        //phoneNumber.requestFocus();
                    }
                    else {
                        phoneNumber.setTextColor(getResources().getColor(R.color.textColor));
                    }
                    phoneNumber.setSelection(phoneNumber.getText().length());
                } else {
                    if (phone.length() == 4) {
                        phoneNumber.setTextColor(getResources().getColor(R.color.inactiveColor));
                    }
                }
            }
        });
        recCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                builder.setTitle("Код отправлен!")
                        .setMessage("Код подтверждения отправлен на указанный Вами номер телефона")
                        .setCancelable(false)
                        .setNegativeButton("ОК",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        phoneNumber.setVisibility(View.INVISIBLE);
                                        phoneLabel.setVisibility(View.INVISIBLE);
                                        codeLabel.setVisibility(View.VISIBLE);
                                        code.setVisibility(View.VISIBLE);

                                        recCode.setVisibility(View.INVISIBLE);
                                        resend.setVisibility(View.VISIBLE);
                                        code.requestFocus();
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                builder.setTitle("Код отправлен!")
                        .setMessage("Код подтверждения отправлен на указанный Вами номер телефона")
                        .setCancelable(false)
                        .setNegativeButton("ОК",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        code.requestFocus();
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (phoneNumber.getText().length() == 18) {
                    recCode.setTextColor(getResources().getColor(R.color.colorPrimary));
                    recCode.setBackground(getResources().getDrawable(R.drawable.whitebuttonstyle));
                    recCode.setEnabled(true);
                }
                else {
                    recCode.setTextColor(getResources().getColor(R.color.inactiveColor));
                    recCode.setBackground(getResources().getDrawable(R.drawable.notenabledbutton));
                    recCode.setEnabled(false);
                }
            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (name.getText().length() > 15)
                {
                    String str = String.valueOf(name.getText());
                    str = str.substring(0, 15);
                    name.setText(str);
                    name.setSelection(name.getText().length());
                }
                else
                {
                    if (name.getText().length() >= 3 && code.getText().length() == 6 && password.getText().length() >= 8) {
                        register.setTextColor(getResources().getColor(R.color.colorPrimary));
                        register.setBackground(getResources().getDrawable(R.drawable.whitebuttonstyle));
                        register.setEnabled(true);
                    }
                    else {
                        register.setTextColor(getResources().getColor(R.color.inactiveColor));
                        register.setBackground(getResources().getDrawable(R.drawable.notenabledbutton));
                        register.setEnabled(false);
                    }
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (password.getText().length() > 16)
                {
                    String str = String.valueOf(password.getText());
                    str = str.substring(0, 16);
                    password.setText(str);
                    password.setSelection(password.getText().length());
                }
                else
                {
                    if (name.getText().length() >= 3 && code.getText().length() == 6 && password.getText().length() >= 8) {
                        register.setTextColor(getResources().getColor(R.color.colorPrimary));
                        register.setBackground(getResources().getDrawable(R.drawable.whitebuttonstyle));
                        register.setEnabled(true);
                    }
                    else {
                        register.setTextColor(getResources().getColor(R.color.inactiveColor));
                        register.setBackground(getResources().getDrawable(R.drawable.notenabledbutton));
                        register.setEnabled(false);
                    }
                }
            }
        });

        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (code.getText().length() > 6)
                {
                    String str = String.valueOf(code.getText());
                    str = str.substring(0, 6);
                    code.setText(str);
                    code.setSelection(code.getText().length());
                }
                else
                {
                    if (code.getText().length() == 6 && password.getText().length() >= 8 && name.getText().length() >= 3) {
                        register.setTextColor(getResources().getColor(R.color.colorPrimary));
                        register.setBackground(getResources().getDrawable(R.drawable.whitebuttonstyle));
                        register.setEnabled(true);
                    }
                    else {
                        register.setTextColor(getResources().getColor(R.color.inactiveColor));
                        register.setBackground(getResources().getDrawable(R.drawable.notenabledbutton));
                        register.setEnabled(false);
                    }
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                builder.setTitle("Поздравляем!")
                        .setMessage("Вы успешно зарегистрировались!")
                        .setCancelable(false)
                        .setNegativeButton("ОК",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        finish();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}
