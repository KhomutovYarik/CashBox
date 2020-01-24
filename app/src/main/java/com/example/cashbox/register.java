package com.example.cashbox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class register extends AppCompatActivity {
    private EditText phoneNumber, code;
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
        codeLabel = findViewById(R.id.code2Label);
        code = findViewById(R.id.code2);
        recCode = findViewById(R.id.joinButton);
        register = findViewById(R.id.regButton);
        phoneNumber = findViewById(R.id.phoneNumber);
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
                                        register.setVisibility(View.VISIBLE);
                                        register.setTextColor(getResources().getColor(R.color.colorPrimary));
                                        register.setBackground(getResources().getDrawable(R.drawable.whitebuttonstyle));
                                        register.setEnabled(true);
                                        recCode.setVisibility(View.INVISIBLE);
                                        resend.setVisibility(View.VISIBLE);
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }
}
