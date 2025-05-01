package ru.mirea.aleev.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.net.DnsResolver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> activityResultLauncher;
    public static final String BOOK_NAME_KEY = "book_name";
    public static final String QUOTES_KEY = "quotes_name";
    public static final String USER_MESSAGE = "MESSAGE";
    private TextView textViewUserBook;

    public Intent intent;

    String book;
    String quote;

    TextView main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewUserBook = findViewById(R.id.TextMain);
        ActivityResultCallback<ActivityResult> callback = new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    String userBook = data.getStringExtra(USER_MESSAGE);
                    textViewUserBook.setText(userBook);
                }
            }
        };
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                callback);
        intent = new Intent(this, ShareActivity.class);
        book = "!!!ВАША КНИГА, А НЕ МОЯ!!!";
        quote = "!!!ВАША ЦИТАТА, А НЕ МОЯ!!!";

        intent.putExtra(BOOK_NAME_KEY, book);
        intent.putExtra(QUOTES_KEY, quote);

        main = findViewById(R.id.TextMain);

    }


    public void OnClickgetInfoAboutBook(View view) {

        activityResultLauncher.launch(intent);
    }



}