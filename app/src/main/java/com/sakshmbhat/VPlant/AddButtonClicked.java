package com.sakshmbhat.VPlant;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class AddButtonClicked extends AppCompatActivity {
    String Category="",Type="",selectedPlant="",imageUri="",sendNext="";
    CardView Adv,Desc,Maintenance,PlantCategory,PlantingPro,QuickInfo,DrawB,Scientific;
    String mAdv="",mDesc="",mMaintenance="",mPlantCategory="",mPlantingPro="",mQuickInfo="",mDrawB="",mScientific="";
    DatabaseReference rootref;
    DatabaseReference mreq,detailPlant;
    ImageView mview;
    Button buy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbuttonclicked);
        buy=findViewById(R.id.buy);
        mview=findViewById(R.id.plantImage);
        Adv=findViewById(R.id.AdvBen);
        Desc=findViewById(R.id.desc);
        Maintenance=findViewById(R.id.MainReq);
        PlantCategory=findViewById(R.id.Cat);
        PlantingPro=findViewById(R.id.PlantingPro);
        QuickInfo=findViewById(R.id.Quick);
        DrawB=findViewById(R.id.Drawback);
        Scientific=findViewById(R.id.Scientific);
        final Intent intent=getIntent();
        Category=intent.getStringExtra("Category");
        Log.d("adarsh",Category);
        Type=intent.getStringExtra("Type");
        Log.d("adarshType",Type);
        rootref= FirebaseDatabase.getInstance().getReference().child("Plants");
        mreq=rootref.child("Category").child(Category);
        mreq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                selectedPlant=dataSnapshot.child(Type).getValue(String.class);
                Toast.makeText(AddButtonClicked.this,"Most Suitable Plant "+selectedPlant,Toast.LENGTH_LONG).show();
                //Log.d("Mishre",selectedPlant);
                detailPlant=rootref.child("All Plants").child(selectedPlant);
                initialiseAllvalues(detailPlant);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Log.d("Detail",detailPlant+" ");

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Uri myuri=Uri.parse("geo:0,0?q=Gardener");
                Intent intent1=new Intent(Intent.ACTION_VIEW,myuri);
                intent1.setPackage("com.google.android.apps.maps");
                startActivity(intent1);*/
                Intent intent1=new Intent(AddButtonClicked.this,ContactingSeller.class);
                startActivity(intent1);
            }
        });




        Desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddButtonClicked.this,mDesc,Toast.LENGTH_LONG).show();
                Intent intent1=new Intent(AddButtonClicked.this,CardClicked.class);
                intent1.putExtra("Data",mDesc);
                startActivity(intent1);
            }
        });
        PlantCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(AddButtonClicked.this,CardClicked.class);
                intent1.putExtra("Data",mPlantCategory);
                startActivity(intent1);
            }
        });
        PlantingPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(AddButtonClicked.this,CardClicked.class);
                intent1.putExtra("Data",mPlantingPro);
                startActivity(intent1);
            }
        });
        QuickInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(AddButtonClicked.this,CardClicked.class);
                intent1.putExtra("Data",mQuickInfo);
                startActivity(intent1);
            }
        });
        Scientific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(AddButtonClicked.this,CardClicked.class);
                intent1.putExtra("Data",mScientific);
                startActivity(intent1);
            }
        });
    }

    private void initialiseAllvalues(DatabaseReference detailPlant) {

        detailPlant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imageUri=dataSnapshot.child("Image Url").getValue(String.class);
                mDesc=dataSnapshot.child("Description").getValue(String.class);
                mPlantCategory=dataSnapshot.child("Plant Category").getValue(String.class);
                mPlantingPro=dataSnapshot.child("Planting Considerations").getValue(String.class);
                mQuickInfo=dataSnapshot.child("Quick Info").getValue(String.class);
                mScientific=dataSnapshot.child("Scientific Name").getValue(String.class);
                Picasso.get().load(imageUri).placeholder(R.drawable.ic_app_logo).into(mview);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}