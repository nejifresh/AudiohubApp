package com.nejitawo.audiohub.Model;

import android.content.Context;

import com.parse.ParseObject;

/**
 * Created by devthehomes on 13/03/2017.
 */

public class Categories {
    private String docType;
    private String name;
    private String imageLocation;

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public static Categories giveFullDetails(ParseObject revision, Context context){
        Categories t = new Categories();
        ParseObject docs = revision;
        try{
            t.setImageLocation(docs.getString("imagelink"));
            t.setDocType(docs.getString("type"));
            t.setName(docs.getString("name"));
        } catch (Exception e){
            e.printStackTrace();
        }

        return  t;
    }
}
