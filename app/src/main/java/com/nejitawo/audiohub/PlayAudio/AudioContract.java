package com.nejitawo.audiohub.PlayAudio;

/**
 * Created by devthehomes on 28/04/2017.
 */

public interface AudioContract {
    void setUpView(String artistTitle);
    void playAudio(String fileTitle, String titleId, String storageMade, String fileLocation);
    void downloadAudioFile(String fileTitle, int totalDownloads, String storageMade, String fileLocation);


}
