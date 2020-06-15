package com.example.madproject.classes;

public class Post {
    String uid,imageUrl;
    public  Post(){

    }
    public Post(String uid, String imageUrl){
        this.uid=uid;
        this.imageUrl=imageUrl;
    }
     public String getUID(){
        return uid;
     }

    public String getImageURL(){
        return imageUrl;
    }

}
