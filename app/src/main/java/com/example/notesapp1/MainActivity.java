package com.example.notesapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
  private EditText mloginemail, mloginpassword;
  RelativeLayout mlogin,mgotosignup;
  TextView  mgotoforgotpassword;
  ProgressBar mporgressbar;

   private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        mloginemail = findViewById(R.id.loginemail);
        mloginpassword = findViewById(R.id.loginpassword);
        mlogin = findViewById(R.id.login);
        mgotosignup = findViewById(R.id.gotosignup);
        mgotoforgotpassword = findViewById(R.id.gotoforgotpassword);
        mporgressbar = findViewById(R.id.progressbarofmainactivity);
         firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            finish();
            startActivity(new Intent(MainActivity.this, notesActivity.class));
        }

        mgotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });

        mgotoforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,forgotPassword.class));
            }
        });

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mloginemail.getText().toString().trim();
                String password = mloginpassword.getText().toString().trim();

                if(mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else{
                    // login the user firebase
                       mporgressbar.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                          if(task.isSuccessful()){
                              checkmaiverification();
                          }
                          else{
                              Toast.makeText(getApplicationContext(),"Account Doesn't Exist ", Toast.LENGTH_SHORT).show();
                              mporgressbar.setVisibility(View.INVISIBLE);
                          }
                        }
                    });
                }
            }
        });
    }
    private  void checkmaiverification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser.isEmailVerified()==true){
            Toast.makeText(getApplicationContext(),"Loged In ", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this, notesActivity.class));
        }
        else{
            mporgressbar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"Verify Your Mail First ", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}