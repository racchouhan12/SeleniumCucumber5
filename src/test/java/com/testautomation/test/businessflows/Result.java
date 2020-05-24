package com.testautomation.test.businessflows;

import com.automation.exceptions.InvalidInputException;
import com.testautomation.test.pages.ResultPage;

public class Result {

     public void clickOnResultsLinkByText(String resultTextToBeClicked) throws InvalidInputException {
            //ResultScreen.getInstance().clickOnResultByLinkText(resultTextToBeClicked);
         ResultPage.getInstance().clickOnResultByLinkText(resultTextToBeClicked);
     }
}
