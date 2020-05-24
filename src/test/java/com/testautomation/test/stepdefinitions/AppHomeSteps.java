package com.testautomation.test.stepdefinitions;

import com.automation.exceptions.InvalidInputException;
import com.testautomation.test.businessflows.AppHome;
import io.cucumber.java8.En;

public class AppHomeSteps implements En {

    public AppHomeSteps() {

        Then("I verify official website is launched$", () ->        {
            try {
                new AppHome().verifyHomeScreenIsOpen();
            } catch (InvalidInputException e) {
                e.printStackTrace();
            }
        });
    }
}
