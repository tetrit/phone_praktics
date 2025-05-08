package ru.mirea.aleev.employeedb;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppDatabase db;
    private SuperHeroDao superHeroDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация базы данных
        db = App.getInstance().getDatabase();
        superHeroDao = db.superHeroDao();

        // Добавление супер-героя
        SuperHero hero = new SuperHero("Человек-паук", "Паучьи способности", 10);
        superHeroDao.insert(hero);

        // Получение всех героев
        List<SuperHero> heroes = superHeroDao.getAll();
        Toast.makeText(this, "Сохранено героев: " + heroes.size(), Toast.LENGTH_SHORT).show();
    }
}