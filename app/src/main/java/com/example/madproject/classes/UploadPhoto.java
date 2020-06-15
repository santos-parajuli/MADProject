package com.example.madproject.classes;

public class UploadPhoto {
    private String uID;
    private String uImageUrl;
    public UploadPhoto() {

    }
    public UploadPhoto(String imageUrl, String id) {
        uImageUrl = imageUrl;
        uID = id;

    }

    public String getImageUrl() {
        return uImageUrl;
    }
    public String getUID() {
        return uID;
    }
}
