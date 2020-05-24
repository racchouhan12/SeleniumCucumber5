package com.testautomation.test.stepdefinitions;

import com.automation.exceptions.InvalidInputException;
import com.testautomation.test.businessflows.Search;
import io.cucumber.java8.En;


public class SearchSteps implements En {

    public SearchSteps() {
       Given("^I search for \"([^\"]*)\"$", (String text) -> {
           try {
               new Search().searchText(text);
           } catch (InvalidInputException e) {
               e.printStackTrace();
           }
       });

    }

}
