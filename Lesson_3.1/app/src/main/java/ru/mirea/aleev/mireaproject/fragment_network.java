package ru.mirea.aleev.mireaproject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class fragment_network extends Fragment {

    private static final String TAG = "NetworkFragment";
    private TextView textViewNetworkData;
    private Button buttonRefreshNetwork;
    // Можно использовать любой публичный API. Например, https://jsonplaceholder.typicode.com/todos/1
    // или как в вашем примере https://ipinfo.io/json
    private String targetUrl = "https://ipinfo.io/json"; // Можно заменить на другой

    public fragment_network() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_network, container, false);
        textViewNetworkData = view.findViewById(R.id.textViewNetworkData);
        buttonRefreshNetwork = view.findViewById(R.id.buttonRefreshNetwork);

        buttonRefreshNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNetworkData();
            }
        });

        loadNetworkData(); // Загружаем данные при создании View
        return view;
    }

    private void loadNetworkData() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = null;
        if (connectivityManager != null) {
            networkinfo = connectivityManager.getActiveNetworkInfo();
        }
        if (networkinfo != null && networkinfo.isConnected()) {
            new DownloadPageTask().execute(targetUrl);
        } else {
            Toast.makeText(getActivity(), "Нет интернет-соединения", Toast.LENGTH_SHORT).show();
            textViewNetworkData.setText("Нет интернет-соединения. Проверьте подключение и нажмите 'Обновить'.");
        }
    }

    private class DownloadPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textViewNetworkData.setText("Загрузка...");
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadIpInfo(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return "Ошибка: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "Raw result: " + result);
            // Простая обработка для ipinfo.io
            // Для более сложного JSON используйте Gson или стандартные JSONObject/JSONArray
            try {
                JSONObject responseJson = new JSONObject(result);
                String ip = responseJson.optString("ip", "N/A");
                String city = responseJson.optString("city", "N/A");
                String region = responseJson.optString("region", "N/A");
                String country = responseJson.optString("country", "N/A");
                String org = responseJson.optString("org", "N/A");

                String formattedResult = "IP: " + ip + "\n" +
                        "Город: " + city + "\n" +
                        "Регион: " + region + "\n" +
                        "Страна: " + country + "\n" +
                        "Организация: " + org;
                textViewNetworkData.setText(formattedResult);

            } catch (JSONException e) {
                Log.e(TAG, "JSON parsing error: ", e);
                textViewNetworkData.setText("Ошибка парсинга данных или неверный формат ответа.\n" + result);
            }
            super.onPostExecute(result);
        }
    }

    private String downloadIpInfo(String address) throws IOException {
        InputStream inputStream = null;
        String data = "";
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(100000);
            connection.setConnectTimeout(100000);
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
                inputStream = connection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int read = 0;
                while ((read = inputStream.read()) != -1) {
                    bos.write(read);
                }
                bos.close();
                data = bos.toString();
            } else {
                data = "Ошибка: " + connection.getResponseMessage() + ". Код: " + responseCode;
            }
            connection.disconnect();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return data;
    }
}