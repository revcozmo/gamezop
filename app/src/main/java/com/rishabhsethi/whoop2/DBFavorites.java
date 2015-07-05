package com.rishabhsethi.whoop2;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by rishabh on 27/6/15.
 */
@Table(name="Favorites")
public class DBFavorites extends Model{
    @Column(name = "FAVORITENAME")
    public String favoriteName;

    @Column(name = "FAVORITEURL", index = true)
    public String favoriteUrl;

    @Column(name = "FAVORITEICONURL")
    public String favoriteIconUrl;

    public DBFavorites() {
        super();
    }

    public DBFavorites(String name, String favoriteurl, String favoreiteiconurl) {
        super();
        this.favoriteName = name;
        this.favoriteUrl = favoriteurl;
        this.favoriteIconUrl = favoreiteiconurl;
    }
}
