package com.sakshmbhat.VPlant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;
    LocationManager locationManager;
    LocationListener locationListener;
    private String PLon,PLat;
    private MaterialButton submitBtn;
    private EditText lName,fName,address,buisnessName;
    private DatabaseReference rRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialiseParameters();
        getDeviceLocation();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fName.getText().toString().isEmpty()){
                    Toast.makeText(registerActivity.this, "All Fields are must!!", Toast.LENGTH_SHORT).show();
                    fName.setError("First Name Required!");
                    fName.requestFocus();
                }else if(lName.getText().toString().isEmpty()) {
                    Toast.makeText(registerActivity.this, "All Fields are must!!", Toast.LENGTH_SHORT).show();
                    lName.setError("Last Name Required!");
                    lName.requestFocus();
                }else if(address.getText().toString().isEmpty()) {
                    Toast.makeText(registerActivity.this, "All Fields are must!!", Toast.LENGTH_SHORT).show();
                    address.setError("First Name Required!");
                    address.requestFocus();
                }else{

                    saveInfo();
                }
            }
        });
    }

    private void saveInfo() {

        String phoneNumber=getIntent().getStringExtra("phoneNumber");
        String userType=getIntent().getStringExtra("userType");

        if(userType.equals("Buyer")) {
            uBuyerData uBData = new uBuyerData(fName.getText().toString().trim(), lName.getText().toString().trim(), address.getText().toString().trim(), userType, PLat, PLon,phoneNumber);

            rRef.child("Users").child("All Users").child(phoneNumber).setValue(uBData).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    rRef.child("Users").child(userType).child(phoneNumber).setValue(uBData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(registerActivity.this, "Success!!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(registerActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(registerActivity.this, "Task failed! Try again!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(registerActivity.this, "Task Failed! Try again!", Toast.LENGTH_SHORT).show();
                }
            });
        }else{

            String SphoneNumber=getIntent().getStringExtra("phoneNumber");
            String SuserType=getIntent().getStringExtra("userType");

            uSellerData uSData = new uSellerData(fName.getText().toString().trim(), lName.getText().toString().trim(), address.getText().toString().trim(), userType,buisnessName.getText().toString().trim(), PLat, PLon,phoneNumber);

            rRef.child("Users").child("All Users").child(phoneNumber).setValue(uSData).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    rRef.child("Users").child(userType).child(phoneNumber).setValue(uSData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(registerActivity.this, "Success!!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(registerActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(registerActivity.this, "Task failed! Try again!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(registerActivity.this, "Task Failed! Try again!", Toast.LENGTH_SHORT).show();
                }
            });



        }


    }

    private void  getDeviceLocation() {

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            if(getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
//
//                //Getting Location
//               fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                   @Override
//                   public void onSuccess(Location location) {
//
//                       if(location !=null){
//
//                           Double lat=location.getLatitude();
//                           Double lon=location.getLongitude();
//                         setLonLat(lat,lon);
//                           Toast.makeText(registerActivity.this, lat+" "+ lon, Toast.LENGTH_SHORT).show();
//                       }
//
//                   }
//               }).addOnFailureListener(new OnFailureListener() {
//                   @Override
//                   public void onFailure(@NonNull Exception e) {
//                       Toast.makeText(registerActivity.this, "Device location access: Error", Toast.LENGTH_SHORT).show();
//                   }
//               });
//
//            }else{
//                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},101);
//            }
//        }

      if(ContextCompat.checkSelfPermission(registerActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(registerActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

          ActivityCompat.requestPermissions(registerActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);

      }
      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, new LocationListener() {
          @Override
          public void onLocationChanged(@NonNull Location location) {

              setLonLat(String.valueOf(location.getLongitude()),String.valueOf(location.getLatitude()));


          }
          @Override
          public void onStatusChanged(String s, int i,Bundle bundle) {

          }
          @Override
          public void onProviderEnabled(@NonNull String s) {

          }
          @Override
          public void onProviderDisabled(@NonNull String s) {

          }
      });

    }



    private void setLonLat(String lon, String lat) {

        PLat=lat;
        PLon=lon;

    }

    private void initialiseParameters() {

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(registerActivity.this);
        submitBtn=findViewById(R.id.submitBtn);
        lName=findViewById(R.id.lastName);
        fName=findViewById(R.id.firstName);
        address=findViewById(R.id.address);
        rRef= FirebaseDatabase.getInstance().getReference();
        buisnessName=findViewById(R.id.businessName);
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(getIntent().getStringExtra("userType").equals("Buyer")){
            buisnessName.setVisibility(View.GONE);
        }

    }
}