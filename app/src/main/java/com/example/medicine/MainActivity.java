package com.example.medicine;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
    public void manage (View view) {
        Intent intent = new Intent(MainActivity.this,ManajemenObat.class);
        startActivity(intent);
    }
    public void reminder (View view) {
        Intent intent = new Intent(MainActivity.this,Reminder.class);
        startActivity(intent);
    }

}