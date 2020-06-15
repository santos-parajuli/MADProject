package com.example.madproject.classes;

public class UploadUser {
    private String uName;
    private String uEmail;
    private String uID;
    private String uImageUrl;
    public UploadUser() {

    }
    public UploadUser(String email, String name, String imageUrl, String id) {
        uName = name;
        uImageUrl = imageUrl;
        uID=id;
        uEmail=email;
    }
    public void setName(String name) {
        uName = name;
    }
    public void setImageUrl(String imageUrl) {
        uImageUrl = imageUrl;
    }
    public void setEmail(String email){uEmail=email;}
    public void setId(String id){uID=id;}
    public String getID(){return uID;}
    public String getEmail(){return uEmail;}
    public String getImageUrl() {
        return uImageUrl;
    }
    public String getName() {
        return uName;
    }



}

