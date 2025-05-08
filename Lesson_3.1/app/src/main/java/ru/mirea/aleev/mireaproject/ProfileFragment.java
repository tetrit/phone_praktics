package ru.mirea.aleev.mireaproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private EditText editTextName, editTextAge, editTextEmail;
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Инициализация элементов
        editTextName = view.findViewById(R.id.editTextName);
        editTextAge = view.findViewById(R.id.editTextAge);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        Button buttonSave = view.findViewById(R.id.buttonSaveProfile);

        // Инициализация SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE);

        // Загрузка сохраненных данных
        loadProfileData();

        // Сохранение данных
        buttonSave.setOnClickListener(v -> saveProfileData());

        return view;
    }

    // Метод для сохранения данных
    private void saveProfileData() {
        String name = editTextName.getText().toString();
        int age = Integer.parseInt(editTextAge.getText().toString());
        String email = editTextEmail.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name)
                .putInt("age", age)
                .putString("email", email)
                .apply();

        // Уведомление пользователя
        Toast.makeText(getContext(), "Данные сохранены!", Toast.LENGTH_SHORT).show();
    }

    // Метод для загрузки данных
    private void loadProfileData() {
        editTextName.setText(sharedPreferences.getString("name", ""));
        editTextAge.setText(String.valueOf(sharedPreferences.getInt("age", 0)));
        editTextEmail.setText(sharedPreferences.getString("email", ""));
    }
}