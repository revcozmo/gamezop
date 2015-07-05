package com.rishabhsethi.whoop2;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.location.GpsStatus;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
//import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.hudomju.swipe.OnItemClickListener;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.SwipeableItemClickListener;
import com.hudomju.swipe.adapter.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import io.github.codefalling.recyclerviewswipedismiss.SwipeDismissRecyclerViewTouchListener;

/**
 * Created by rishabh on 25/5/15.
 */
public class GamesTab extends Fragment{
    RecyclerView gamesTabRecyclerView;
    //CardAdapter gamesAdapter = new CardAdapter();
    //String imei_no = android.telephony.TelephonyManager.getDeviceId();


    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    public String imei_no="null";
    private RequestQueue requestQueue;
    private ArrayList<Games> listGames = new ArrayList<>();
    //private ArrayList<String> listFiles = new ArrayList<>();
    private CardAdapter gamesAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.games_tab,container,false);
        TelephonyManager telephonyManager = (TelephonyManager)v.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        imei_no = telephonyManager.getDeviceId();

        gamesTabRecyclerView = (RecyclerView)v.findViewById(R.id.my_recycler_view);
        LinearLayoutManager gamesLayoutManager = new LinearLayoutManager(getActivity());
        gamesTabRecyclerView.setLayoutManager(gamesLayoutManager);


        //RecyclerView.Adapter gamesAdapter;
        //gamesAdapter = new CardAdapter();
        gamesAdapter = new CardAdapter(v.getContext());
        gamesTabRecyclerView.setAdapter(gamesAdapter);
        gamesAdapter.setGameList(listGames);
        volleySingleton = VolleySingleton.getInstance();
        requestQueue =  volleySingleton.getRequestQueue();
        getGamesJson(imei_no);
        //FrameLayout frameLayout = (FrameLayout)v.findViewById(R.id.frameLayoutGamesTab);

        //frameLayout.setVisibility(frameLayout.VISIBLE);
        //CircularProgressView progressView = (CircularProgressView) v.findViewById(R.id.progress_view);

        //progressView.startAnimation();

        //L.T(getActivity(), "IMEI NO: "+imei_no);



        /*final SwipeToDismissTouchListener<RecyclerViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new RecyclerViewAdapter(gamesTabRecyclerView),
                        new SwipeToDismissTouchListener.DismissCallbacks<RecyclerViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(RecyclerViewAdapter view, int position) {
                                gamesAdapter.remove(position);
                            }
                        });

        gamesTabRecyclerView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        gamesTabRecyclerView.setOnScrollListener((RecyclerView.OnScrollListener) touchListener.makeScrollListener());
        gamesTabRecyclerView.addOnItemTouchListener(new SwipeableItemClickListener(v.getContext(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (view.getId() == R.id.txt_delete) {
                            touchListener.processPendingDismisses();
                        } else if (view.getId() == R.id.txt_undo) {
                            touchListener.undoPendingDismiss();
                        } else { // R.id.txt_data
                            Toast.makeText(view.getContext(), "Position " + position, Toast.LENGTH_SHORT).show();
                        }
                    }
                }));*/


        return v;
    }

    /*public String getGamesUrl(String imei){
        String gameurl = "http://107.178.214.92/webservices/index.php?tag=game_list&imei="+imei;
        return gameurl;
    }*/

    private void getGamesJson(final String imei){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,"http://107.178.214.92/webservices/index.php?tag=game_list&imei="+imei, (String)null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //L.t(getActivity(), "Aa gya response");
                        //Toast.makeText(getContext(),response.toString(),Toast.LENGTH_LONG);
                        listGames = parseJSONResponse(response, imei);
                        //FrameLayout frameLayout = (FrameLayout)getActivity().findViewById(R.id.frameLayoutGamesTab);
                        //frameLayout.setVisibility(frameLayout.GONE);
                        //CircularProgressView progressView = (CircularProgressView)getActivity().findViewById(R.id.progress_view);
                        //progressView.setVisibility(CircularProgressView.GONE);
                        gamesAdapter.setGameList(listGames);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        requestQueue.add(request);
    }

    public ArrayList<Games> parseJSONResponse(JSONObject response, String imei_no){
        ArrayList<Games> listgames = new ArrayList<>();
        if(response!=null || response.length()>0) {
            try {
                //StringBuilder data = new StringBuilder();
                //final JSONArray arrayphonegroup = response.getJSONArray("STATIC_VALUE");
                //JSONObject currentphonegroup = arrayphonegroup.getJSONObject(0);
                //String phoneGroup = currentphonegroup.getString("PHONE_GROUP");
                final JSONArray arrayGames = response.getJSONArray("GAME_LIST");
                L.t(getActivity(), "Array Length = "+Integer.toString(arrayGames.length()));
                for (int i = 0; i < arrayGames.length(); i++) {
                    JSONObject currentGame = arrayGames.getJSONObject(i);
                    Integer gameId = currentGame.getInt("GAME_ID");
                    String gameName = currentGame.getString("GAME_NAME");
                    //data.append(gameName);
                    //L.t(getActivity(), gameName);
                    String gameDescription = currentGame.getString("GAME_DESC");
                    String gameCategory = currentGame.getString("GAME_CATE");
                    String gameCode = currentGame.getString("GAME_CODE");
                    String gameUrl = currentGame.getString("GAME_URL");

                    //L.t(getActivity(),"game Code: "+gameCode);
                    //String gameUrl = "http://107.178.214.92/webservices/index.php?tag=game_view&imei=" + imei_no + "&game_code=" + gameCode;
                    String gameIconUrl = currentGame.getString("GAME_ICON");
                    Games game = new Games();
                    game.setGame_name(gameName);
                    game.setGame_id(gameId);
                    game.setGame_type(gameCategory);
                    game.setGame_url(gameUrl);
                    game.setGame_code(gameCode);
                    /*JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,"http://107.178.214.92/webservices/st_game.php?gcd="+gameCode+"&imei_no="+imei_no, (String)null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    //L.t(getActivity(), response.toString());
                                    //Toast.makeText(getContext(),response.toString(),Toast.LENGTH_LONG);
                                    try {

                                        JSONArray arrayFiles = response.getJSONArray("IMAGE_LIST");
                                        for(int j = 0; j < arrayFiles.length();j++){
                                            JSONObject currentFile = arrayFiles.getJSONObject(j);
                                            String fileName = currentFile.getString("IMAGE_NAME");
                                            listFiles.add(fileName);
                                        }

                                    }catch(Exception e){
                                        L.t(getActivity(),"not able to download");
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });*/


                    //requestQueue.add(request);
                    //game.setGame_files(listFiles);
                    //game.setPhoneGroup(phoneGroup);
                    game.setGame_description(gameDescription);
                    game.setGame_image_url(gameIconUrl);

                    listgames.add(game);

                }

                //L.T(getActivity(), data.toString());
            } catch (JSONException je) {
                //Toast.makeText(getView().getContext(), "Could not load Games", Toast.LENGTH_LONG);
            }
        }
        return listgames;
    }

/*    SwipeDismissRecyclerViewTouchListener listener = new SwipeDismissRecyclerViewTouchListener.Builder(
            gamesTabRecyclerView,
            new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
                @Override
                public boolean canDismiss(int position) {
                    return true;
                }

                @Override
                public void onDismiss(View view) {
                    int id = gamesTabRecyclerView.getChildLayoutPosition(view);
                    gamesAdapter.notifyItemRemoved(id);
                    gamesAdapter.notifyDataSetChanged();

                    Toast.makeText(view.getContext(), String.format("Delete item %d",id), Toast.LENGTH_LONG).show();
                }
            })
            .setIsVertical(false)
            .setItemTouchCallback(
                    new SwipeDismissRecyclerViewTouchListener.OnItemTouchCallBack() {
                        @Override
                        public void onTouch(int index) {

                        }
                    })
            .create();

    gamesTabRecyclerView.setOnClickListener(listener);*/
}
