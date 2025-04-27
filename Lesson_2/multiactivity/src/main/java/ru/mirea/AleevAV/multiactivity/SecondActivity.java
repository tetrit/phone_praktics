package ru.mirea.AleevAV.multiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "ActivityLifeCycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String resieveddata = (String) getIntent().getSerializableExtra("key");

        TextView textSecond = findViewById(R.id.TextSecond);

        textSecond.setText(resieveddata);

        Log.d(TAG, "Метод onCreate Сработал! (Second Activity)");
    }



    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "Метод onResume Сработал! (Second Activity)");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "Метод onStop Сработал! (Second Activity)");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "Метод onRestart Сработал! (Second Activity)");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "метод onDestroy Сработал! (Second Activity)");
    }


    public void OnclickBackToMain(View view){
        Log.d("button", "кнопка нажата");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "метод OnStart сработал! (Second Activity)");
    }
}