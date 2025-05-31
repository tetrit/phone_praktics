package ru.mirea.aleev.rumireaaleevlesson7;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private Button button;
    private final String HOST = "time.nist.gov";
    private final int PORT = 13;
    private static final String TAG = "TimeService";

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

        text = findViewById(R.id.Text);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetTimeTask timeTask = new GetTimeTask();
                timeTask.execute();
            }
        });
    }

    private class GetTimeTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            text.setText("Loading...");
            button.setEnabled(false);
        }

        @Override
        protected String doInBackground(Void... params) {
            String timeResult = null; // Инициализируем как null
            Socket socket = null;
            BufferedReader reader = null;
            int retries = 0;
            final int MAX_RETRIES = 5; // Максимальное количество попыток прочитать не-null строку

            try {
                Log.d(TAG, "Attempting to connect to " + HOST + ":" + PORT);
                socket = new Socket();
                // Установим таймаут на соединение и чтение, чтобы не ждать вечно
                socket.connect(new java.net.InetSocketAddress(HOST, PORT), 5000); // 5 секунд на соединение
                socket.setSoTimeout(5000); // 5 секунд на ожидание данных при чтении

                Log.d(TAG, "Connected. Getting reader.");
                reader = SocketUtils.getReader(socket);

                // Пытаемся прочитать валидную строку
                // Сервер времени может отправлять сначала служебную информацию или пустые строки
                String line;
                while ((line = reader.readLine()) != null && retries < MAX_RETRIES) {
                    Log.d(TAG, "Read line: " + line);
                    // Проверяем, что строка не пустая и содержит что-то похожее на дату/время
                    // Примерный формат "YY-MM-DD" и "HH:MM:SS"
                    if (!line.trim().isEmpty() && line.contains("-") && line.contains(":")) {
                        timeResult = line;
                        break; // Нашли подходящую строку
                    }
                    retries++;
                }

                if (timeResult == null) {
                    Log.w(TAG, "Could not get a valid time string after " + retries + " attempts or stream ended.");
                    return "Error: No valid data received from server.";
                }

                Log.d(TAG, "Raw timeResult (final): " + timeResult);

            } catch (java.net.SocketTimeoutException e) {
                Log.e(TAG, "SocketTimeoutException: " + e.getMessage());
                return "Error: Connection or read timed out.";
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
                e.printStackTrace();
                return "Error: Could not connect or read from server.";
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Error closing resources: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            return timeResult;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            button.setEnabled(true); // Разблокируем кнопку

            if (result != null && !result.startsWith("Error:")) {
                // Пример строки от сервера: 60241 23-10-27 10:06:11 50 0 0 795.2 UTC(NIST) *
                // Нам нужны части "23-10-27" (дата) и "10:06:11" (время)
                // Строка разделена пробелами. Дата находится на 2-й позиции (индекс 1), время на 3-й (индекс 2).
                String[] parts = result.split(" ");
                if (parts.length >= 3) {
                    String date = parts[1]; // YR-MM-DD
                    String time = parts[2]; // HH:MM:SS
                    text.setText("Date: " + date + "\nTime: " + time);
                } else {
                    text.setText("Error: Malformed server response.\nRaw: " + result);
                    Log.e(TAG, "Malformed server response: " + result);
                }
            } else {
                text.setText(result); // Отображаем сообщение об ошибке
            }
        }
    }
}