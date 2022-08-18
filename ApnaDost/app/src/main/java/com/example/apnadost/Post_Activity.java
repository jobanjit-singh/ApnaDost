package com.example.apnadost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Post_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ImageView post_send_icon;
        EditText post_question_edittext;

        post_send_icon = findViewById(R.id.post_send_icon);
        post_question_edittext = findViewById(R.id.post_question_edittext);


    }
}