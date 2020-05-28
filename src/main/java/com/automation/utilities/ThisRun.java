package com.automation.utilities;

import com.automation.helpers.KEYS;
import com.automation.helpers.ProjectConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public class ThisRun {

    private static ThisRun ourInstance;
    private HashMap<String, Object> sessionState = new HashMap<>();
    private static Logger logger = LogManager.getLogger(ThisRun.class.getName());

    public static ThisRun getInstance() {

        if (ourInstance == null) {
            logger.debug("ThisRun is null initialize it....");
            return ourInstance = new ThisRun();
        }
        return ourInstance;
    }

    private ThisRun() {
        sessionState.clear();
        add(KEYS.PROJECT_PATH, System.getProperty("user.dir"));
        add(KEYS.TEST_RESOURCES, getAsString("PROJECT_PATH") + "/src/test/resources");
        add(KEYS.REPORT_PATH, getAsString("PROJECT_PATH") + "/reports");
        add(KEYS.FEATURE_FILES_PATH, getAsString("PROJECT_PATH") + "/src/test/java/com/test/automation/feature");
        add(KEYS.OS_NAME, System.getProperty("os.name"));
        /*add(KEYS.PLATFORM, getPlatform());
        add(KEYS.SUB_PLATFORM, getSubPlatform());
        add(KEYS.BROWSER, getBrowser());
        add(KEYS.DEVICE_ID, getDeviceID());
        add(KEYS.APPIUM_SERVER_STARTED, false);
        add(KEYS.TAG, getTag());*/
    }

    /*private String getTag() {
        String tag = System.getenv("tags");
        if (StringUtil.isStringEmpty(tag)) {
            logger.warn("Setting default tags as @foo since no tags were set externally");
            tag = "@foo";
        } else {
            return tag.trim();
        }
        return tag;
    }

    private String getDeviceID() {
        String deviceID = System.getenv("deviceId");
        if (StringUtil.isStringEmpty(deviceID)) {
            deviceID = "";
        } else {
            return deviceID;
        }
        return deviceID;
    }

    private String getPlatform() {
        String platform = System.getenv("platform");
        if (StringUtil.isStringEmpty(platform)) {
            logger.warn("Setting default platform as desktop since no platform were set externally");
            platform = ProjectConstants.PLATFORM_DESKTOP;
        } else {
            return platform.toLowerCase();
        }
        return platform;
    }

    private String getSubPlatform() {
        String subPlatform = System.getenv("subPlatform");
        if (StringUtil.isStringEmpty(subPlatform)) {
            subPlatform = "";
        } else {
            return subPlatform.toLowerCase();
        }
        return subPlatform;
    }

    private String getBrowser() {
        String browser = System.getenv("browser");
        if (StringUtil.isStringEmpty(browser)) {
            browser = ProjectConstants.CHROME;
        } else {
            return browser.toLowerCase();
        }
        return browser;
    }*/

    public void add(String key, Object value) {
        sessionState.put(key, value);
    }

    public void add(KEYS key, Object value) {
        add(key.name(), value);
    }

    public Object get(String key) {
        return sessionState.get(key);
    }

    public String getAsString(String key) {
        return sessionState.get(key).toString();
    }

    public WebDriver driver() {
        return (WebDriver) sessionState.get("DRIVER");
    }

    public void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
