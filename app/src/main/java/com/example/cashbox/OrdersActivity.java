package com.example.cashbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrdersActivity extends AppCompatActivity {
    private Button activeButton, finishedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        preparing();
    }

    private void preparing()
    {
        activeButton = findViewById(R.id.activeOrdersButton);
        finishedButton = findViewById(R.id.finishedOrdersButton);
        activeButton.setBackground(getResources().getDrawable(R.drawable.focuschangedbutton));
        activeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeButton.setBackground(getResources().getDrawable(R.drawable.focuschangedbutton));
                finishedButton.setBackground(getResources().getDrawable(R.drawable.nonfocuschangedbutton));
            }
        });
        finishedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeButton.setBackground(getResources().getDrawable(R.drawable.nonfocuschangedbutton));
                finishedButton.setBackground(getResources().getDrawable(R.drawable.focuschangedbutton));
            }
        });
    }
}
