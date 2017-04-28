package com.nejitawo.audiohub.MusicGenres;

import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by devthehomes on 28/04/2017.
 */

public class MusicPresenter {
    MusicContract musicContract;

    public MusicPresenter(MusicContract musicContract){
        this.musicContract = musicContract;
    }

    public void showMusicGenres(){
        musicContract.showMusicGenres();
    }

    public ParseQuery<ParseObject> queryRunning(String className, String orderBy, int queryLimit){
        return musicContract.runQuery(className,orderBy,queryLimit);
    }
}
