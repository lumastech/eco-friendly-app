package com.lumastech.ecoapp;

import android.view.Menu;
import android.view.MenuInflater;

import androidx.annotation.NonNull;

public interface NavListener { void onButtonClicked(int id);

    void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater);

}
