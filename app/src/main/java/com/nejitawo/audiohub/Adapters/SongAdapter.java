package com.nejitawo.audiohub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nejitawo.audiohub.Util.GlobalClass;
import com.nejitawo.audiohub.Model.Title;
import com.nejitawo.audiohub.R;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Neji on 19/06/2016.
 */
public class SongAdapter extends BaseAdapter  implements View.OnClickListener{
    final Context context;
    final List<Title> titleList;

    public SongAdapter(Context context, List<Title> choices){
        this.context = context;
        this.titleList = choices;
    }
public Title giveItemPosition(int position){
    return this.titleList.get(position);
}
    @Override
    public int getCount() {
        return this.titleList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.titleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.song_list_item, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.txtTitle);
       TextView artist = (TextView) convertView.findViewById(R.id.txtArtist);
     //  TextView total = (TextView) convertView.findViewById(R.id.txtTiming);
       //  CircleImageView imageView = (CircleImageView) convertView.findViewById(R.id.choice_image);
        CircleImageView coverImage = (CircleImageView)convertView.findViewById(R.id.choice_image) ;
//        coverImage.setTag(position);
        Title t = this.titleList.get(position);
        if(!t.equals(null)) {

            title.setText(toCamelCase(t.getTitle()));
            artist.setText("By "+t.getAuthor() );
              Glide.with(context).load(t.getImageLocation()).into(coverImage);
          // coverImage.setOnClickListener(this);

        }
      notifyDataSetChanged();

        return convertView;

    }



    public static String toCamelCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char previousChar = inputString.charAt(i - 1);
            if (previousChar == ' ') {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result = result + currentCharToUpperCase;
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
        }
        return result;
    }

    private String formatDecimal(Integer number){
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        return decimalFormat.format(number);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int position = (Integer) v.getTag();


                // click on like button
                final GlobalClass globalVariable = (GlobalClass)context.getApplicationContext();

                globalVariable.setAuthor(titleList.get(position).getAuthor());
                globalVariable.setImageLocation(titleList.get(position).getImageLocation());
                globalVariable.setFileLocation(titleList.get(position).getFileLocation());
        globalVariable.setTitle(titleList.get(position).getTitle());


        }
    }



