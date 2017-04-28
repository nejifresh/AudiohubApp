package com.nejitawo.audiohub.Util;

import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by devthehomes on 28/04/2017.
 */

public class RunQuery {
    String classname, orderBy ;
   int limit;
    public RunQuery(String className, String orderBy, int limit){
        this.classname = className;
        this.orderBy = orderBy;
        this.limit = limit;
    }

    public ParseQuery<ParseObject> getResults(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
               classname);
        query.orderByDescending(orderBy);
        query = query.setLimit(limit);
        return query;
    }
}
