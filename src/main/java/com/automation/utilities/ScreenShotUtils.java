package com.automation.utilities;

import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public final class ScreenShotUtils {
    private static Logger logger = LogManager.getLogger(ScreenShotUtils.class.getName());
    private static ThisRun thisRun = ThisRun.getInstance();

    private ScreenShotUtils() {
        logger.info("Initializing - ScreenShotUtils");

    }

    public static void embedScreenShotInReport(Scenario scenario, String scenarioName, WebDriver driver) {
        logger.debug("Finished running scenario - '" + scenarioName + "', Embedding screenshot in report");
        if (null != driver) {
            final byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenarioName); // ... and embed it in the report.
        }
    }
}