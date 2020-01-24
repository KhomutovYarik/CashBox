package com.example.cashbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class register extends AppCompatActivity {
    private EditText phoneNumber;
    private TextView btnLog;
    private MaskImpl inputMask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        preparing();
    }

    private void preparing()
    {
        phoneNumber = findViewById(R.id.phoneNumber);
        btnLog = findViewById(R.id.login);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(register.this, MainActivity.class);
                startActivity(main);
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
    }
}
