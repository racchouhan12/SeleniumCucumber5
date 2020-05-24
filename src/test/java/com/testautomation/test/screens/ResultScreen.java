package com.testautomation.test.screens;

import org.openqa.selenium.By;

public class ResultScreen extends BaseScreen {


    public static ResultScreen getInstance() {
        return new ResultScreen();
    }

    public void clickOnResultByLinkText(String linkToBeClicked) {
        String finalLocatorOfResult = "//h3[text() = '"+linkToBeClicked+"']";
        waitForElementToBeClickableAndClickOnElement(By.xpath(finalLocatorOfResult));
    }
}
