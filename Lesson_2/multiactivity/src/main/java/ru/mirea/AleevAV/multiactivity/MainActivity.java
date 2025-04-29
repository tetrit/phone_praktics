package ru.mirea.AleevAV.multiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "ActivityLifeCycle";


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
        Log.d(TAG, "метод OnCreate сработал!");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "Метод onResume Сработал!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "Метод onStop Сработал!");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "Метод onRestart Сработал!");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "метод onDestroy Сработал!");
    }

    public void onClickToNextActivity(View view){
        Log.d("Button", "Кнопка нажата");
        Intent intent = new Intent(this, SecondActivity.class);
        EditText editText = findViewById(R.id.EditText);
        intent.putExtra("key", editText.getText().toString());
        startActivity(intent);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "метод OnStart сработал!");
    }

}