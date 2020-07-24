package com.automation.utilities;

import com.automation.helpers.KEYS;
import com.automation.helpers.ProjectConstants;
import com.automation.scripts.Command;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class DriverUtils {

    private WebDriver driver;

    private ThisRun thisRun = ThisRun.getInstance();

    private static Logger logger = LogManager.getLogger(DriverUtils.class.getName());
    private String browser;
    private String platform;

    public DriverUtils(String platform, String browser) {
        this.platform = platform.toLowerCase();
        this.browser = browser.toLowerCase();
    }


    private WebDriver instantiateChromeDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(setWebChromeOptions());
        driver.get(thisRun.getAsString(KEYS.APP_URL.toString()));
        driver.manage().window().maximize();
        return driver;
    }


    private ChromeOptions setWebChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.setExperimentalOption("useAutomationExtension", false);
        if ("yes".equalsIgnoreCase(headless)) {
            options.addArguments("--headless");
            options.addArguments("--window-size=1920,1080");
        }
        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        //options.addArguments("--disable-ipv6");
        //options.addArguments("--whitelisted-ips=''");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("download.default_directory", thisRun.getAsString(KEYS.DOWNLOADS_PATH.name()));
        prefs.put("profile.default_content_setting_values.automatic_downloads", 1);
        prefs.put("profile.default_content_settings.popups", 0);
        options.setExperimentalOption("prefs", prefs);
        return options;
    }

    private WebDriver instantiateEdgeDriver() {
        //WebDriverManager.edgedriver().setup(); https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/
        driver = new EdgeDriver();
        driver.get(thisRun.getAsString(KEYS.APP_URL.toString()));
        driver.manage().window().maximize();
        return driver;
    }

    private WebDriver instantiateAndroidDriver() throws MalformedURLException {
        // appium --allow-insecure chromedriver_autodownload
        driver = new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), setCapabilitiesForAndroid());
        driver.get(thisRun.getAsString(KEYS.APP_URL.toString()));
        return driver;
    }

    private DesiredCapabilities setCapabilitiesForAndroid() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.UDID, thisRun.getAsString(KEYS.DEVICE_ID.toString())); //"emulator-5554"
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator"); //"emulator-5554"
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.0");
        //  capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
        capabilities.setCapability("nativeWebScreenshot", true);
        // capabilities.setCapability("chromedriverUseSystemExecutable", true);
        // capabilities.setCapability("autoGrantPermissions", true);

        return capabilities;
    }

    private WebDriver instantiateIE11Driver() {
        String cmd = "REG ADD \"HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\New Windows\" /F /V \"PopupMgr\" /T REG_SZ /D \"no\"";
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            System.out.println("Error ocured!");
        }
        System.setProperty("webdriver.ie.driver", thisRun.getAsString(KEYS.TEST_RESOURCES.name()) + "/IEDriverServer.exe");//32 bit ie11
        driver = new InternetExplorerDriver(ieCapabilities());
        driver.get(thisRun.getAsString(KEYS.APP_URL.toString()));
        driver.manage().window().maximize();
        return driver;
    }

    private InternetExplorerOptions ieCapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability("ignoreProtectedModeSettings", true);
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        InternetExplorerOptions options = new InternetExplorerOptions(capabilities);
        return options;
    }

    private WebDriver instantiateFireFoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver(setFirefoxOptions());
        driver.get(thisRun.getAsString(KEYS.APP_URL.toString()));
        driver.manage().window().maximize();
        return driver;
    }

    private FirefoxOptions setFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.dir", thisRun.getAsString(KEYS.DOWNLOADS_PATH.name()));
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        profile.setPreference("browser.download.useDownloadDir", true);
        profile.setPreference("browser.download.folderList", 2);
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        options.setAcceptInsecureCerts(true);
        if ("yes".equalsIgnoreCase(headless)) {
            options.addArguments("--headless");
        }
        options.setProfile(profile);
        capabilities.setCapability("marionette", true);
        return options.merge(capabilities);
    }

    private WebDriver instantiateRemoteWebDriver() {
        try {
            Command.execCommand("test");
            driver = new RemoteWebDriver(getBrowserCapabilities(getBrowserNamesForRemoteWebdriver()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.get(thisRun.getAsString(KEYS.APP_URL.toString()));
        driver.manage().window().fullscreen();
        return driver;
    }

    private String getBrowserNamesForRemoteWebdriver() {
        return thisRun.getAsString(KEYS.REMOTEWEBDRIVER_BROWSERS.toString());
    }

    private DesiredCapabilities getBrowserCapabilities(String browserType) {
        switch (browserType) {
            case "firefox":
                logger.info("Opening firefox driver in Node");
                return DesiredCapabilities.firefox();
            case "chrome":
                logger.info("Opening chrome driver in Node");
                return DesiredCapabilities.chrome();
            case "IE":
                logger.info("Opening IE driver in Node");
                return DesiredCapabilities.internetExplorer();
            default:
                throw new InvalidArgumentException("browser : " + browserType + " is invalid.");
        }
    }

    public WebDriver instantiateMobileEmulatorDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(setChromeOptions());
        driver.get(thisRun.getAsString(KEYS.APP_URL.toString()));
        return driver;
    }

    private List<String> addChromeArguments() {
        List<String> chromeArguments = new ArrayList<>();
        chromeArguments.add("--test-type");
        chromeArguments.add("--browser-test");
        chromeArguments.add("--disable-popup-blocking");
        chromeArguments.add("--disable-extensions");
        chromeArguments.add("--disable-infobars");
        chromeArguments.add("--disable-notifications");
        chromeArguments.add("--no-default-browser-check");
        chromeArguments.add("--allow-file-access");
        chromeArguments.add("--allow-file-access-from-files");
        chromeArguments.add("--allow-nacl-file-handle-api[2]");
        chromeArguments.add("--use-file-for-fake-audio-capture");
        chromeArguments.add("--allow-external-pages");
        chromeArguments.add("--enable-local-file-accesses");
        chromeArguments.add("--allow-external-pages");
        chromeArguments.add("--ash-enable-touch-view-testing");
        chromeArguments.add("--enable-touch-drag-drop");
        chromeArguments.add("--enable-touchview[7]");
        chromeArguments.add("--disable-extensions-file-access-check");
        return chromeArguments;
    }

    private ChromeOptions setChromeOptions() {
        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", thisRun.getAsString(KEYS.DEVICE_ID.name()));
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("mobileEmulation", mobileEmulation);
        options.addArguments(addChromeArguments());
        options.setAcceptInsecureCerts(true);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        return options;
    }


    public WebDriver getDriver() throws MalformedURLException {
        switch (platform) {
            case ProjectConstants.PLATFORM_DESKTOP:
                if (ProjectConstants.CHROME.equals(browser)) {
                    return instantiateChromeDriver();
                } else if (ProjectConstants.FIREFOX.equals(browser)) {
                    return instantiateFireFoxDriver();
                } else if (ProjectConstants.IE11.equals(browser)) {
                    return instantiateIE11Driver();
                } else if (ProjectConstants.EDGE.equals(browser)) {
                    return instantiateEdgeDriver();
                } else {
                    throw new InvalidArgumentException("Invalid browser name ");
                }
            case ProjectConstants.PLATFORM_MOBILITY:
                if (ProjectConstants.PLATFORM_ANDROID.equals(thisRun.getAsString(KEYS.SUB_PLATFORM.name())))
                    return instantiateAndroidDriver();
            case ProjectConstants.PLATFORM_EMULATOR:
                return instantiateMobileEmulatorDriver();
            case "remotewebdriver":
                return instantiateRemoteWebDriver();
            default:
                throw new InvalidArgumentException("Invalid browser type");
        }
    }


    public void quit(WebDriver driver) {
        driver.quit();
    }
}
