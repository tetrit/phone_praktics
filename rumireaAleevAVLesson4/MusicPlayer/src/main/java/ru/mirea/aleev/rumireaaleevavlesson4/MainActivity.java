package ru.mirea.aleev.rumireaaleevavlesson4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.aleev.rumireaaleevavlesson4.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

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
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.textViewMirea.setText("Введите название песни");
        if (getIntent() != null){
            Intent name = getIntent();
            binding.editTextMirea.setText(name.getStringExtra("music_name"));
        }

        binding.editTextMirea.setHint("Название песни");


        binding.buttonMirea.setText("Перейти в плеер");
        binding.buttonMirea.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), MusicActivity.class);
                intent.putExtra("music_name", binding.editTextMirea.getText().toString());
                Log.d(MainActivity.class.getSimpleName(), "onClickListener");
                startActivity(intent);
            }
        });
    }


}