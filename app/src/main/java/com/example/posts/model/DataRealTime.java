package com.example.posts.model;

public class DataRealTime {
    String email , password , nickName , imageResource ;

    public DataRealTime(String email, String password, String nickName, String imageResource) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.imageResource = imageResource;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }
}
