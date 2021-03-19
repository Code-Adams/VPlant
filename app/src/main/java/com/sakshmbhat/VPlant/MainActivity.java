package com.sakshmbhat.VPlant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton FOAB;//SAKSHM BHAT

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeParameter();//SAKSHM BHAT
    }

    private void initializeParameter() {

        FOAB=findViewById(R.id.foab);//SAKSHM BHAT

    }
}