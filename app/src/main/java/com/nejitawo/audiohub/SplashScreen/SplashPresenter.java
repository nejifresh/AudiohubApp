package com.nejitawo.audiohub.SplashScreen;

import com.parse.ParseUser;

/**
 * Created by devthehomes on 28/04/2017.
 */

public class SplashPresenter {

    SplashContract contract;

    public SplashPresenter(SplashContract contract){
        this.contract = contract;
    }

    public void animationStarted(){
        contract.animationEntry();
    }

    public void nextActivityStarted(ParseUser currentUser){
        contract.proceedNext(currentUser);
    }

}
