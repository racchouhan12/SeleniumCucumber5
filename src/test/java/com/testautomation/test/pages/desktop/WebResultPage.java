package com.testautomation.test.pages.desktop;

import com.automation.exceptions.InvalidInputException;
import com.testautomation.test.pages.HomePage;
import com.testautomation.test.pages.ResultPage;
import org.openqa.selenium.By;

public class WebResultPage extends ResultPage {
    private String finalLocatorOfResult = "//h3[text() = '%s']";

    @Override
    public HomePage clickOnResultByLinkText(String linkToBeClicked) throws InvalidInputException {
        clickOnElement(By.xpath(String.format(finalLocatorOfResult, linkToBeClicked)));
        return HomePage.getInstance();
    }
}
