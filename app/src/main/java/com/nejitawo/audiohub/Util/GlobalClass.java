package com.nejitawo.audiohub.Util;

import android.app.Application;

import com.nejitawo.audiohub.Model.Orders;
import com.parse.Parse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devthehomes on 11/16/16.

 */

public class GlobalClass extends Application {
    private String name;


    private String title;
    private String author;
    private String category;
    private String docType;
    private String imageLocation;
    private String fileLocation;
    private String titleId;
    private int downloads;
    private String storagemode;

    public void onCreate() {
        //Point to new parse server
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("OiF3XbtleTutJqT4w02uQHxK8KKowfdlbPXWzDXj")
                .clientKey("frGwd3fs39tYriEuJxwBEmp7miC2RGoae3AsOVrV")
                .server("https://parseapi.back4app.com/").build()
        );
    }


    public String getStoragemode() {
        return storagemode;
    }

    public void setStoragemode(String storagemode) {
        this.storagemode = storagemode;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
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

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
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

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }















}
