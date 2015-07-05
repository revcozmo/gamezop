package com.rishabhsethi.whoop2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by rishabh on 3/6/15.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{
    //List<> mItems;
    private ArrayList<Games> listGames = new ArrayList<>();
    //private ArrayList<String> listFiles = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    public void setGameList(ArrayList listGames){
        this.listGames = listGames;
        notifyItemRangeChanged(0, listGames.size());
    }
    String imei_no;
    public CardAdapter(Context context) {
        //super();
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
        /*mItems = new ArrayList<Games>();
        Games game = new Games();
        game.setGame_name("2048");
        game.setGame_image(R.drawable.two);
        game.setGame_type("Puzzle");
        game.setGame_description("Best game ever");
        game.setGame_url("http://gabrielecirulli.github.io/2048/");
        mItems.add(game);

        game = new Games();
        game.setGame_name("Hextris");
        game.setGame_image(R.drawable.hextris);
        game.setGame_type("Casual");
        game.setGame_description("Best game ever");
        game.setGame_url("http://hextris.io/");
        mItems.add(game);

        /*game = new Games();
        game.setGame_name("Parity");
        game.setGame_image(R.drawable.parity);
        game.setGame_type("Puzzle");
        game.setGame_description("Best game ever");
        game.setGame_url("http://www.abefehr.com/parity/");
        mItems.add(game);*/

        /*game = new Games();
        game.setGame_name("Timbermen");
        game.setGame_image(R.drawable.timbermen);
        game.setGame_type("Adventure");
        game.setGame_description("Best game ever");
        game.setGame_url("http://whylike.me/codecanyon/timberman/index.html");
        mItems.add(game);

        game = new Games();
        game.setGame_name("Pop Up");
        game.setGame_image(R.drawable.popup);
        game.setGame_type("Endless Runner");
        game.setGame_description("Best game ever");
        game.setGame_url("http://td2tl.com/codecanyon/popup/");
        mItems.add(game);

        /*game = new Games();
        game.setGame_name("Woblox");
        game.setGame_image(R.drawable.woblox);
        game.setGame_type("Puzzle");
        game.setGame_description("Best game ever");
        game.setGame_url("https://0.s3.envato.com/files/78785509/index.html");
        mItems.add(game);

        game = new Games();
        game.setGame_name("Rain Boxes");
        game.setGame_image(R.drawable.rainboxes);
        game.setGame_type("Puzzle");
        game.setGame_description("Best game ever");
        game.setGame_url("http://odiusfly.com/envato/codecanyon/html5/games/rbox/");
        mItems.add(game);

        game = new Games();
        game.setGame_name("Twins");
        game.setGame_image(R.drawable.twins);
        game.setGame_type("Puzzle");
        game.setGame_description("Best game ever");
        game.setGame_url("http://odiusfly.com/envato/codecanyon/html5/games/twins/");
        mItems.add(game);

        game = new Games();
        game.setGame_name("Bouncing Dot");
        game.setGame_image(R.drawable.bouncingdot);
        game.setGame_type("Puzzle");
        game.setGame_description("Best game ever");
        game.setGame_url("http://odiusfly.com/envato/codecanyon/html5/games/bdot/");
        mItems.add(game);

        game = new Games();
        game.setGame_name("Circle Ball");
        game.setGame_image(R.drawable.circleball);
        game.setGame_type("Puzzle");
        game.setGame_description("Best game ever");
        game.setGame_url("http://odiusfly.com/envato/codecanyon/html5/games/cball/");
        mItems.add(game);

        game = new Games();
        game.setGame_name("Falling Boxes");
        game.setGame_image(R.drawable.fallingboxes);
        game.setGame_type("Puzzle");
        game.setGame_description("Best game ever");
        game.setGame_url("http://odiusfly.com/envato/codecanyon/html5/games/fbox/");
        mItems.add(game);*/

    }

    /*public void add(Games item, int position) {
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    /*public void remove(Games item) {
        int position = mItems.indexOf(item);
        mItems.remove(position);
        notifyItemRemoved(position);
    }*/

    /*public void remove(int position){
        mItems.remove(position);
        notifyItemRemoved(position);
    }*/

    //@Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    //@Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        //Games games = mItems.get(i);

        final Games games = listGames.get(i);
        final String gameUrl = games.getGame_url();
        final String gameName = games.getGame_name();
        //listFiles = games.getGame_files();
        //final int listFilesLength = listFiles.size();
        viewHolder.game_name.setText(games.getGame_name());
        //viewHolder.game_image.setImageResource(games.getGame_image());
        String gameImageUrl = games.getGame_image_url();
        final String game_code = games.getGame_code();
        if(gameImageUrl!=null){
            imageLoader.get("http://107.178.214.92/webservices/game_icon/"+gameImageUrl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                    viewHolder.game_image.setImageBitmap(imageContainer.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    viewHolder.game_image.setImageResource(R.drawable.two);
                }
            });
        }
        viewHolder.game_type.setText(games.getGame_type());
        viewHolder.game_desc = games.getGame_description();
        viewHolder.game_url = games.getGame_url();
        viewHolder.game_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),"URL="+viewHolder.game_url+"position="+i,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), CrossActivity.class);
                String url = (String)gameUrl;
                intent.putExtra("GAME_URL", url);
                v.getContext().startActivity(intent);
            }
        });
        viewHolder.game_name.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),"URL="+viewHolder.game_url+"position="+i,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), CrossActivity.class);
                String url = (String)gameUrl;
                intent.putExtra("GAME_URL", url);
                v.getContext().startActivity(intent);
            }
        });

        viewHolder.game_type.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),"URL="+viewHolder.game_url+"position="+i,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), CrossActivity.class);
                    String url = (String)gameUrl;
                intent.putExtra("GAME_URL", url);
                v.getContext().startActivity(intent);
            }
        });

        viewHolder.TVshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String gameName = (String)viewHolder.game_name.getText();
                //Toast.makeText(v.getContext(),"Share "+gameName,Toast.LENGTH_SHORT).show();
                Intent i=new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                //String gamename = viewHolder.game_name.toString();
                //String gameurl = viewHolder.game_url.toString();
                i.putExtra(android.content.Intent.EXTRA_SUBJECT,"Hey, Play this game: " + gameName);
                i.putExtra(android.content.Intent.EXTRA_TEXT, gameUrl);
                v.getContext().startActivity(Intent.createChooser(i,"Share "+ gameName +" via"));
            }
        });

        viewHolder.TVfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TelephonyManager telephonyManager = (TelephonyManager)v.getContext().getSystemService(Context.TELEPHONY_SERVICE);
                //imei_no = telephonyManager.getDeviceId();
                //String phoneGroup = games.getPhoneGroup();
                //

                Toast.makeText(v.getContext(),"Added to favorites",Toast.LENGTH_SHORT).show();
                /*DBFavorites favorite = new DBFavorites();
                favorite.favoriteName = gameName;
                favorite.favoriteIconUrl = "http://google.com/";
                favorite.favoriteUrl = gameUrl;
                favorite.save();*/
                AsyncHttpClient client = new AsyncHttpClient();
                client.get("http://107.178.214.92/webservices/st_game.php?gcd="+game_code+"&imei_no="+imei_no, new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                        // called when response HTTP status is "200 OK"

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });
                // execute this when the downloader must be fired
                //final DownloadTask downloadTask = new DownloadTask(v.getContext());
                //downloadTask.setgameCode(game_code);
                //for(int k=0; k < listFilesLength; k++) {
                //    String fileName = listFiles.get(k);
                //    downloadTask.setfileName(fileName);
                //    downloadTask.execute("http://107.178.214.92/webservices/game_images/"+game_code+"/"+phoneGroup+"/"+fileName);
            //    }
            }
        });

        /*viewHolder.game_image.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                //Toast.makeText(v.getContext(),"URL="+viewHolder.game_url+"position="+i,Toast.LENGTH_LONG).show();
                remove(i);
                return false;
            }
        });*/


    }

    //@Override
    /*public int getItemCount() {
        return mItems.size();
    }*/

    public int getItemCount() {
        return listGames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView game_image;
        public TextView game_name;
        public TextView game_type;
        public FancyButton TVshare;
        public FancyButton TVfavorite;
        public String game_desc;
        public String game_url;
        public CardView game_card_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            game_image = (ImageView)itemView.findViewById(R.id.game_image);
            game_name = (TextView)itemView.findViewById(R.id.game_name);
            game_type = (TextView)itemView.findViewById(R.id.game_type);
            TVshare = (FancyButton)itemView.findViewById(R.id.TVshare);
            TVfavorite = (FancyButton)itemView.findViewById(R.id.TVfavorite);
            game_card_layout = (CardView)itemView.findViewById(R.id.game_card_layout);
            //game_image.setOnClickListener(this);
            //game_name.setOnClickListener(this);
            //game_url = (String)Games.game_url;
        }

        /*@Override
        public void onClick(View v){
            Intent intent = new Intent(v.getContext(), CrossActivity.class);
            intent.putExtra("GAME_URL", game_url);
            String a = intent.getStringExtra("GAME_URL");
            Toast.makeText(v.getContext(),"URL="+a,Toast.LENGTH_LONG).show();
            v.getContext().startActivity(intent);
        }*/
    }
}
