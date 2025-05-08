package ru.mirea.aleev.mireaproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileWorkFragment extends Fragment implements FileOperationDialog.OnOperationSelectedListener {

    private RecyclerView recyclerViewFiles;
    private FileAdapter fileAdapter;
    private List<File> fileList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_work, container, false);

        // Инициализация RecyclerView
        recyclerViewFiles = view.findViewById(R.id.recyclerViewFiles);
        recyclerViewFiles.setLayoutManager(new LinearLayoutManager(requireContext()));
        fileAdapter = new FileAdapter(fileList);
        recyclerViewFiles.setAdapter(fileAdapter);

        // Обработка FAB
        FloatingActionButton fab = view.findViewById(R.id.fabAddFile);
        fab.setOnClickListener(v -> showOperationDialog());

        loadFiles();
        return view;
    }

    private void showOperationDialog() {
        FileOperationDialog dialog = new FileOperationDialog();
        dialog.setTargetFragment(this, 0);
        dialog.show(getParentFragmentManager(), "file_operation_dialog");
    }

    @Override
    public void onOperationSelected(String operation) {
        switch (operation) {
            case "Шифровать файл":
                encryptFile();
                break;
            case "Дешифровать файл":
                if (!fileList.isEmpty()) decryptFile(fileList.get(0));
                else Toast.makeText(requireContext(), "Нет файлов для дешифровки", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void encryptFile() {
        try {
            // Пример данных - замените на реальный ввод
            String secretData = "Секретные данные: " + System.currentTimeMillis();

            byte[] encryptedData = CryptoUtils.encrypt(secretData.getBytes(StandardCharsets.UTF_8));

            // Сохранение в файл
            File dir = new File(requireContext().getFilesDir(), "encrypted");
            if (!dir.exists() && !dir.mkdir()) {
                throw new Exception("Не удалось создать директорию");
            }

            File outputFile = new File(dir, "encrypted_" + System.currentTimeMillis() + ".enc");
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(encryptedData);
            }

            loadFiles();
            Toast.makeText(requireContext(), "Файл зашифрован: " + outputFile.getName(), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(requireContext(), "Ошибка шифрования: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void decryptFile(File file) {
        try {
            byte[] fileData = readFileToByteArray(file);
            byte[] decryptedData = CryptoUtils.decrypt(fileData);
            String result = new String(decryptedData, StandardCharsets.UTF_8);

            Toast.makeText(requireContext(),
                    "Дешифровано:\n" + result,
                    Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(requireContext(), "Ошибка дешифровки: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void loadFiles() {
        File dir = new File(requireContext().getFilesDir(), "encrypted");
        if (!dir.exists()) return;

        File[] files = dir.listFiles();
        if (files != null) {
            fileList.clear();
            for (File file : files) {
                if (file.isFile()) fileList.add(file);
            }
            fileAdapter.notifyDataSetChanged();
        }
    }

    private byte[] readFileToByteArray(File file) throws Exception {
        try (java.io.FileInputStream fis = new java.io.FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toByteArray();
        }
    }
}