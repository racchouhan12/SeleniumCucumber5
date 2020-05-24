package com.testautomation.test.pages.mobility.android;

import com.automation.exceptions.InvalidInputException;
import com.testautomation.test.pages.HomePage;
import com.testautomation.test.pages.ResultPage;
import org.openqa.selenium.By;

public class AndroidResultPage extends ResultPage {
    private final String resultLinkLocator = "//div[@role='heading']/div[text()='Selenium']";

    @Override
    public HomePage clickOnResultByLinkText(String linkToBeClicked) throws InvalidInputException {
        clickOnElement(By.xpath(resultLinkLocator));
        return HomePage.getInstance();
    }
}
