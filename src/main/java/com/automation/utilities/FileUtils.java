package com.automation.utilities;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class FileUtils {

    private static Logger logger = LogManager.getLogger(FileUtils.class.getName());

    public static boolean createFolder(String nameOfFolder) {
        File file = new File(nameOfFolder);
        if (!file.exists()) {
            if (file.mkdir()) {
                logger.debug("Directory is created: " + nameOfFolder);
                return true;
            } else {
                logger.error("Failed to create directory!");
                return false;
            }
        }
        return false;
    }

}
