package com.carloid.nitromemorybooster.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.carloid.nitromemorybooster.R;
import com.carloid.nitromemorybooster.databinding.ActivityMainBinding;
import com.carloid.nitromemorybooster.view.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);

        getWindow().setNavigationBarColor(0x00);
        showMainFragment();
    }

    private void showMainFragment() {
        MainFragment mainFragment = new MainFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.main_fragment_area, mainFragment);
        transaction.commit();
    }
}