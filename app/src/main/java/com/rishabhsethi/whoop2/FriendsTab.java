package com.rishabhsethi.whoop2;
import android.content.AsyncQueryHandler;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by rishabh on 25/5/15.
 */


public class FriendsTab extends Fragment{
    //ListView friendsRecyclerView;
    //RecyclerView.LayoutManager friendsLayoutManager;
    //FavoritesAdapter friendsAdapter;
    private Realm realm;
    //private WorkerThread workerThread;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.friends_tab,container,false);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(v.getContext()).build();
        //Realm.deleteRealm(realmConfig);
        realm = Realm.getInstance(realmConfig);

        RealmResults<User> timeStamps = realm.where(User.class).findAll();
        final FriendsAdapter adapter = new FriendsAdapter(v.getContext(), R.id.list, timeStamps, true);
        ListView listView = (ListView) v.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                User friend = adapter.getRealmResults().get(i);
                //Message message = buildMessage(AsyncQueryHandler.WorkerHandler.REMOVE_TIMESTAMP, timeStamp.getTimeStamp());

                //workerThread.workerHandler.sendMessage(message);
                return true;
            }
        });

        // initialize and set the list adapter
        //listView.setAdapter(adapter);

    return v;
    }
}

