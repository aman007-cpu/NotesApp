package com.example.notesapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class createnote extends AppCompatActivity {
    EditText mcreatetitleofnote , mcreatecontentofnote;
    FloatingActionButton msavenote;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    ProgressBar mprogressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnote);
         msavenote = findViewById(R.id.savenote);
         mcreatetitleofnote =findViewById(R.id.createtitleofnote);
         mcreatecontentofnote = findViewById(R.id.createcontentofnote);
        Toolbar toolbar  = findViewById(R.id.toolbarofcreatenote);
        mprogressbar = findViewById(R.id.progressbarofcreatenote);
         setSupportActionBar(toolbar);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     firebaseAuth = FirebaseAuth.getInstance();
     firebaseFirestore = FirebaseFirestore.getInstance();
     firebaseUser =  FirebaseAuth.getInstance().getCurrentUser();
   msavenote.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           String title = mcreatetitleofnote.getText().toString();
           String content = mcreatecontentofnote.getText().toString();
           if(title.isEmpty() || content.isEmpty()){
               Toast.makeText(getApplicationContext(), "BOTH FIELDS ARE REQUIRED", Toast.LENGTH_SHORT).show();

           }else{

               mprogressbar.setVisibility(View.VISIBLE);
               DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document();
               Map<String, Object> note = new HashMap<>();
               note.put("title",title);
               note.put("content", content);
               documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void unused) {
                       Toast.makeText(getApplicationContext(), "Note Created Successfully", Toast.LENGTH_SHORT).show();
                      startActivity(new Intent(createnote.this , notesActivity.class));
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(getApplicationContext(), "Failed to create node", Toast.LENGTH_SHORT).show();
                       mprogressbar.setVisibility(View.INVISIBLE);
                     //  startActivity(new Intent(createnote.this , notesActivity.class));
                   }
               });
           }
       }
   });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}