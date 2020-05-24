package com.testautomation.test.pages.mobility.android;

import com.automation.exceptions.InvalidInputException;
import com.testautomation.test.pages.ResultPage;
import com.testautomation.test.pages.SearchPage;
import org.openqa.selenium.By;

public class AndroidSearchPage extends SearchPage {
    private final String optionLocator = "//span[contains(text(), 'seleniumhq')]";
    @Override
    public ResultPage searchTextOnGoogleHomePage(String searchText) throws InvalidInputException {
        setTextInElement(By.cssSelector(searchTextBoxLocator), searchText);
        clickOnElement(By.xpath(optionLocator));
        return ResultPage.getInstance();
    }
}
