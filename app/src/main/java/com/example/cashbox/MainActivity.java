package com.example.cashbox;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    private EditText phoneNumber, password;
    private TextView phoneNumberLbl, forgetPasswordLabel;
    private EditText editText;
    private Button joinButton, btn;
    private  TextView btnRega;
    private MaskImpl inputMask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        editText.requestFocus();
        preparing();
    }

    private void preparing()
    {
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.smsText);
        joinButton = findViewById(R.id.getCodeButton);
        btn = findViewById(R.id.btn);
        btnRega = findViewById(R.id.register);
        forgetPasswordLabel = findViewById(R.id.forgetPasswordButton);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent orders = new Intent(MainActivity.this, OrdersActivity.class);
                startActivity(orders);
            }
        });
        btnRega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(MainActivity.this, register.class);
                startActivity(reg);
            }
        });
        forgetPasswordLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgetPasswordPage = new Intent(MainActivity.this, ForgetPasswordActivity.class);
                startActivity(forgetPasswordPage);
            }
        });

        inputMask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER);
        FormatWatcher formatWatcher = new MaskFormatWatcher(inputMask);


        phoneNumberLbl = findViewById(R.id.phoneNumberText);
        formatWatcher.installOn(phoneNumber);
        //phoneNumber.setSelection(phoneNumber.getText().length());
        View.OnClickListener onclkLst = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                inputMask.insertFront(phoneNumber.getText());
                phoneNumberLbl.setText(inputMask.toUnformattedString());
            }
        };
        btn.setOnClickListener(onclkLst);
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
    }
}
