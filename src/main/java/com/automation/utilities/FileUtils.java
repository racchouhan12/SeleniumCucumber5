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

    public static void deleteFileWithExtension(String dirName, String extensionWithDot) {
        File file = new File(dirName);
        //check first if supplied file name is a folder
        if (file.isDirectory()) {
            File[] listFile = file.listFiles((dir, name) -> name.toLowerCase().endsWith(extensionWithDot));
            for (File f : listFile) {
                System.out.println("Deleting " + f.getAbsolutePath());
                f.delete();
            }
        } else {
            //Flagging down that the filename specified is not a directory
            System.out.println("file:" + file.getAbsolutePath() + " is not a directory");
        }

    }

}
