package com.nejitawo.audiohub.LatestWorks;

import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by devthehomes on 28/04/2017.
 */

public class LastestPresenter {

    LastestContract contract;

    public LastestPresenter(LastestContract contract){
        this.contract = contract;
    }

    public void showLatestItems(){
        contract.loadLatestWorks();
    }

    public ParseQuery<ParseObject> queryRunning(String className, String orderBy, int queryLimit){
        return contract.runQuery(className,orderBy,queryLimit);
    }

}
