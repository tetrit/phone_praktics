package ru.mirea.AleevAV.buttonclicker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        TextView TvOut = findViewById(R.id.TVOut);
        Button btnWho = findViewById(R.id.btnWhoAmI);
        CheckBox check = findViewById(R.id.checkBoxTogle);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View.OnClickListener OCLbtnWho = new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                TvOut.setText("Мой номер по списку № 1");
                check.toggle();
            }
        };

        btnWho.setOnClickListener(OCLbtnWho);

    }

    public void OnClickNotMe(View view){

        TextView TvOut = findViewById(R.id.TVOut);
        CheckBox check = findViewById(R.id.checkBoxTogle);

        TvOut.setText("Это не я сделал");
        check.toggle();
    }
}