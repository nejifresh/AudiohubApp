package com.nejitawo.audiohub.Model;

import android.content.Context;

import com.parse.ParseObject;

/**
 * Created by devthehomes on 09/03/2017.
 */

public class Title {
    private String title;
    private String author;
    private String category;
    private String docType;
    private String description;
    private String imageLocation;
    private String fileLocation;
    private String titleId;
    private String level;
    private String storagemode;

    public String getStoragemode() {
        return storagemode;
    }

    public void setStoragemode(String storagemode) {
        this.storagemode = storagemode;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public static Title giveFullDetails(ParseObject revision, Context context){
        Title t = new Title();
        ParseObject docs = revision;
        try{
            t.setAuthor((String)(docs.getString("author")));
            t.setCategory(docs.getString("category"));
            t.setDescription(docs.getString("description"));
            t.setDocType(docs.getString("doctype"));
            t.setTitle(docs.getString("title"));
            t.setTitleId((String)docs.getObjectId());
            t.setFileLocation(docs.getString("filelocation"));
            t.setImageLocation(docs.getString("imagelocation"));
            t.setLevel(docs.getString("level"));
            t.setStoragemode(docs.getString("storagemode"));
        }catch (Exception e){
            e.printStackTrace();
        }

        return  t;
    }
}
