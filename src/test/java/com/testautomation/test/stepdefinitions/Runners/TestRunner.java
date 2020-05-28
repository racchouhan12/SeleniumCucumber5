package com.testautomation.test.stepdefinitions.Runners;


import com.automation.helpers.KEYS;
import com.automation.helpers.ProjectConstants;
import com.automation.scripts.Main;
import com.automation.utilities.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.StringJoiner;


@CucumberOptions(
        features = {"src/test/java/com/testautomation/test/feature"},
        glue = {"com.testautomation.test.stepdefinitions"}
)

public class TestRunner extends AbstractTestNGCucumberTests {
    private static Logger logger = LogManager.getLogger(com.testautomation.test.stepdefinitions.Runners.TestRunner.class.getName());
    private static Main main = new Main();
    ThisRun thisRun = ThisRun.getInstance();
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    DriverUtils driverUtils;
    private static String browser;
    private static String platform;
    private static String reportsData[] = new String[2];
    private static StringJoiner joinBrowser = new StringJoiner(",", "[", "]");


    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @BeforeSuite
    public void beforeSuiteActivities() {
        reportsData[0] = main.createReportFolder("browser");
        reportsData[1] = thisRun.getAsString(KEYS.REPORT_PATH.name());
    }

    @BeforeTest(alwaysRun = true)
    @Parameters({"browser", "platform", "tags", "subPlatform", "deviceId"})
    public void beforeClassActivities(@Optional("") String browser, String platform, String tags, @Optional("") String subPlatform, @Optional("") String deviceId) {
        this.browser = browser;
        this.platform = platform;
        joinBrowser.add(this.browser);
        thisRun.add(KEYS.PLATFORM, platform);
        thisRun.add(KEYS.SUB_PLATFORM, subPlatform);
        thisRun.add(KEYS.DEVICE_ID, deviceId);
        thisRun.add(KEYS.BROWSER, joinBrowser.toString());

        System.setProperty("cucumber.filter.tags", tags);
        System.setProperty("cucumber.plugin", "json:reports/cucumber" + StringUtil.getRandomAlphaNumStringOfLen(5) + ".json");

        if (this.platform.equalsIgnoreCase(ProjectConstants.PLATFORM_MOBILITY)) {
            AppiumServerUtils.stopServer();
            AppiumServerUtils.startServer();
        }
    }

    @Before
    public void setup(Scenario scenario) throws IOException {
        logger.info("Running scenario : " + scenario.getName());
        loadFromPropertiesFile();
        addDriverProperties(platform, browser);
        printOptions();
    }

    private void printOptions() {
        logger.info("Platform: " + thisRun.getAsString(KEYS.PLATFORM.name()));
        logger.info("Sub Platform: " + thisRun.getAsString(KEYS.SUB_PLATFORM.name()));
        logger.info("Device id/Udid: " + thisRun.getAsString(KEYS.DEVICE_ID.name()));
        logger.info("Browser: " + browser);
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

    private void addDriverProperties(String platform, String browser) throws MalformedURLException {
        driverUtils = new DriverUtils(platform, browser);
        driver.set(driverUtils.getDriver());
    }

    protected WebDriver getDriver() {
        return driver.get();
    }


    @After
    public void tearDown(Scenario scenario) {
        logger.info("Inside teardown(), now Browser will quit.....");
        scenario.log("Browser :" + browser);
        ScreenShotUtils.embedScreenShotInReport(scenario, scenario.getName(), getDriver());
        driverUtils.quit(driver.get());
    }


    @AfterClass
    public void afterActivities() {
        if (thisRun.getAsString(KEYS.PLATFORM.name()).equalsIgnoreCase(ProjectConstants.PLATFORM_MOBILITY)) {
            AppiumServerUtils.stopServer();
        }
    }

    @AfterSuite
    public void afterTestActivities() {
        com.automation.utilities.Reporter.main(reportsData[1], reportsData[0]);
        FileUtils.deleteFileWithExtension(thisRun.getAsString(KEYS.REPORT_PATH.name()), ".json");
    }

}
