package com.nejitawo.audiohub.MusicGenres;

import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by devthehomes on 28/04/2017.
 */

public interface MusicContract {
    void showMusicGenres();

    ParseQuery<ParseObject> runQuery(String className, String orderBy, int limit);

}
