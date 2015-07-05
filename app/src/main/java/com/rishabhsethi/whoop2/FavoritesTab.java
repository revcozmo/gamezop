package com.rishabhsethi.whoop2;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by rishabh on 25/5/15.
 */
public class FavoritesTab extends Fragment{
    RecyclerView favoritesRecyclerView;
    RecyclerView.LayoutManager favoritesLayoutManager;
    //RecyclerView.Adapter favoritesAdapter;
    FavoritesAdapter favoritesAdapter;
    List<DBFavorites> FAVORITES;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.favorites_tab,container,false);
        //favoritesTabGridView = (RecyclerView)v.findViewById(R.id.favorites_grid_layout);
        //LinearLayoutManager favoritesLayoutManager = new LinearLayoutManager(getActivity());
        //favoritesTabGridView.setLayoutManager(favoritesLayoutManager);
        //RecyclerView.Adapter favoritesAdapter;
        //favoritesAdapter = new FavoritesAdapter();
        //favoritesTabGridView.setAdapter(favoritesAdapter);


        favoritesRecyclerView = (RecyclerView)v.findViewById(R.id.favorites_grid_layout);
        favoritesRecyclerView.setHasFixedSize(true);

        // The number of Columns
        favoritesLayoutManager = new GridLayoutManager(v.getContext(), 3);
        favoritesRecyclerView.setLayoutManager(favoritesLayoutManager);

        favoritesAdapter = new FavoritesAdapter();
        favoritesRecyclerView.setAdapter(favoritesAdapter);
        /*Select select = new Select();
        FAVORITES = select.all().from(DBFavorites.class).execute();
        for(int i=0;i<FAVORITES.size();i++) {
            favoritesAdapter.add(new Favorites(FAVORITES.get(i).favoriteName,R.drawable.two,FAVORITES.get(i).favoriteUrl), i);
        }*/

        return v;
    }
}

