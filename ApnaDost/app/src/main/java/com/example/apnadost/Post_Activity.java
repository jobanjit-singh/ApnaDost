package com.example.apnadost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Post_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ImageView post_send_icon;
        EditText post_question_edittext;

        post_send_icon = findViewById(R.id.post_send_icon);
        post_question_edittext = findViewById(R.id.post_question_edittext);

        post_send_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(post_question_edittext.getText().toString())){
                    Toast.makeText(Post_Activity.this, "Please Enter Question..", Toast.LENGTH_SHORT).show();
                }
                else{
                    storepost();
                }
            }

            private void storepost() {
                Dialog loadingdialog = new Dialog(Post_Activity.this);
                loadingdialog.setContentView(R.layout.loading_dialog);
                loadingdialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                loadingdialog.setCancelable(true);
                loadingdialog.show();
                HashMap<String,Object> post = new HashMap<>();
                post.put("Email",FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
                post.put("Post",post_question_edittext.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("post").push().setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Post_Activity.this, "Thanks for Posting your Query", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Post_Activity.this,MainActivity.class));
                        finish();
                        loadingdialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Post_Activity.this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        loadingdialog.dismiss();
                    }
                });
            }
        });


    }
}