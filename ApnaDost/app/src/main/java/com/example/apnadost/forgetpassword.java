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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class forgetpassword extends AppCompatActivity {
    EditText forgetemail;
    Button forget_reset_button;
    TextView forget_backtologin_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        forgetemail = findViewById(R.id.forget_email);
        forget_reset_button = findViewById(R.id.forget_reset_button);
        forget_backtologin_text = findViewById(R.id.forget_backtologin_text);

        forget_backtologin_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(forgetpassword.this,loginpage.class));
                finish();
            }
        });

        forget_reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(forgetemail.getText().toString())){
                    Toast.makeText(forgetpassword.this, "Enter Email", Toast.LENGTH_SHORT).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(forgetemail.getText().toString()).matches()){
                    Toast.makeText(forgetpassword.this, "Enter Valid Input", Toast.LENGTH_SHORT).show();
                }
                else{
                    resetpass();
                }
            }

            private void resetpass() {
                Dialog loadingdialog = new Dialog(forgetpassword.this);
                loadingdialog.setContentView(R.layout.loading_dialog);
                loadingdialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                loadingdialog.show();
                loadingdialog.setCancelable(true);
                FirebaseAuth.getInstance().sendPasswordResetEmail(forgetemail.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(forgetpassword.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(forgetpassword.this,loginpage.class));
                        finish();
                        loadingdialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(forgetpassword.this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        loadingdialog.dismiss();
                    }
                });
            }
        });
    }
}