package com.sakshmbhat.VPlant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton EFOAB;//SAKSHM BHAT
    private String phoneNumber,userType;
    private TextView textViewHeading;
    private ScrollView scrollView;
    private DatabaseReference rRef;
    private MaterialButton updateBtn,refreshBtn;
    private TextInputEditText AloeveraCount,ArtichokeCount,BokChoyCount,BostonFernCount,CauliflowerCount,ChamomileCount,ChivesCount,EnglishIvyCount,GarlicCount,LettuceCount,OnionsCount,SunflowerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAuth();//SAKSHM BHAT
        initializeParameter();//SAKSHM BHAT
        getUserType();//SAKSHM BHAT

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInventory();
            }
        });
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshInventory();
            }
        });

        Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show();
    }

    private void updateInventory() {

        sellerInventory si= new sellerInventory(AloeveraCount.getText().toString(),ArtichokeCount.getText().toString(),BokChoyCount.getText().toString(),BostonFernCount.getText().toString(),CauliflowerCount.getText().toString(),ChamomileCount.getText().toString(),ChivesCount.getText().toString(),EnglishIvyCount.getText().toString(),GarlicCount.getText().toString(),LettuceCount.getText().toString(),OnionsCount.getText().toString(),SunflowerCount.getText().toString());
        rRef.child("Users").child("Seller").child(phoneNumber).child("Inventory").setValue(si).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, "Inventory Update: Success!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Inventory update: Failure!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLayoutByUser() {

        if(userType.equals("Seller")){

            scrollView.setVisibility(View.VISIBLE);
            textViewHeading.setVisibility(View.VISIBLE);
            textViewHeading.setText("You can see your current stock details below.\nYou can also update it.");

            refreshInventory();



        }

    }

    private void refreshInventory() {

        rRef.child("Users").child("Seller").child(phoneNumber).child("Inventory").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){


                    AloeveraCount.setText(snapshot.child("aloevera").getValue(String.class));
                    ArtichokeCount.setText(snapshot.child("artichoke").getValue(String.class));
                    BokChoyCount.setText(snapshot.child("bokChoy").getValue(String.class));
                    BostonFernCount.setText(snapshot.child("bostonFern").getValue(String.class));
                    CauliflowerCount.setText(snapshot.child("cauliflower").getValue(String.class));
                    ChamomileCount.setText(snapshot.child("chamomile").getValue(String.class));
                    ChivesCount.setText(snapshot.child("chives").getValue(String.class));
                    EnglishIvyCount.setText(snapshot.child("englishIvy").getValue(String.class));
                    GarlicCount.setText(snapshot.child("garlic").getValue(String.class));
                    LettuceCount.setText(snapshot.child("lettuce").getValue(String.class));
                    OnionsCount.setText(snapshot.child("onions").getValue(String.class));
                    SunflowerCount.setText(snapshot.child("sunflower").getValue(String.class));


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void getUserType() {

        rRef.child("Users").child("All Users").child(phoneNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    setUserType(snapshot.child("userType").getValue(String.class));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setUserType(String userTypeTemp) {

        userType=userTypeTemp;
        setLayoutByUser();//SAKSHM BHAT

    }

    private void checkAuth() {

        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            Intent i = new Intent(MainActivity.this,phoneNumberAuthActivity.class);
            startActivity(i);
            finish();
        }

    }

    private void initializeParameter() {

        EFOAB=findViewById(R.id.efoab);//SAKSHM BHAT
                  phoneNumber= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber();//SAKSHM BHAT
        rRef= FirebaseDatabase.getInstance().getReference();
                 scrollView=findViewById(R.id.inventoryScroll);
        textViewHeading=findViewById(R.id.mainActivityHeading);
                 updateBtn=findViewById(R.id.updateSellerInventoryBtn);
        refreshBtn=findViewById(R.id.refreshSellerInventoryBtn);
                 AloeveraCount=findViewById(R.id.aloveraCount);
        ArtichokeCount=findViewById(R.id.artichokeCount);
                BokChoyCount=findViewById(R.id.bokChoyCount);
        BostonFernCount=findViewById(R.id.bostonFernCount);
                CauliflowerCount=findViewById(R.id.cauliflowerCount);
        ChamomileCount=findViewById(R.id.chamomileCount);
                ChivesCount=findViewById(R.id.chivesCount);
        EnglishIvyCount=findViewById(R.id.englishIvyCount);
                GarlicCount=findViewById(R.id.garlicCount);
        LettuceCount=findViewById(R.id.lettuceCount);
                 OnionsCount=findViewById(R.id.onionCount);
        SunflowerCount=findViewById(R.id.sunflowerCount);

    }
}