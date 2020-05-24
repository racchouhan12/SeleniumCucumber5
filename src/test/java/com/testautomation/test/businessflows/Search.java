package com.testautomation.test.businessflows;

import com.automation.exceptions.InvalidInputException;
import com.testautomation.test.pages.SearchPage;

public class Search {

    public void searchText(String searchText) throws InvalidInputException {
        //SearchScreen.getInstance().searchTextOnGoogleHomePage(searchText);
        SearchPage.getInstance().searchTextOnGoogleHomePage(searchText);
    }

}
