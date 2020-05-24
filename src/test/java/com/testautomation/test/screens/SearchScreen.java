package com.testautomation.test.screens;

import org.openqa.selenium.By;

public class SearchScreen extends BaseScreen{

    final String searchTextBoxLocator = "input[name='q']";
    final String submitButtonLocator = "btnK";

    public static SearchScreen getInstance() {
         return new SearchScreen();
    }

    public void searchTextOnGoogleHomePage(String searchData) {
        sendText(By.cssSelector(searchTextBoxLocator), searchData);
        waitForElementToBeClickableAndClickOnElement(By.name(submitButtonLocator));
    }

    public void clickOnBuyNow() {
        //clickOnElement(By.xpath("//div[@class='product__listing product__list']/li[1]/div[2]/div[4]/div/div/div/div[2]/div/form[2]/button"));
        waitForElementToBeClickableAndClickOnElement(By.xpath(
                "//h2[contains(text(),'Apple iPhone XS (Space Grey, 512 GB, 4 GB RAM)')]/ancestor::li//button[@id='addToCartButton']"));
    }

    public void clickOnFileChoose() {
        try {
            Thread.sleep(2000);
            sendTextInElement("espauto@ness.com", By.id("email"));
            sendTextInElement("password1", By.id("password"));
            clickOnElement(By.id("signin"));
            //Thread.sleep(10000);
            //clickOnElement(By.xpath("//button[contains(text(), 'OK')))]"));
            Thread.sleep(10000);
            clickOnElement(By.cssSelector(".task-info-bar:nth-of-type(1) i[ng-click*='openTaskDetail']"));
            Thread.sleep(10000);
            clickOnElement(By.cssSelector(".task-info-bar:nth-of-type(1) i[ng-click*='goToPartDetails']"));
            Thread.sleep(10000);
            sendTextInElement("D:\\TestPDF.pdf", By.id("fileuploadarea-mobile"));
            Thread.sleep(5000);
        }catch (Exception e) {
        e.printStackTrace();
        }
    }


}
