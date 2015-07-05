package com.rishabhsethi.whoop2;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rishabh on 1/7/15.
 */
public class User extends RealmObject{
    @PrimaryKey
    private String phoneNumber;

    private String name;
    private String imageUrl;
    private String game1;
    private String game2;
    private String game3;
    private String game4;
    private boolean registered;

    @Ignore
    private int sessionId;

    // Standard getters & setters generated by your IDE…
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phone) { this.phoneNumber = phone; }
    public void setImageUrl(String url){
        this.imageUrl=url;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public String getGame2(){
        return game2;
    }
    public void setGame2(String GAME2){
        this.game2 = GAME2;
    }
    public String getGame3(){
        return game3;
    }
    public void setGame3(String GAME3){
        this.game3 = GAME3;
    }
    public String getGame4(){
        return game4;
    }
    public void setGame4(String GAME4){
        this.game4 = GAME4;
    }
    public String getGame1(){
        return game1;
    }
    public void setGame1(String GAME1){
        this.game1 = GAME1;
    }
    public void setRegistered(boolean b){
        this.registered = b;
    }
    public boolean getRegistered(){
        return registered;
    }
    public int getSessionId() { return sessionId; }
    public void setSessionId(int dontPersist) { this.sessionId = sessionId; }
}