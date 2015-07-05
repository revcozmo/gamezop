package com.rishabhsethi.whoop2;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by rishabh on 1/7/15.
 */
public class FriendsAdapter extends RealmBaseAdapter<User> implements ListAdapter {

    private static class MyViewHolder {
        RelativeLayout friend;
        TextView friendName;
        ImageView friendImage;
        String phoneNumber;
        Button Binvite;
        LinearLayout friendGames;
        ImageView game1;
        ImageView game2;
        ImageView game3;
        ImageView game4;
    }

    public FriendsAdapter(Context context, int resId,
                     RealmResults<User> realmResults,
                     boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.friend_layout, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.friendName = (TextView) convertView.findViewById(R.id.friendName);
            viewHolder.friendImage = (ImageView)convertView.findViewById(R.id.friendImage);
            viewHolder.friendGames = (LinearLayout)convertView.findViewById(R.id.friendExpandable);
            viewHolder.Binvite = (Button)convertView.findViewById(R.id.invite);
            viewHolder.friend = (RelativeLayout)convertView.findViewById(R.id.friend);
            viewHolder.game1 = (ImageView)convertView.findViewById(R.id.gameOne);
            viewHolder.game2 = (ImageView)convertView.findViewById(R.id.gameTwo);
            viewHolder.game3 = (ImageView)convertView.findViewById(R.id.gameThree);
            viewHolder.game4 = (ImageView)convertView.findViewById(R.id.gameFour);
            convertView.setTag(viewHolder);
        } else {
            viewHolder =  (MyViewHolder)convertView.getTag();
        }

        User item = realmResults.get(position);
        viewHolder.friendName.setText(item.getName());
        viewHolder.friendImage.setImageResource(R.drawable.userpic);
        if(item.getRegistered()==false){
            viewHolder.Binvite.setVisibility(View.VISIBLE);
        }
        viewHolder.friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.friend.setVisibility(View.VISIBLE);
            }
        });
        return convertView;
    }

    public RealmResults<User> getRealmResults() {
        return realmResults;
    }
}