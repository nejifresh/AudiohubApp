package com.nejitawo.audiohub.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nejitawo.audiohub.Activity.MusicActivity;
import com.nejitawo.audiohub.Util.GlobalClass;
import com.nejitawo.audiohub.Model.Categories;
import com.nejitawo.audiohub.R;
import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by devthehomes on 09/03/2017.
 */

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.MyViewHolder> {
    private Context mContext;
    private List<Categories> catList;

    //create view holder class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, numTracks;
        public ImageView coverImage, overflow;

        //constuctor
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            numTracks = (TextView) view.findViewById(R.id.count);
            coverImage = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public CatAdapter(Context mContext, List<Categories> catList) {
        this.mContext = mContext;
        this.catList = catList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final GlobalClass globalVariable = (GlobalClass) mContext.getApplicationContext();
        final Categories categories = catList.get(position);
        holder.title.setText(categories.getName());
       // holder.numTracks.setText(categories.getDocType());
        Glide.with(mContext).load(categories.getImageLocation()).into(holder.coverImage);

        ParseQuery<ParseObject> trackQuery = new ParseQuery<ParseObject>("Titles");
        trackQuery.whereEqualTo("category",categories.getName());
        trackQuery.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                if (e==null){
                    holder.numTracks.setText(String.valueOf(count) + " tracks");
                }
            }
        });


        holder.coverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalVariable.setCategory(categories.getName());


               Intent intent = new Intent(mContext, MusicActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
              //  Toast.makeText(mContext,globalVariable.getFileLocation(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return catList.size();
    }


    //Show the popup menu when the 3 dots are tapped on
    private void showPopUpMenu(View view){
        //inflate menu
        PopupMenu popupMenu = new PopupMenu(mContext,view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_album,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new CatAdapter.MyMenuItemClickListener());
    }


    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }


}
