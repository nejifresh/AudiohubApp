package com.nejitawo.audiohub.SplashScreen;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by devthehomes on 28/04/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class SplashPresenterTest {

    SplashPresenter presenter;
    SplashContract contract;

    @Before
    public void setUp() throws Exception {

        presenter = new SplashPresenter(contract);
    }

    @Test
    public void nextActivityStarted() throws Exception {
        //Arrange

        //Act


        //Assert
    }

}