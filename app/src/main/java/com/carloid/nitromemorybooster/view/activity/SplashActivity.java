package com.carloid.nitromemorybooster.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.carloid.nitromemorybooster.R;
import com.carloid.nitromemorybooster.databinding.ActivitySplashBinding;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        binding.setLifecycleOwner(this);

        getWindow().setNavigationBarColor(0x00);
        Objects.requireNonNull(getSupportActionBar()).hide();

        new Handler(getMainLooper()).postDelayed(this::gotoMainActivity, SPLASH_DELAY);
    }

    private void gotoMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}