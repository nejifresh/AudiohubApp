package com.nejitawo.audiohub.PlayAudio;

/**
 * Created by devthehomes on 28/04/2017.
 */

public class AudioPresenter {
    AudioContract contract;

    public AudioPresenter(AudioContract contract){
        this.contract = contract;
    }

    public void setupViewLoading(String artistTitle){
        contract.setUpView(artistTitle);
    }

    public void playAudioFile(String fileTitle,String titleId, String storageMade, String fileLocation){
        contract.playAudio(fileTitle, titleId,  storageMade, fileLocation);
    }

    public void downloadAudioFile(String fileTitle,int totalDownloads, String storageMade, String fileLocation){
        contract.downloadAudioFile(fileTitle, totalDownloads,  storageMade, fileLocation);
    }


}
