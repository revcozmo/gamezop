package com.rishabhsethi.whoop2;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by rishabh on 3/6/15.
 */
public class Games {
    private String game_name;
    private String game_type;
    private String game_description;
    private int game_image;
    private String game_url;
    private String game_image_url;
    private Integer game_id;
    private String game_code;
    private ArrayList game_files;
    private String phoneGroup;

    public String getGame_name(){
        return game_name;
    }

    public void setGame_name(String name){
        this.game_name = name;
    }

    public Integer getGame_id(){
        return game_id;
    }

    public void setGame_id(Integer gameId){
        this.game_id = gameId;
    }

    public String getGame_type(){
        return game_type;
    }

    public void setGame_type(String type){
        this.game_type = type;
    }

    public String getGame_code(){
        return game_code;
    }

    public void setGame_code(String code){
        this.game_code = code;
    }


    public String getGame_description(){
        return game_description;
    }

    public void setGame_description(String desc){
        this.game_description = desc;
    }

    public int getGame_image(){
        return game_image;
    }

    public void setGame_image(int image){
        this.game_image = image;
    }

    public String getGame_url(){
        return game_url;
    }

    public void setGame_url(String url){
        this.game_url = url;
    }

    public String getGame_image_url(){
        return game_image_url;
    }

    public void setGame_image_url(String image_url){
        this.game_image_url = image_url;
    }

    public String getPhoneGroup(){
        return phoneGroup;
    }

    public void setPhoneGroup(String phoneGroup){
        this.phoneGroup = phoneGroup;
    }


    public ArrayList getGame_files(){
        return game_files;
    }

    public void setGame_files(ArrayList gameFiles){
        this.game_files = gameFiles;
    }
}
