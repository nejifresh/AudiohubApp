package com.nejitawo.audiohub.Comedy;

import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by devthehomes on 28/04/2017.
 */

public interface ComedyContract {
    void showComedyWorks();

    ParseQuery<ParseObject> runQuery(String className, String orderBy, String category, int limit);

}
