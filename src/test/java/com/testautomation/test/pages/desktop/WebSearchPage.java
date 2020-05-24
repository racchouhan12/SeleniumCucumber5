package com.testautomation.test.pages.desktop;

import com.automation.exceptions.InvalidInputException;
import com.testautomation.test.pages.ResultPage;
import com.testautomation.test.pages.SearchPage;
import org.openqa.selenium.By;

public class WebSearchPage extends SearchPage {

    final String submitButtonLocator = "btnK";

    @Override
    public ResultPage searchTextOnGoogleHomePage(String searchText) throws InvalidInputException {
        setTextInElement(By.cssSelector(searchTextBoxLocator), searchText);
        clickOnElement(By.name(submitButtonLocator));
        return ResultPage.getInstance();
    }
}
