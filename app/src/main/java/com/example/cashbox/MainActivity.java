package com.example.cashbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText phoneNumber, password;
    private Button joinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preparing();
    }

    private void preparing()
    {
        joinButton = (Button)findViewById(R.id.joinButton);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent orders = new Intent(MainActivity.this, OrdersActivity.class);
                startActivity(orders);
            }
        });
    }
}
