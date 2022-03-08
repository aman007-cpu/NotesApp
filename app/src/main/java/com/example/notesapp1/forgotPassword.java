package com.example.notesapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class forgotPassword extends AppCompatActivity {
   private EditText mforgotpassword;
   private Button mpasswordrecoverbtn;
   private TextView mgobacktologin;
   FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();
        mforgotpassword = findViewById(R.id.forgotpassword);
        mpasswordrecoverbtn = findViewById(R.id.passwordrecoverbtn);
        mgobacktologin = findViewById(R.id.gobacktologin);
        firebaseAuth = FirebaseAuth.getInstance();

        mgobacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forgotPassword.this , MainActivity.class);
                startActivity(intent);

            }
        });
        mpasswordrecoverbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mforgotpassword.getText().toString().trim();
                if(mail.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter Your Email id First", Toast.LENGTH_SHORT).show();
                }
                else{
                    //we have to send password recovery mail

                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Mail Sent , You can recover your password using main", Toast.LENGTH_SHORT).show();
                                         finish();
                                         startActivity(new Intent(forgotPassword.this, MainActivity.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Email is wrong or Account not Exist", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

    }
}