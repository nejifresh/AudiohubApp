package com.nejitawo.audiohub.Adapters;

/**
 * Created by devthehomes on 11/16/16.
 */
import android.content.Context;
import android.content.DialogInterface;
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
import com.nejitawo.audiohub.Util.GlobalClass;
import com.nejitawo.audiohub.Model.Item;
import com.nejitawo.audiohub.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private Context mContext;
    private List<Item> itemList;

    //Create the viewHolder Class
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title, price;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view){
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            price = (TextView)view.findViewById(R.id.count);
            thumbnail = (ImageView)view.findViewById(R.id.thumbnail);
            overflow = (ImageView)view.findViewById(R.id.overflow);
        }

    }

    public ItemAdapter(Context mContext, List<Item> itemList){
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card,parent,false);
        return new  MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position){
        final GlobalClass globalClass = (GlobalClass)mContext.getApplicationContext();
    final    Item item = itemList.get(position);
        holder.title.setText(item.getName());
        holder.price.setText ("N" +  String.valueOf(item.getCost()));
        // loading album cover using Glide library
        Glide.with(mContext).load(item.getImageUrl()).into(holder.thumbnail);


        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpMenu(holder.overflow);
            }
        });
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }

    private void showDrinks(String message) {
        final GlobalClass globalVariable = (GlobalClass)mContext. getApplicationContext();

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        builder.setMessage(message).setTitle("Drinks Menu")
                .setCancelable(false)
                .setPositiveButton("ALCOHOLIC DRINKS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Post something


                    }
                });
        builder.setNegativeButton("NON ALCOHOLIC DRINKS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //Dont post anything

            }
        });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    //Show the popup menu when the 3 dots are tapped on
    private void showPopUpMenu(View view){
        //inflate menu
        PopupMenu popupMenu = new PopupMenu(mContext,view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_album,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new MyMenuItemClickListener());
    }

    /**
     * Click listener for popup menu items
     */
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

    @Override
    public int getItemCount(){
        return itemList.size();
    }

}
