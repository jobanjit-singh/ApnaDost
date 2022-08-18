package com.example.apnadost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginpage extends AppCompatActivity {
    EditText loginemail,loginpass;
    TextView loginforgetpass,loginregistertext;
    Button loginbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        loginemail = findViewById(R.id.loginemail);
        loginpass = findViewById(R.id.loginpass);
        loginforgetpass = findViewById(R.id.loginforgetpassword);
        loginregistertext = findViewById(R.id.loginregistertext);
        loginbutton = findViewById(R.id.loginbutton);

        loginregistertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginpage.this,registerpage.class));
                finish();
            }
        });

        loginforgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginpage.this,forgetpassword.class));
                finish();
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(loginemail.getText().toString())){
                    Toast.makeText(loginpage.this, "Enter Email", Toast.LENGTH_SHORT).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(loginemail.getText().toString()).matches()){
                    Toast.makeText(loginpage.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(loginpass.getText().toString())){
                    Toast.makeText(loginpage.this, "Enter 8 characterPassword", Toast.LENGTH_SHORT).show();
                }
                else{
                    login();
                }
            }

            private void login() {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(loginemail.getText().toString(),loginpass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(loginpage.this,MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(loginpage.this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}