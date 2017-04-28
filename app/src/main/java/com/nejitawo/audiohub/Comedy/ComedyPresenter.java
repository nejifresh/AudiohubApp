package com.nejitawo.audiohub.Comedy;

import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by devthehomes on 28/04/2017.
 */

public class ComedyPresenter {

    ComedyContract comedyContract;

    public ComedyPresenter(ComedyContract comedyContract){
        this.comedyContract = comedyContract;
    }

    public void showingComedy(){
        comedyContract.showComedyWorks();
    }

    public ParseQuery<ParseObject> queryRunning(String className, String orderBy, String category, int queryLimit){
        return comedyContract.runQuery(className,orderBy,category,queryLimit);
    }
}
