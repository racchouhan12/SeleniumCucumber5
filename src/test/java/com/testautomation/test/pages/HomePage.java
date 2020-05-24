package com.testautomation.test.pages;

import com.automation.exceptions.InvalidInputException;
import com.automation.helpers.KEYS;
import com.automation.helpers.ProjectConstants;
import com.testautomation.test.pages.desktop.WebHomePage;
import com.testautomation.test.pages.mobility.android.AndroidHomePage;
import com.automation.utilities.ThisRun;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class HomePage extends BasePage {
    public static HomePage getInstance() throws InvalidInputException {
        ThisRun thisRun = ThisRun.getInstance();
        Logger logger = LogManager.getLogger(SearchPage.class.getName());
        String platform = thisRun.getAsString(KEYS.PLATFORM.name());
        String subPlatform = thisRun.getAsString(KEYS.SUB_PLATFORM.name());
        String screenNameSuffix = HomePage.class.getName();
        switch (platform) {
            case ProjectConstants.PLATFORM_DESKTOP:
                return new WebHomePage();
            case ProjectConstants.PLATFORM_MOBILITY:
                if (subPlatform.equals(ProjectConstants.PLATFORM_ANDROID)) {
                    return new AndroidHomePage();
                }
            default:
                throw new InvalidInputException("Invalid platform type - '" + platform + "' provided to instantiate instance of " + screenNameSuffix);
        }
    }

    public String getCurrentScreenURL() {
        return getCurrentURL();
    }
}
