package com.rishabhsethi.whoop2;

import java.util.ArrayList;

/**
 * Created by rishabh on 27/6/15.
 */
public class Friend {
    private String name;
    private ArrayList<String> gameUrls;
    private ArrayList<String> gameIconUrls;

    public String getName(){
        return name;
    }

    public void setName(String NAME){
        this.name = NAME;
    }

    public ArrayList getGameUrls(){
        return gameUrls;
    }

    public void setGameUrls(ArrayList GAMEURLS){
        this.gameUrls = GAMEURLS;
    }

    public ArrayList getGameIconUrls(){
        return gameIconUrls;
    }

    public void setGameIconUrls(ArrayList GAMEICONURLS){
        this.gameIconUrls = GAMEICONURLS;
    }
}
