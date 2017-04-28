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
import com.nejitawo.audiohub.PlayAudio.AudioDetails;
import com.nejitawo.audiohub.Util.GlobalClass;
import com.nejitawo.audiohub.Model.Download;
import com.nejitawo.audiohub.R;

import java.util.List;

/**
 * Created by devthehomes on 09/03/2017.
 */

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.MyViewHolder> {
    private Context mContext;
    private List<Download> titleList;

    //create view holder class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, artist;
        public ImageView coverImage, overflow;

        //constuctor
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            artist = (TextView) view.findViewById(R.id.count);
            coverImage = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public DownloadAdapter(Context mContext, List<Download> titleList) {
        this.mContext = mContext;
        this.titleList = titleList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final GlobalClass globalVariable = (GlobalClass) mContext.getApplicationContext();
        final Download title = titleList.get(position);
        holder.title.setText(title.getTitle());
        holder.artist.setText(title.getAuthor());
        Glide.with(mContext).load(title.getImageLocation()).into(holder.coverImage);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpMenu(holder.overflow);
            }
        });
        holder.coverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalVariable.setFileLocation(title.getFileLocation());
                globalVariable.setImageLocation(title.getImageLocation());
                globalVariable.setTitle(title.getTitle());
                globalVariable.setAuthor(title.getAuthor());
                globalVariable.setTitleId(title.getTitleId());
globalVariable.setStoragemode(title.getStoragemode());
                Intent intent = new Intent(mContext, AudioDetails.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
              //  Toast.makeText(mContext,globalVariable.getFileLocation(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }


    //Show the popup menu when the 3 dots are tapped on
    private void showPopUpMenu(View view){
        //inflate menu
        PopupMenu popupMenu = new PopupMenu(mContext,view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_album,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new DownloadAdapter.MyMenuItemClickListener());
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
