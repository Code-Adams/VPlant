package com.sakshmbhat.VPlant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CardClicked extends AppCompatActivity {
    TextView mtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_clicked);
        mtext=findViewById(R.id.desiredDetail);
        Intent Iten=getIntent();
        mtext.setText(Iten.getStringExtra("Data"));
    }
}