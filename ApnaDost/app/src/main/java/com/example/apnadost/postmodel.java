package com.example.apnadost;

public class postmodel {
    String Email,Post;
    postmodel(){
    }

    public postmodel(String email, String post) {
        Email = email;
        Post = post;
    }

    public String getEmail() {
        return Email;
    }

    public String getPost() {
        return Post;
    }

    public void setEmail(String email) {
        Email = email;
    }
    public void setPost(String post){
        Post = post;
    }
}
