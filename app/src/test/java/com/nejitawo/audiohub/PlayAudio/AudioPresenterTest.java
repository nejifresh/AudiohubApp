package com.nejitawo.audiohub.PlayAudio;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by devthehomes on 28/04/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AudioPresenterTest {

    AudioPresenter presenter;
    AudioContract contract;
    @Before
    public void setUp() throws Exception {
        presenter = new AudioPresenter(contract);
    }

    @Test
    public void setupViewLoading() throws Exception {
        //Arrange

        //Act


        //Assert
    }

    @Test
    public void playAudioFile() throws Exception {

    }

    @Test
    public void downloadAudioFile() throws Exception {

    }

}