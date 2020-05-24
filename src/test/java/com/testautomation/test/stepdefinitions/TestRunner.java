package com.testautomation.test.stepdefinitions;

import com.automation.helpers.KEYS;
import com.automation.helpers.ProjectConstants;
import com.automation.scripts.Main;
import com.automation.utilities.AppiumServerUtils;
import com.automation.utilities.ThisRun;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"src/test/java/com/testautomation/test/feature"},
        glue = {"com.testautomation.test.stepdefinitions"},
        plugin = {"pretty", "html:reports/html", "json:reports/cucumber.json"}
)

public class TestRunner extends AbstractTestNGCucumberTests {
    private static String reportPath;
    private static Main main = new Main();
    ThisRun thisRun = ThisRun.getInstance();

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @BeforeSuite
    public void beforeSuiteActivities() {
        reportPath = main.createReportFolder("true");
        System.setProperty("cucumber.filter.tags", thisRun.getAsString(KEYS.TAG.name()));
        if(thisRun.getAsString(KEYS.PLATFORM.name()).equalsIgnoreCase(ProjectConstants.PLATFORM_MOBILITY)) {
            AppiumServerUtils.stopServer();
            AppiumServerUtils.startServer();
        }

    }

    @AfterSuite
    public void afterActivities() {
        com.automation.utilities.Reporter.main(reportPath);
        if(thisRun.getAsString(KEYS.PLATFORM.name()).equalsIgnoreCase(ProjectConstants.PLATFORM_MOBILITY)) {
            AppiumServerUtils.stopServer();
        }
    }

}
