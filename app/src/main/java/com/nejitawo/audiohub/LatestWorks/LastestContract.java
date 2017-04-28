package com.nejitawo.audiohub.LatestWorks;

import com.nejitawo.audiohub.Util.RunQuery;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by devthehomes on 28/04/2017.
 */

public interface LastestContract {
    void loadLatestWorks();

    ParseQuery<ParseObject> runQuery(String className, String orderBy, int limit);
}
