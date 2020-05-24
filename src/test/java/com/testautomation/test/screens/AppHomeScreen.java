package com.testautomation.test.screens;

public class AppHomeScreen extends BaseScreen {

    public static AppHomeScreen getInstance() {
        return new AppHomeScreen();
    }

    public String getCurrentScreenURL() {
        return getCurrentURL();

    }
}
