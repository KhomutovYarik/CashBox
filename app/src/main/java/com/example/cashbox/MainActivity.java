package com.example.cashbox;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class MainActivity extends AppCompatActivity {

    private EditText phoneNumber, code;
    private TextView phoneNumberLbl, codeLbl;
    private Button send, resend, signin;
    private MaskImpl inputMask;

    FirebaseAuth fbauth;
    String sentCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActiveOrdersFragment.activeOrdersList = new ArrayList<ActiveOrder>();
        FinishedOrdersFragment.finishedOrdersList = new ArrayList<FinishedOrder>();
        fbauth = FirebaseAuth.getInstance();

        if (fbauth.getCurrentUser() != null)
        {
            Intent ordersActivity = new Intent(MainActivity.this, OrdersActivity.class);
            startActivity(ordersActivity);
            finish();
        }

        preparing();
    }

    private void preparing()
    {
        phoneNumberLbl = findViewById(R.id.phoneNumberLabel);
        codeLbl = findViewById(R.id.codeLabel);
        phoneNumber = findViewById(R.id.phoneNumber);
        code = findViewById(R.id.code);
        send = findViewById(R.id.getCodeButton);
        resend = findViewById(R.id.resendCode);
        signin = findViewById(R.id.signinButton);

        codeLbl.setVisibility(View.INVISIBLE);

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
                    send.setTextColor(getResources().getColor(R.color.colorPrimary));
                    send.setBackground(getResources().getDrawable(R.drawable.whitebuttonstyle));
                    send.setEnabled(true);
                }
                else {
                    send.setTextColor(getResources().getColor(R.color.inactiveColor));
                    send.setBackground(getResources().getDrawable(R.drawable.notenabledbutton));
                    send.setEnabled(false);
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    sendVerificationCode();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCode();
            }
        });
    }

    private void sendVerificationCode()
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber.getText().toString(),        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            sentCode = s;
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Код отправлен")
                    .setMessage("Код подтверждения отправлен на указанный Вами номер телефона")
                    .setCancelable(false)
                    .setPositiveButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    code.requestFocus();
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();

            phoneNumber.setEnabled(false);
            send.setVisibility(View.INVISIBLE);
            codeLbl.setVisibility(View.VISIBLE);
            code.setVisibility(View.VISIBLE);
            resend.setVisibility(View.VISIBLE);
            signin.setVisibility(View.VISIBLE);
        }
    };

    private void checkCode()
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(sentCode, code.getText().toString());
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                            builder.setTitle("Вход выполнен")
//                                    .setMessage("Вы вошли в систему")
//                                    .setCancelable(false)
//                                    .setPositiveButton("ОК",
//                                            new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialog, int id) {
//                                                    dialog.cancel();
//
//                                                }
//                                            });
//                            AlertDialog alert = builder.create();
//                            alert.show();
                            Intent ordersActivity = new Intent(MainActivity.this, OrdersActivity.class);
                            startActivity(ordersActivity);
                            finish();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Введён неверный код")
                                        .setMessage("Вы ввели неверный код, попробуйте ещё раз")
                                        .setCancelable(false)
                                        .setPositiveButton("ОК",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        }
                    }
                });
    }
}


//public class MainActivity extends AppCompatActivity {
//
//    private EditText phoneNumber, password;
//    private TextView phoneNumberLbl, forgetPasswordLabel;
//    private EditText editText;
//    private Button loginButton, btn;
//    private TextView btnRega;
//    private MaskImpl inputMask;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        preparing();
//        //test();
//    }
//    private void test() {
//        Button testbtn = findViewById(R.id.test);
//        testbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent test = new Intent(MainActivity.this, AddStore.class);
//                startActivity(test);
//            }
//        });
//
//        Button testbutton = findViewById(R.id.addCashBox);
//        testbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent test = new Intent(MainActivity.this, bottom_navbar.class);
//                startActivity(test);
//            }
//        });
//    }
//
//    private void preparing()
//    {
//        editText = findViewById(R.id.editText);
//        editText.requestFocus();
//        phoneNumber = findViewById(R.id.phoneNumber);
//        password = findViewById(R.id.password);
//        loginButton = findViewById(R.id.loginButton);
//        btn = findViewById(R.id.btn);
//        btnRega = findViewById(R.id.register);
//        forgetPasswordLabel = findViewById(R.id.forgetPasswordButton);
//        phoneNumber.setText("+7 (924) 234-12-63");
//        password.setText("7777777");
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (String.valueOf(password.getText()).equals("77777777"))
//                {
//                    Intent orders = new Intent(MainActivity.this, OrdersActivity.class);
//                    startActivity(orders);
//                }
//                else
//                {
//                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this)
//                            .setCancelable(false)
//                            .setTitle("Некорректные данные")
//                            .setMessage("Введён неверный номер телефона или пароль")
//                            .setPositiveButton("ОК", null);
//                    AlertDialog alert = dialogBuilder.create();
//                    alert.show();
//                }
//            }
//        });
//        btnRega.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent reg = new Intent(MainActivity.this, Register.class);
//                startActivity(reg);
//            }
//        });
//
//
//        inputMask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER);
//        FormatWatcher formatWatcher = new MaskFormatWatcher(inputMask);
//
//
//        phoneNumberLbl = findViewById(R.id.phoneNumberText);
//        formatWatcher.installOn(phoneNumber);
//        //phoneNumber.setSelection(phoneNumber.getText().length());
//        View.OnClickListener onclkLst = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                inputMask.clear();
//                inputMask.insertFront(phoneNumber.getText());
//                phoneNumberLbl.setText(inputMask.toUnformattedString());
//            }
//        };
//        btn.setOnClickListener(onclkLst);
//        phoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                String phone = phoneNumber.getText().toString();
//                if (hasFocus) {
//                    if (phone.length() == 0) {
//                        phoneNumber.setText("7");
//                        //phoneNumber.requestFocus();
//                    }
//                    else {
//                        phoneNumber.setTextColor(getResources().getColor(R.color.textColor));
//                    }
//                    phoneNumber.setSelection(phoneNumber.getText().length());
//                } else {
//                    if (phone.length() == 4) {
//                        phoneNumber.setTextColor(getResources().getColor(R.color.inactiveColor));
//                    }
//                }
//            }
//        });
//
//        phoneNumber.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (phoneNumber.getText().length() == 18 && password.getText().length() >= 8) {
//                    loginButton.setTextColor(getResources().getColor(R.color.colorPrimary));
//                    loginButton.setBackground(getResources().getDrawable(R.drawable.whitebuttonstyle));
//                    loginButton.setEnabled(true);
//                }
//                else {
//                    loginButton.setTextColor(getResources().getColor(R.color.inactiveColor));
//                    loginButton.setBackground(getResources().getDrawable(R.drawable.notenabledbutton));
//                    loginButton.setEnabled(false);
//                }
//            }
//        });
//
//        password.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (phoneNumber.getText().length() == 18 && password.getText().length() >= 8) {
//                    loginButton.setTextColor(getResources().getColor(R.color.colorPrimary));
//                    loginButton.setBackground(getResources().getDrawable(R.drawable.whitebuttonstyle));
//                    loginButton.setEnabled(true);
//                }
//                else {
//                    loginButton.setTextColor(getResources().getColor(R.color.inactiveColor));
//                    loginButton.setBackground(getResources().getDrawable(R.drawable.notenabledbutton));
//                    loginButton.setEnabled(false);
//                }
//            }
//        });
//
//        forgetPasswordLabel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Intent forgetPasswordPage = new Intent(MainActivity.this, ForgetPasswordActivity.class);
//                if (phoneNumber.getText().length()>4) {
//
//                    forgetPasswordPage.putExtra("phone", phoneNumber.getText().toString());
//                }
//                startActivity(forgetPasswordPage);
//            }
//        });
//
//        //loginButton.performClick();
//    }
//}
