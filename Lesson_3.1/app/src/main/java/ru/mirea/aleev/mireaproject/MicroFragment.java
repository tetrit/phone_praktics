package ru.mirea.aleev.mireaproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import ru.mirea.aleev.mireaproject.databinding.FragmentMicroBinding;


public class MicroFragment extends Fragment {
    private FragmentMicroBinding binding;
    private static final int REQUEST_CODE_PERMISSION = 200;

    private boolean isWork = false;
    private String fileName = null;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    private boolean isStartRecord = true;
    private boolean isStartPlay = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMicroBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fileName = requireContext().getExternalFilesDir(null).getAbsolutePath() + "/audiorecord.3gp";

        int audioPermissionStatus = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO);
        if (audioPermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_PERMISSION);
        }

        binding.RecordButton.setOnClickListener(v -> {
            if (isStartRecord) {
                startRecord();
                binding.RecordButton.setText("Стоп");
                binding.PlayButton.setEnabled(false);
            } else {
                stopRecord();
                binding.RecordButton.setText("Запись");
                binding.PlayButton.setEnabled(true);
            }
            isStartRecord = !isStartRecord;
        });

        binding.PlayButton.setOnClickListener(v -> {
            if (isStartPlay) {
                startPlay();
                binding.PlayButton.setText("Стоп");
                binding.RecordButton.setEnabled(false);
            } else {
                stopPlay();
                binding.PlayButton.setText("Воспроизведение");
                binding.RecordButton.setEnabled(true);
            }
            isStartPlay = !isStartPlay;
        });
    }

    private void startRecord() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(fileName);
        try {
            recorder.prepare();
            recorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopRecord() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
    }

    private void startPlay() {
        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopPlay() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
