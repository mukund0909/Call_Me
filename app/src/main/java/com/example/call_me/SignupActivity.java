package com.example.call_me;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
    EditText emailbox,passwordbox,namebox;
    Button loginbtn,signupbtn;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Please Wait....");
        auth=FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();
        emailbox=findViewById(R.id.emailbox);
        namebox=findViewById(R.id.username);
        passwordbox=findViewById(R.id.passwordbox);
        loginbtn=findViewById(R.id.loginbtn);
        signupbtn=findViewById(R.id.createbtn);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,pass,name;
                email=emailbox.getText().toString();
                pass=passwordbox.getText().toString();
                name=namebox.getText().toString();
                User obj=new User();
                obj.setEmail(email);
                obj.setName(name);
                obj.setPass(pass);
                if(name.length()==0){
                    namebox.setError("Name is required");
                }
                else if(email.length() == 0){
                    emailbox.setError("Email is required");
                }else if(pass.length() == 0){
                    passwordbox.setError("Password is required");
                }
                else
                {
                    dialog.show();
                    auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();
                            if(task.isSuccessful())
                            {
                                database.collection("Users").document().set(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                                    }
                                });
                                Toast.makeText(SignupActivity.this,"Account is Created",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(SignupActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });
    }
}