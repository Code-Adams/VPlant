package com.sakshmbhat.VPlant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    Button addplant;
    String chosenCategory="";
    String ChosenType="";
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=findViewById(R.id.drawer);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id){
                    case R.id.plants:
                      Fragment   fragment=new PlantCatalog();
                        loadFragment(fragment);
                    break;
                    case R.id.maintain:
                          fragment=new Maintanance();
                        loadFragment(fragment);
                        break;
                    case R.id.tips:
                         fragment=new tipsInfo();
                        loadFragment(fragment);
                        break;
                    case R.id.devs:
                          fragment=new Developers();
                        loadFragment(fragment);
                        break;
                    default:
                        return true;

                }
                    return true;
            }
        });


        addplant=findViewById(R.id.clicked);
        addplant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryDialog();
            }
        });
    }

private void loadFragment(Fragment fragment){
    FragmentManager fragmentManager=getSupportFragmentManager();
    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.frame,fragment).commit();
    drawerLayout.closeDrawer(GravityCompat.START);
    fragmentTransaction.addToBackStack(null);

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