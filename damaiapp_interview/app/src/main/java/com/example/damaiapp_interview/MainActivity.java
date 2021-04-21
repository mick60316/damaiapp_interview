package com.example.damaiapp_interview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    final String TAG = "Mike TAG ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final NavController navController = Navigation.findNavController(this, R.id.main_fragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "OnResume");
    }
}