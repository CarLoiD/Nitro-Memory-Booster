package com.carloid.nitromemorybooster.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.carloid.nitromemorybooster.R;
import com.carloid.nitromemorybooster.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        binding.setLifecycleOwner(this);

        getWindow().setNavigationBarColor(0x00);
        getSupportActionBar().hide();

        new Handler(getMainLooper()).postDelayed(this::gotoMainActivity, SPLASH_DELAY);
    }

    private void gotoMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }
}