package com.example.apnadost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    ImageView main_toolbar_account;
    ImageView main_bottombar_add;
    RecyclerView main_recyclerView;
    postAdapter adapter;
    Dialog loading;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_bottombar_add = findViewById(R.id.main_bottombar_add);
        main_toolbar_account = findViewById(R.id.main_toolbar_account);
        main_recyclerView = findViewById(R.id.main_recyclerView);

        loading = new Dialog(MainActivity.this);
        loading.setContentView(R.layout.loading_dialog);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loading.getWindow().setDimAmount(0);
        loading.show();
        main_toolbar_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_layout);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                dialog.show();
                dialog.setCancelable(true);

                TextView usertext;
                TextView Signout,deleteAccount;
                usertext = dialog.findViewById(R.id.dialog_username);
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                usertext.setText(email);

                Signout = dialog.findViewById(R.id.dialog_signout_button);
                Signout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(MainActivity.this,loginpage.class));
                        finish();
                        dialog.dismiss();
                    }
                });
                deleteAccount = dialog.findViewById(R.id.dialog_deleteaccount_button);
                deleteAccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Dialog deletedialog = new Dialog(MainActivity.this);
                        deletedialog.setContentView(R.layout.dialog_deleteaccount);
                        deletedialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                        deletedialog.show();

                        TextView delete_dialog_yes,delete_dialog_no;
                        EditText delete_dialog_password_edit,delete_dialog_userid;

                        delete_dialog_password_edit =deletedialog.findViewById(R.id.deletedialog_password_edittext);
                        delete_dialog_yes = deletedialog.findViewById(R.id.yestext);
                        delete_dialog_no = deletedialog.findViewById(R.id.notext);
                        delete_dialog_userid = deletedialog.findViewById(R.id.deletedialog_userid_text);

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        delete_dialog_no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                deletedialog.dismiss();
                            }
                        });
                        delete_dialog_yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (TextUtils.isEmpty(delete_dialog_userid.getText().toString())) {
                                    Toast.makeText(MainActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                                }
                                else if(!Patterns.EMAIL_ADDRESS.matcher(delete_dialog_userid.getText().toString()).matches()){
                                    Toast.makeText(MainActivity.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                                }
                                else if(TextUtils.isEmpty(delete_dialog_password_edit.getText().toString())) {
                                    Toast.makeText(MainActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Dialog loadingdialog = new Dialog(MainActivity.this);
                                    loadingdialog.setContentView(R.layout.loading_dialog);
                                    loadingdialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                                    loadingdialog.setCancelable(true);
                                    loadingdialog.show();
                                    AuthCredential credential = EmailAuthProvider.getCredential(delete_dialog_userid.getText().toString(), delete_dialog_password_edit.getText().toString());
                                    FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            user.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(MainActivity.this, "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(MainActivity.this,loginpage.class));
                                                            finish();
                                                            loadingdialog.dismiss();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(MainActivity.this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(MainActivity.this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(MainActivity.this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
            }
        });


        main_bottombar_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Post_Activity.class));
                finish();
            }
        });

        main_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<postmodel> options =
                new FirebaseRecyclerOptions.Builder<postmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("post"), postmodel.class)
                        .build();
        adapter = new postAdapter(options,this,loading);
        main_recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}