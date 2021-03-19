package com.sakshmbhat.VPlant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.sakshmbhat.VPlant.phoneNumberAuthActivity;
import com.sakshmbhat.VPlant.R;

import java.util.concurrent.TimeUnit;



public class phoneNumberAuthActivity extends AppCompatActivity {

    private EditText phoneNumber,otp;
    private Spinner userTypeSpinner;
    private String userType="";
    private boolean mVerificationInProgress = false,getOtpClicked=false;
    private CountryCodePicker ccp;
    private Button verifyBtn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private TextView getOTP,resendOTP,timer,otpSent;
    private String TAG,mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String verificationCodeBySystem;
    private DatabaseReference rootRef;


    Dialog dialog;
    FirebaseUser mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_auth);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent i = new Intent(phoneNumberAuthActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }

        initialiseElements();
        setSpinner();

        getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getotpOnclick();
            }
        });
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(userType.isEmpty()|| userType.equals("Select User Mode")){
                  Toast.makeText(phoneNumberAuthActivity.this, "Please identify user type!!", Toast.LENGTH_SHORT).show();
                  userTypeSpinner.requestFocus();
              }else {
                  loginOnClick();
              }
            }
        });
        verifyBtn.setTextColor(Color.parseColor("#C0BEBE"));
        userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userType = userTypeSpinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void initialiseElements() {
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        otp=findViewById(R.id.otpField);
        otp.setVisibility(View.GONE);
        phoneNumber = findViewById(R.id.phoneNumberField);
        progressBar = findViewById(R.id.progressBar);
        verifyBtn = findViewById(R.id.verifyBtn);
        resendOTP = findViewById(R.id.resendOTP);
        getOTP=findViewById(R.id.getOTP);
        timer=findViewById(R.id.countdownTimer);
        otpSent=findViewById(R.id.otpSent);

        ccp=(CountryCodePicker)findViewById(R.id.countrycodepicker);
        ccp.registerCarrierNumberEditText(phoneNumber);

        rootRef= FirebaseDatabase.getInstance().getReference();
        userTypeSpinner=findViewById(R.id.userTypeSpinner);


    }

    public void loginOnClick(){

        String number = ccp.getFullNumberWithPlus().trim();
        String otp1 = otp.getText().toString().trim();

        if ((number.length() == 13 || number.length()==14) && otp1.length() > 4){

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
            dialog = new Dialog(phoneNumberAuthActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_wait);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            verifyCode(otp1);


        }else {

            if (number.isEmpty() || number.length() < 13 || number.length() >14){

                phoneNumber.setError("Valid number is required");
                phoneNumber.requestFocus();

            }else if (otp1.isEmpty() || otp1 .length() < 5){

                otp.setError("Valid OTP is required");
                otp.requestFocus();


            }


        }

    }

    private void verifyCode(String code){

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,code);
        FirebaseAuth.getInstance().getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
        signInWithCredential(credential);


    }

    private void signInWithCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            mCurrentUser = mAuth.getCurrentUser();
                            if (mCurrentUser !=null){

                                //dialog.dismiss();
                                Intent i = new Intent(getApplicationContext(),registerActivity.class);
                                i.putExtra("phoneNumber",ccp.getFullNumberWithPlus().trim());
                                i.putExtra("userType",userType);
                                startActivity(i);
                                finish();


                            }else {

                                if (dialog != null){

                                    dialog.dismiss();
                                    Toast.makeText(phoneNumberAuthActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                });
    }

    public void getotpOnclick(){

        if (!getOtpClicked){

            String num   = ccp.getFullNumberWithPlus().trim();

            if(num.length() < 13  || num.length() >14){

                phoneNumber.setError("Valid number is required");
                phoneNumber.requestFocus();

            }else {

                getOtpClicked = true;
                sendVerificationCode(num);
                getOTP.setTextColor(Color.parseColor("#C0BEBE"));
                dialog = new Dialog(phoneNumberAuthActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_wait);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();


            }

        }


    }

    private void sendVerificationCode(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L,TimeUnit.SECONDS)
                        .setActivity(phoneNumberAuthActivity.this)
                        .setCallbacks(mCallBack)
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);


    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onCodeSent(@NonNull String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

            dialog.dismiss();
            verifyBtn.setTextColor(Color.parseColor("#000000"));
            otpSent.setText("OTP has been sent yo your mobile number");
            otp.setVisibility(View.VISIBLE);
            otpSent.setVisibility(View.VISIBLE);
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;

            timer.setVisibility(View.VISIBLE);

            new CountDownTimer(60000,1000){


                @Override
                public void onTick(long millisUntilFinished) {

                    timer.setText("" + millisUntilFinished/1000);

                }

                @Override
                public void onFinish() {

                    resendOTP.setVisibility(View.VISIBLE);
                    timer.setVisibility(View.INVISIBLE);

                }
            }.start();
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if (code != null){

                otp.setText(code);
                verifyCode(code);


            }


        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            getOtpClicked = false;
            getOTP.setTextColor(Color.parseColor("00000FF"));
            Toast.makeText(phoneNumberAuthActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

        }
    };

    private void setSpinner() {

        String[] items= new String[] {"Select User Mode","Seller","Buyer"};

        userTypeSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));

    }

}