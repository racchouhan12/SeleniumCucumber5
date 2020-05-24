package com.testautomation.test.stepdefinitions;

import com.automation.utilities.DriverUtils;
import com.automation.utilities.ScreenShotUtils;
import com.automation.utilities.ThisRun;
import com.automation.helpers.KEYS;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;

public class Hooks {

    private ThisRun thisRun  = ThisRun.getInstance();
    private static Logger logger = LogManager.getLogger(Hooks.class.getName());
    DriverUtils driverUtils;
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    @Before
    public void setup(Scenario scenario) throws IOException {
        logger.info("Running scenario : "+ scenario.getName());
        loadFromPropertiesFile();
        addDriverProperties();
        printOptions();
    }

    private void printOptions() {
        logger.info("Platform: " + thisRun.getAsString(KEYS.PLATFORM.name()));
        logger.info("Sub Platform: " + thisRun.getAsString(KEYS.SUB_PLATFORM.name()));
        logger.info("Device id/Udid: " + thisRun.getAsString(KEYS.DEVICE_ID.name()));
        logger.info("Browser: " + thisRun.getAsString(KEYS.BROWSER.name()));
        logger.info("URL: " + thisRun.getAsString(KEYS.APP_URL.name()));
        logger.info("OS: " + thisRun.getAsString(KEYS.OS_NAME.name()));
    }

    private void loadFromPropertiesFile() throws IOException {
        FileInputStream fileStream =
                new FileInputStream(thisRun.get(KEYS.TEST_RESOURCES.toString()) + "/CommonProperties.properties");
        Properties commonProperties = new Properties();
        commonProperties.load(fileStream);
        thisRun.add(KEYS.APP_URL, commonProperties.getProperty(KEYS.APP_URL.toString()));
    }

    private void addDriverProperties() throws MalformedURLException {
        driverUtils = new DriverUtils(thisRun.getAsString(KEYS.PLATFORM.name()), thisRun.getAsString(KEYS.BROWSER.toString()));
        driver.set(driverUtils.getDriver());
    }

    protected WebDriver getDriver() {
        return driver.get();
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("Inside teardown(), now Browser will quit.....");
        ScreenShotUtils.embedScreenShotInReport(scenario, scenario.getName(), getDriver());
        driverUtils.quit(driver.get());
    }

}
