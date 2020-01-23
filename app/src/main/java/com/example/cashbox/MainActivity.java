package com.example.cashbox;

import androidx.appcompat.app.AppCompatActivity;

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
    Button btn;
    MaskImpl inputMask;
    TextView tv;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        inputMask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER);

        FormatWatcher formatWatcher = new MaskFormatWatcher(inputMask);
        editText = findViewById(R.id.phoneNum);
        formatWatcher.installOn(editText); // тут аргументом может быть любой TextView

        View.OnClickListener onclkLst = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                tv = findViewById(R.id.textView);
                inputMask.insertFront(editText.getText());
                tv.setText(inputMask.toUnformattedString());
            }
        };
        btn.setOnClickListener(onclkLst);
    }


}
