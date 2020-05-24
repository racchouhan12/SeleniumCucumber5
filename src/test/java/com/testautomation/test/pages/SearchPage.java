package com.testautomation.test.pages;

import com.automation.exceptions.InvalidInputException;
import com.automation.helpers.KEYS;
import com.automation.helpers.ProjectConstants;
import com.testautomation.test.pages.desktop.WebSearchPage;
import com.testautomation.test.pages.mobility.android.AndroidSearchPage;
import com.automation.utilities.ThisRun;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class SearchPage extends BasePage {
    protected final String searchTextBoxLocator = "input[name='q']";

    public static SearchPage getInstance() throws InvalidInputException {
        ThisRun thisRun = ThisRun.getInstance();
        Logger logger = LogManager.getLogger(SearchPage.class.getName());
        String platform = thisRun.getAsString(KEYS.PLATFORM.name());
        String subPlatform = thisRun.getAsString(KEYS.SUB_PLATFORM.name());
        String screenNameSuffix = SearchPage.class.getName();
        switch (platform) {
            case ProjectConstants.PLATFORM_DESKTOP:
                return new WebSearchPage();
            case ProjectConstants.PLATFORM_MOBILITY:
               if (subPlatform.equals(ProjectConstants.PLATFORM_ANDROID)) {
                   return new AndroidSearchPage();
               }
            default:
                throw new InvalidInputException("Invalid platform type - '" + platform + "' provided to instantiate instance of " + screenNameSuffix);
        }
    }
     public abstract ResultPage searchTextOnGoogleHomePage(String searchText) throws InvalidInputException;
}
