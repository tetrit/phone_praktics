package com.AleevAV.layouttype;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Trace;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        TextView MainTV = findViewById(R.id.maintext);
        Button SubmitBt = findViewById(R.id.submitbutton);
        CheckBox TestCheck = findViewById(R.id.CheckTest);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MainTV.setText("Если это сообщение появилось, значит текст в нужнем месте изменился через код.");
        TestCheck.setChecked(true);
        SubmitBt.setText("Submit");

    }
}


