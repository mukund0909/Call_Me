package com.example.call_me;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    EditText emailbox,passwordbox;
    Button loginbtn,signupbtn;
    FirebaseAuth auth;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Please Wait...");
        auth=FirebaseAuth.getInstance();
        emailbox=findViewById(R.id.emailbox);
        passwordbox=findViewById(R.id.passwordbox);
        loginbtn=findViewById(R.id.loginbtn);
        signupbtn=findViewById(R.id.createbtn);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,pass,name;
                email=emailbox.getText().toString();
                pass=passwordbox.getText().toString();
                if(email.length() == 0){
                    emailbox.setError("Email is required");
                }else if(pass.length() == 0){
                    passwordbox.setError("Password is required");
                }
                else
                {
                    dialog.show();
                    auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();
                            if(task.isSuccessful())
                            {
                                startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}