package com.sakshmbhat.VPlant;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Button addplant;
    String chosenCategory="";
    String ChosenType="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addplant=findViewById(R.id.clicked);
        addplant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryDialog();
            }
        });
    }
    private void showCategoryDialog() {
        final String CategoryArray[]={"Balcony","Terrace","Indoor","Lawns"};
        AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose a Category");
        builder.setSingleChoiceItems(CategoryArray, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chosenCategory=CategoryArray[which];
                Toast.makeText(MainActivity.this,"Category Chosen :"+chosenCategory,Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showTypeDialog();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
    private void showTypeDialog() {
        final String TypeArray[]={"Air purifier","Health","Low maintainance"};
        AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose a Category");
        builder.setSingleChoiceItems(TypeArray, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ChosenType=TypeArray[which];
                Toast.makeText(MainActivity.this,"Category Type :"+ChosenType,Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(MainActivity.this,AddButtonClicked.class);
                intent.putExtra("Category",chosenCategory);
               intent.putExtra("Type",ChosenType);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}

//
//public class AddButtonClicked extends AppCompatActivity {
//    String Category="",Type="";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_button_clicked);
//        TextView mtext=findViewById(R.id.ShowingCatAndType);
//        Intent intent=getIntent();
//        Category=intent.getStringExtra("Category");
//        Type=intent.getStringExtra("Type");
//        mtext.setText(Category+" "+Type);
//    }
//}


//
//public class MainActivity extends AppCompatActivity {
//
//    private FloatingActionButton FOAB;//SAKSHM BHAT
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        initializeParameter();//SAKSHM BHAT
//    }
//
//    private void initializeParameter() {
//
//        FOAB=findViewById(R.id.foab);//SAKSHM BHAT
//
//    }
//}