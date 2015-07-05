package com.rishabhsethi.whoop2;

/**
 * Created by rishabh on 4/6/15.
 */
public class Favorites {
    private String game_name;
    private int game_image;
    private String game_url;

    public String getName() {
        return game_name;
    }

    public void setName(String name) {
        this.game_name = name;
    }

    public String getUrl() {
      return game_url;
    }

    public void setUrl(String url) {
      this.game_url = url;
    }

    public int getThumbnail() {
        return game_image;
    }

    public void setThumbnail(int thumbnail) {
        this.game_image = thumbnail;
    }

    public Favorites(){
        super();
    }

    public Favorites(String gameName, int gameImage, String gameUrl){
        this.game_name = gameName;
        this.game_image = gameImage;
        this.game_url = gameUrl;
    }
}
