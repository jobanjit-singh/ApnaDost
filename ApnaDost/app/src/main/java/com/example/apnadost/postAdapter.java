package com.example.apnadost;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class postAdapter extends FirebaseRecyclerAdapter<postmodel,postAdapter.PostViewholder> {
    Context context;
    Dialog loading;
    public postAdapter(@NonNull FirebaseRecyclerOptions<postmodel> options, Context context,Dialog loading) {
        super(options);
        this.context = context;
        this.loading = loading;
    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewholder holder, int position, @NonNull postmodel model) {
        holder.postviewemail.setText(model.getEmail().toString());
        holder.postviewpost.setText(model.getPost().toString());
    }

    @NonNull
    @Override
    public PostViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.postview,parent,false);
        return new PostViewholder(v);
    }

    public class PostViewholder extends RecyclerView.ViewHolder{

        TextView postviewemail,postviewpost;
        ImageView postviewcomment;
        public PostViewholder(@NonNull View itemView) {
            super(itemView);
            postviewemail = itemView.findViewById(R.id.postviewemail);
            postviewpost = itemView.findViewById(R.id.postviewpost);
            postviewcomment = itemView.findViewById(R.id.postcommenttext);
        }
    }
    public void onDataChanged(){
        if(loading!=null){
            loading.dismiss();
        }
    }
}
