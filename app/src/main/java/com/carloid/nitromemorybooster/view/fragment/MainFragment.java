package com.carloid.nitromemorybooster.view.fragment;

import android.animation.ObjectAnimator;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.carloid.nitromemorybooster.R;
import com.carloid.nitromemorybooster.databinding.FragmentMainBinding;
import com.carloid.nitromemorybooster.util.U;

import java.util.Locale;
import java.util.Objects;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainFragment extends Fragment {

    private static final int PERMISSION_REQUEST = 10001;
    private static final String[] PERMISSION_LIST = { READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE };

    private FragmentMainBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        requestPermissions(PERMISSION_LIST, PERMISSION_REQUEST);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        components();

        U.listDirContents(Environment.getExternalStorageDirectory());

        return binding.getRoot();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST) {
            for (int grantIterator : grantResults) {
                if (grantIterator != PackageManager.PERMISSION_GRANTED) {
                    Objects.requireNonNull(getActivity()).finish();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void components() {
        final float usable = getUsableMemory();
        final float free = getFreeMemory();
        final int percentage = (int)(100.0f * (usable - free) / usable);

        binding.txtUsable.append(String.format(Locale.getDefault(), "%.1f GB", usable));
        binding.txtFree.append(String.format(Locale.getDefault(), "%.1f GB", free));
        binding.txtOSUsage.append(String.format(Locale.getDefault(), "%.1f GB", getSystemMemoryUsage()));

        final String percentStr = String.format(Locale.getDefault(), "%d", percentage) + "%";
        binding.percentage.setText(percentStr);

        ObjectAnimator progressAnim = ObjectAnimator.ofInt(binding.progressBar, "progress", 0, percentage);
        progressAnim.setDuration(1000);
        progressAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        progressAnim.start();

        binding.cleanBtn.setOnClickListener(v -> {
            Log.d("TEST", "Click");
        });
    }

    private float getUsableMemory() {
        return U.getPathTotalSpace(Environment.getExternalStorageDirectory().getPath());
    }

    private float getFreeMemory() {
        return U.getPathFreeSpace(Environment.getExternalStorageDirectory().getPath());
    }

    private float getSystemMemoryUsage() {
        float product = U.getPathTotalSpace("/product");
        float system  = U.getPathTotalSpace("/system");

        return product + system;
    }

    private float getImageFilesUsage() {
        return U.measureFileTypeUsage(".png", ".bmp", ".jpg", ".jpeg", ".gif", ".webp");
    }

    private float getAudioFilesUsage() {
        return U.measureFileTypeUsage(".wav", ".mp3", ".ogg", ".m4a", ".aac", ".flac", ".mid", ".midi");
    }

    private float getDocumentFilesUsage() {
        return U.measureFileTypeUsage(".doc", ".docx", ".pdf", ".ppt", ".pptx");
    }

    private float getVideoFilesUsage() {
        return U.measureFileTypeUsage(".3gp", ".mp4", ".mkv", ".webm");
    }
}
