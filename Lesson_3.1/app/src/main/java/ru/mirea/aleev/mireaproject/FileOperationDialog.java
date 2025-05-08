package ru.mirea.aleev.mireaproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class FileOperationDialog extends DialogFragment {
    private OnOperationSelectedListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_file_operation, null);

        Spinner spinnerOperation = view.findViewById(R.id.spinnerOperation);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.file_operations,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOperation.setAdapter(adapter);

        builder.setView(view)
                .setTitle("Выберите операцию")
                .setPositiveButton("Далее", (dialog, id) -> {
                    String selectedOperation = spinnerOperation.getSelectedItem().toString();
                    listener.onOperationSelected(selectedOperation);
                })
                .setNegativeButton("Отмена", (dialog, id) -> dismiss());

        return builder.create();
    }

    public interface OnOperationSelectedListener {
        void onOperationSelected(String operation);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Получаем родительский фрагмент, а не активность
        Fragment parentFragment = getTargetFragment();
        if (parentFragment instanceof OnOperationSelectedListener) {
            listener = (OnOperationSelectedListener) parentFragment;
        } else {
            throw new ClassCastException(parentFragment + " должен реализовывать OnOperationSelectedListener");
        }
    }
}