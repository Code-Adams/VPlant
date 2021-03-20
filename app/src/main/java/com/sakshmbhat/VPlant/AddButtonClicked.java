package com.sakshmbhat.VPlant;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class AddButtonClicked extends AppCompatActivity {
    String Category="",Type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbuttonclicked);
        TextView mtext=findViewById(R.id.ShowingCatAndType);
        Intent intent=getIntent();
        Category=intent.getStringExtra("Category");
        Type=intent.getStringExtra("Type");
        mtext.setText(Category+" "+Type);
    }
}
