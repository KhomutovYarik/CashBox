package com.example.cashbox;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class ForgetPasswordActivity extends AppCompatActivity {
    private EditText phoneNumber, smsText, newPassword;
    private TextView smsLabel, CBServiceLabel, newpasswordLabel, backToAuthButton;
    private Button getCodeButton, updatePasswordButton;
    private MaskImpl inputMask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);
        preparing();
    }

    private void preparing()
    {
        phoneNumber = findViewById(R.id.phoneNumber);
        smsText = findViewById(R.id.smsText);
        newPassword = findViewById(R.id.newPassword);
        smsLabel = findViewById(R.id.smsLabel);
        CBServiceLabel = findViewById(R.id.CBServiceLabel);
        newpasswordLabel = findViewById(R.id.newPasswordLabel);
        getCodeButton = findViewById(R.id.getCodeButton);
        updatePasswordButton = findViewById(R.id.updatePasswordButton);
        backToAuthButton = findViewById(R.id.backToAuthButton);

        backToAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        updatePasswordButton.setVisibility(View.INVISIBLE);

        getCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCodeButton.setVisibility(View.INVISIBLE);
                smsLabel.setVisibility(View.VISIBLE);
                smsText.setVisibility(View.VISIBLE);
                newpasswordLabel.setVisibility(View.VISIBLE);
                newPassword.setVisibility(View.VISIBLE);
                updatePasswordButton.setVisibility(View.VISIBLE);
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)CBServiceLabel.getLayoutParams();
                params.setMargins(0, 300, 0, 0);
                CBServiceLabel.setLayoutParams(params);
                phoneNumber.setEnabled(false);
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
                    getCodeButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                    getCodeButton.setBackground(getResources().getDrawable(R.drawable.whitebuttonstyle));
                    getCodeButton.setEnabled(true);
                }
                else {
                    getCodeButton.setTextColor(getResources().getColor(R.color.inactiveColor));
                    getCodeButton.setBackground(getResources().getDrawable(R.drawable.notenabledbutton));
                    getCodeButton.setEnabled(false);
                }
            }
        });

        smsText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (smsText.getText().length() > 6)
                {
                    String str = String.valueOf(smsText.getText());
                    str = str.substring(0, 6);
                    smsText.setText(str);
                    smsText.setSelection(smsText.getText().length());
                }
                else
                {
                    if (smsText.getText().length() == 6 && newPassword.getText().length() >= 8) {
                        updatePasswordButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                        updatePasswordButton.setBackground(getResources().getDrawable(R.drawable.whitebuttonstyle));
                        updatePasswordButton.setEnabled(true);
                    }
                    else {
                        updatePasswordButton.setTextColor(getResources().getColor(R.color.inactiveColor));
                        updatePasswordButton.setBackground(getResources().getDrawable(R.drawable.notenabledbutton));
                        updatePasswordButton.setEnabled(false);
                    }
                }
            }
        });

        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (newPassword.getText().length() > 16)
                {
                    String str = String.valueOf(newPassword.getText());
                    str = str.substring(0, 16);
                    newPassword.setText(str);
                    newPassword.setSelection(newPassword.getText().length());
                }
                else
                {
                    if (smsText.getText().length() == 6 && newPassword.getText().length() >= 8) {
                        updatePasswordButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                        updatePasswordButton.setBackground(getResources().getDrawable(R.drawable.whitebuttonstyle));
                        updatePasswordButton.setEnabled(true);
                    }
                    else {
                        updatePasswordButton.setTextColor(getResources().getColor(R.color.inactiveColor));
                        updatePasswordButton.setBackground(getResources().getDrawable(R.drawable.notenabledbutton));
                        updatePasswordButton.setEnabled(false);
                    }
                }
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
