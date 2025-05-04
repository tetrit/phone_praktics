package ru.mirea.aleev.rumireaaleevavlesson4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.aleev.rumireaaleevavlesson4.databinding.ActivityMainBinding;
import ru.mirea.aleev.rumireaaleevavlesson4.databinding.ActivityMusicBinding;

public class MusicActivity extends AppCompatActivity {
    private ActivityMusicBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivityMusicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent info = getIntent();
        binding.textMusicName.setText(info.getStringExtra("music_name"));

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("music_name", binding.textMusicName.getText().toString());
                startActivity(intent);
            }
        });
    }

}