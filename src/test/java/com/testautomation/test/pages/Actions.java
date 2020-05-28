package com.testautomation.test.pages;

import com.automation.utilities.ThisRun;
import com.google.common.base.Function;
import com.testautomation.test.stepdefinitions.Runners.TestRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;



public abstract class Actions extends TestRunner {
    protected ThisRun thisRun = ThisRun.getInstance();
    private final int DEFAULT_TIMEOUT = 50;
    private final long DEFAULT_POLLING_TIME = 1L;
    private Logger logger = LogManager.getLogger(BasePage.class.getName());

    protected void setTextInElement(By by, String text) {
        waitForElementToBeClickableBy(by).sendKeys(text);
    }

    protected void waitFor(long inMilliSecs) {
        try {
            Thread.sleep(inMilliSecs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected String getCurrentURL() {
        return getDriver().getCurrentUrl();
    }

    protected boolean isElementEnabled(By by) {
        return getDriver().findElement(by).isEnabled();
    }

    protected Boolean isElementsPresent(By by) {
        List<WebElement> list = getListOfAllElementsBy(by);
        if (list.size() > 0)
            return true;
        return false;
    }

    /*protected void clickOKInModalWindow() {
        clickOnElement(By.cssSelector(MODAL_OK_BTN_LOCATOR));
    }*/

    protected boolean waitTillElementDisabledPropertyIsSetToFalse(By by, long pollTimeInMilliSec) {
        for (int i = 0; i < 400; i++) {
            if (isElementEnabled(by)) {
                return true;
            }
            waitFor(pollTimeInMilliSec);
        }
        return false;
    }

   /* protected void clickCancelInModalWindow() {
        clickOnElement(By.cssSelector(MODAL_CANCEL_BTN_LOCATOR));
    }*/

    protected Boolean isElementPresent(By by) {
        try {
            getDriver().findElement(by);
            return true;
        } catch (Exception e) {
            logger.info("Element not found" + by.toString());
            return false;
        }
    }

    protected void refreshPage() {
        getDriver().navigate().refresh();
    }

    protected void clickOnElement(By by) {
        waitForElementToBeClickableBy(by).click();
    }

    protected void clickOnElementWithoutWait(By by) {
        getDriver().findElement(by).click();
    }

    protected void clickOnElement(WebElement element) {
        element.click();
    }

    protected void shouldAcceptAlert(Boolean value) {
        if (value) {
            getDriver().switchTo().alert().accept();
        } else {
            getDriver().switchTo().alert().dismiss();
        }
    }

    protected String getAttribute(String attributeName, By by) {
        return waitForElementToBeClickableBy(by).getAttribute(attributeName);
    }

    protected String getAttribute(String attributeName, By by, int timeout) {
        return waitForElementToBeVisibleBy(by, timeout).getAttribute(attributeName);
    }

    protected String getAttributeWithoutWait(String attributeName, By by) {
        return getDriver().findElement(by).getAttribute(attributeName);
    }

    protected void moveMouseToElement(By by) {
        org.openqa.selenium.interactions.Actions action = new org.openqa.selenium.interactions.Actions(getDriver());
        action.moveToElement(waitForElementToBeClickableBy(by)).perform();
    }

    protected void sendKeys(Keys keys) {
        org.openqa.selenium.interactions.Actions action = new org.openqa.selenium.interactions.Actions(getDriver());
        action.sendKeys(keys).perform();
    }

    protected void sendKeys(String keys) {
        org.openqa.selenium.interactions.Actions action = new org.openqa.selenium.interactions.Actions(getDriver());
        action.sendKeys(keys).perform();
    }

    protected void moveMouseToElementUsingJS(By by) {
        String javaScript = "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initMouseEvent('mouseover',true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                "arguments[0].dispatchEvent(evObj);";

        ((JavascriptExecutor) getDriver()).executeScript(javaScript, getWebElementWithoutWait(by));
    }

    protected String getTextFromElementBy(By by) {
        return waitForElementToBeVisibleBy(by).getText();
    }

    protected String getTextFromElementBy(By by, int timeout) {
        return waitForElementToBeVisibleBy(by, timeout).getText();
    }

    protected String getTextFromElementWithoutWaitBy(By by) {
        return getDriver().findElement(by).getText();
    }


    protected String getTextFromWebElement(WebElement we) {
        return we.getText();
    }

    protected void switchToFrameByName(String name) {
        WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(name)).switchTo().frame(name);
    }

    protected void switchToFrameByWebElementBy(By by) {
        WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by))
                .switchTo().frame(waitForElementToBeVisibleBy(by));
    }

    protected void scrollDownIntoViewBy(By by) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true)", waitForElementToBeClickableBy(by));
    }

    protected void scrollDown(int times) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        for (int i = 0; i < times; i++) {
            js.executeScript("window.scrollBy(0,300)");
        }
    }

    protected void executeJavaScriptCommand(String jsCommand) {
        ((JavascriptExecutor) getDriver()).executeScript(jsCommand);
    }

    protected List<WebElement> getListOfAllElementsWithWaitBy(By by) {
        try {
            waitForElementToBeVisibleBy(by, 20);
            return getDriver().findElements(by);
        } catch (TimeoutException | NoSuchElementException e) {
            return getDriver().findElements(by);
        }
    }

    protected List<WebElement> getListOfAllElementsWithWaitBy(By by, int timeOut) {
        List<WebElement> we = new ArrayList<>();
        try {
            waitForElementToBeVisibleBy(by, timeOut);
            we = getDriver().findElements(by);
            return we;
        } catch (TimeoutException | NoSuchElementException e) {
            return we;
        }
    }

    protected WebElement getWebElementWithoutWait(By by) {
        return getDriver().findElement(by);
    }

    protected WebElement getWebElementWithWait(By by) {
        return waitForElementToBeClickableBy(by);
    }

    protected WebElement getWebElementFromWebElement(WebElement ele, By by) {
        return ele.findElement(by);
    }


    protected List<WebElement> getListOfAllElementsBy(By by) {
        return getDriver().findElements(by);
    }

    protected void selectElementsByTextName(By by, String... value) {
        List<WebElement> elements = getListOfAllElementsBy(by);
        for (int i = 0; i < value.length; i++) {
            for (WebElement element : elements) {
                if (element.getText().equals(value[i])) {
                    element.click();
                    elements.remove(element);
                    break;
                }
            }
        }
    }

    /*protected void selectOptionsByTextName(String locatorByXpath, String value) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        String[] values = value.split(PortalConstants.COMMA_CONSTANT);
        for (int i = 0; i < values.length; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(locatorByXpath, values[i].trim())))).click();
        }
    }*/

    protected WebElement waitForElementToBeClickableBy(By by) {
        return waitForElementToBeClickableBy(by, DEFAULT_TIMEOUT);
    }

    protected WebElement waitForElementToBeClickableBy(By by, int timeOut) {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeOut);
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    protected Boolean waitForElementsToDisappear(By by) {
        WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_TIMEOUT);
        return wait.until(ExpectedConditions.invisibilityOfAllElements(getDriver().findElements(by)));
    }

    /*protected Boolean waitForElementToDisappear(By by, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Clock.systemDefaultZone(), Sleeper.SYSTEM_SLEEPER, timeout, 10L);
        return wait.until(ExpectedConditions.invisibilityOf(driver.findElement(by)));
    }*/

    protected Boolean waitForElementToDisappear(By elementToBeDisappearLocator, By clickActionElementToMakeItHappenLocator, long pollingTimeInSecs, int maxRetry) {
        boolean isElementDisappeared = !isElementPresent(elementToBeDisappearLocator);
        for (int attempt = 1; attempt <= maxRetry; attempt++) {
            if (isElementDisappeared) {
                logger.info("Element " + elementToBeDisappearLocator.toString() + "disappeared");
                return true;
            } else {
                logger.info("Element " + elementToBeDisappearLocator.toString() + "still present..");
                clickOnElement(clickActionElementToMakeItHappenLocator);
                isElementDisappeared = !isElementPresent(elementToBeDisappearLocator);
                waitFor(pollingTimeInSecs);
            }
        }
        return isElementDisappeared;
    }

    protected WebElement waitForElementToBeVisibleBy(By by) {
        return waitForElementToBeVisibleBy(by, DEFAULT_TIMEOUT);
    }

    protected WebElement waitForElementToBeVisibleBy(By by, int timeOut) {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeOut);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected WebElement waitForElementToBePresentBy(By by, int timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    protected WebElement getWebElementWithFluentWaitBy(By by) {
        List<Class<? extends Throwable>> list = new ArrayList<>();
        list.add(ElementNotInteractableException.class);
        list.add(NoSuchElementException.class);
        FluentWait<WebDriver> wait = new FluentWait<>(getDriver());
        wait.pollingEvery(Duration.ofSeconds(DEFAULT_POLLING_TIME));
        wait.withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT));
        wait.ignoreAll(list);

        Function<WebDriver, WebElement> function = webDriver -> {
            logger.info("Checking for the object!!");
            WebElement element = webDriver.findElement(by);
            if (element != null) {
                logger.info("Element is found:" + by.toString());
            }
            return element;
        };

        return wait.until(function);

    }

    protected Boolean isWebElementPresentBy(By elementToBePresent, int timeout) {
        boolean isElementPresent = isElementPresent(elementToBePresent);
        for (int attempt = 1; attempt <= timeout; attempt++) {
            if (isElementPresent) {
                logger.info("Element " + elementToBePresent.toString() + "found");
                return true;
            } else {
                logger.info("Element " + elementToBePresent.toString() + "still not found..");
                isElementPresent = !isElementPresent(elementToBePresent);
                thisRun.waitFor(1);
            }
        }
        return isElementPresent;
    }


}
