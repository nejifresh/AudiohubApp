package com.nejitawo.audiohub.Inspirational;

import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by devthehomes on 28/04/2017.
 */

public class InspPresenter {

    InspContract inspContract;

    public InspPresenter(InspContract inspContract){
        this.inspContract = inspContract;
    }

    public void showingInsp(){
        inspContract.showInspirationalWorks();
    }

    public ParseQuery<ParseObject> queryRunning(String className, String orderBy, String category, int queryLimit){
        return inspContract.runQuery(className,orderBy,category,queryLimit);
    }
}
