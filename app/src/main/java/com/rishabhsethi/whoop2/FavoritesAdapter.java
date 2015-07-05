package com.rishabhsethi.whoop2;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rishabh on 4/6/15.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    public List<Favorites> favorites;

    public FavoritesAdapter() {
        super();
        favorites = new ArrayList<Favorites>();
        Favorites game = new Favorites();
        game.setName("2048");
        game.setUrl("http://gabrielecirulli.github.io/2048/");
        game.setThumbnail(R.drawable.two);
        favorites.add(game);

        game = new Favorites();
        game.setName("Falling Boxes");
        game.setUrl("http://odiusfly.com/envato/codecanyon/html5/games/fbox/");
        game.setThumbnail(R.drawable.fallingboxes);
        favorites.add(game);

        game = new Favorites();
        game.setName("Hextris");
        game.setUrl("http://hextris.io/");
        game.setThumbnail(R.drawable.hextris);
        favorites.add(game);

        game = new Favorites();
        game.setName("Parity");
        game.setUrl("http://www.abefehr.com/parity/");
        game.setThumbnail(R.drawable.parity);
        favorites.add(game);
    }


    public void add(Favorites item, int position) {
        favorites.add(position, item);
        notifyItemInserted(position);
    }

    /*public void remove(Games item) {
        int position = mItems.indexOf(item);
        mItems.remove(position);
        notifyItemRemoved(position);
    }*/

    public void remove(int position){
        favorites.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        Favorites favorite = favorites.get(i);
        viewHolder.game_name.setText(favorite.getName());
        viewHolder.game_image.setImageResource(favorite.getThumbnail());
        viewHolder.game_url = favorite.getUrl();
        String url = (String)viewHolder.game_url;
        viewHolder.game_image.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), CrossActivity.class);
            String url = (String) viewHolder.game_url;
            intent.putExtra("GAME_URL", url);
            v.getContext().startActivity(intent);
        }
        });
        viewHolder.game_image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(i);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {

        return favorites.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView game_image;
        public TextView game_name;
        public String game_url;

        public ViewHolder(View itemView) {
            super(itemView);
            game_image = (ImageView)itemView.findViewById(R.id.game_image);
            game_name = (TextView)itemView.findViewById(R.id.game_name);
        }
    }
}
