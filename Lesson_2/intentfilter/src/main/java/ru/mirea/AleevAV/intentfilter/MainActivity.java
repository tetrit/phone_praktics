package ru.mirea.AleevAV.intentfilter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void OnClickURL(View view){
        Uri address = Uri.parse("https://www.mirea.ru/");
        Intent openLink = new Intent(Intent.ACTION_VIEW, address);
        startActivity(openLink);
    }

    public void OnclickSubmit(View view){
        EditText textFIO = findViewById(R.id.editTextFIO);
        EditText textUniver = findViewById(R.id.editTextUniver);



        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("textFIO", textFIO.getText().toString());
        intent.putExtra("textUniver", textUniver.getText().toString());
        startActivity(intent);
    }

    public void OnClickShare(View view){
        EditText textFIO = findViewById(R.id.editTextFIO);
        EditText textUniver = findViewById(R.id.editTextUniver);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MIREA");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "ФИО: " + textFIO.getText().toString()+ " | Универ: " + textUniver.getText().toString());
        startActivity(Intent.createChooser(shareIntent, "МОИ ФИО и название универа"));
    }
}