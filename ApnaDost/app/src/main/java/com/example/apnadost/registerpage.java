package com.example.apnadost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class registerpage extends AppCompatActivity {
    EditText register_name,register_email,register_pass;
    Button register_registerbutton;
    TextView register_backtologin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);
        register_name = findViewById(R.id.register_name_edittext);
        register_email = findViewById(R.id.register_email_edittext);
        register_pass = findViewById(R.id.register_password_edittext);
        register_registerbutton = findViewById(R.id.register_registerbutton_button);
        register_backtologin = findViewById(R.id.register_backto_text);

        register_backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registerpage.this,loginpage.class));
                finish();
            }
        });
        register_registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(register_name.getText().toString())){
                    Toast.makeText(registerpage.this, "Enter Name", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(register_email.getText().toString())){
                    Toast.makeText(registerpage.this, "Enter Email", Toast.LENGTH_SHORT).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(register_email.getText().toString()).matches()){
                    Toast.makeText(registerpage.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(register_pass.getText().toString())){
                    Toast.makeText(registerpage.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if(register_pass.getText().toString().length()<8){
                    Toast.makeText(registerpage.this, "Enter 8 Character Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    register();
                }
            }

            private void register() {
                Dialog loadingdialog = new Dialog(registerpage.this);
                loadingdialog.setContentView(R.layout.loading_dialog);
                loadingdialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                loadingdialog.show();
                loadingdialog.setCancelable(true);
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(register_email.getText().toString(),register_pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        HashMap<String,Object> map = new HashMap<>();
                        map.put("Name",register_name.getText().toString());
                        map.put("Email",register_email.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isComplete()) {
                                    startActivity(new Intent(registerpage.this, MainActivity.class));
                                    finish();
                                    loadingdialog.dismiss();
                                }
                                else{
                                    Toast.makeText(registerpage.this, "Error", Toast.LENGTH_SHORT).show();
                                    loadingdialog.dismiss();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(registerpage.this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                loadingdialog.dismiss();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(registerpage.this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        loadingdialog.dismiss();
                    }
                });
            }
        });
    }
}