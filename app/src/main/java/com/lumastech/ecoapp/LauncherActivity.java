package com.lumastech.ecoapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lumastech.ecoapp.databinding.ActivityLauncherBinding;

public class LauncherActivity extends AppCompatActivity {
    private ActivityLauncherBinding binding;
    Utility utility = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLauncherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        utility = new Utility(this);
        auth();
    }

    @Override
    protected void onResume() {
        super.onResume();
        auth();
    }

    private void auth() {
        utility  = new Utility(this);
        Intent intent;
        if(utility.token() == null) {
            intent = new Intent(this, LoginActivity.class);
        }else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}