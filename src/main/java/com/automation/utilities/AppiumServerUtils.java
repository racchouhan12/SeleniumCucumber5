package com.automation.utilities;

import com.automation.scripts.Command;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class AppiumServerUtils {

    private static Logger logger = LogManager.getLogger(AppiumServerUtils.class.getName());

    private AppiumServerUtils() {

    }
    public static void startServer() {
        logger.info("Starting appium server....");

        HashMap<String, String> osPaths = getOSSpecificNodeAndAppiumPaths(System.getProperty("os.name").toLowerCase());
        AppiumDriverLocalService service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File(osPaths.get("nodePath")))
                .withIPAddress("0.0.0.0")
                .usingPort(4723)
                .withArgument(GeneralServerFlag.LOG_LEVEL, "error:debug")
                .withAppiumJS(new File(osPaths.get("appiumPath")))
                .withStartUpTimeOut(1, TimeUnit.MINUTES));
        service.start();
        logger.info("Appium server started....");
    }

    public static HashMap<String, String> getOSSpecificNodeAndAppiumPaths(String osName) {
        HashMap<String, String> osPaths = new HashMap<>();
        if (osName.contains("mac")) {
            osPaths.put("nodePath", "/usr/local/bin/node");
            osPaths.put("appiumPath", "/usr/local/lib/node_modules/appium/build/lib/main.js");
        } else if (osName.contains("windows")) {
            osPaths.put("nodePath", "C:\\Program Files\\nodejs\\node.exe");
            osPaths.put("appiumPath", "C:\\Users\\p7110877\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js");
        }

        return osPaths;
    }

    public static void stopServer() {
        logger.info("Stopping appium server..");
        String launchApp = "taskkill /IM \"node.exe\" /F";

        Command.runShellCommand(launchApp, "Killing all node instance(appium server..)", 2);
        logger.info("All node instance killed..");
    }
}
