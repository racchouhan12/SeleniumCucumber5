package com.automation.scripts;

import com.automation.helpers.KEYS;
import com.automation.utilities.ThisRun;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Command {

    private static Logger logger = LogManager.getLogger(Command.class.getName());
    private static Runtime runtime = Runtime.getRuntime();
    private static ThisRun thisRun = ThisRun.getInstance();
    private Command() {

    }

    public static void execCommand(String command) throws IOException, InterruptedException {
        String cmd = "java -jar "+thisRun.getAsString(KEYS.PROJECT_PATH.toString())
                + thisRun.getAsString(KEYS.STANDALONE_SERVER_PATH.toString()) + " -role hub -hubConfig " + thisRun.getAsString(KEYS.PROJECT_PATH.toString()) +"/hubConfig.json";
        logger.debug("Command generated------------------------------" +cmd);
        Process process = runtime.exec(cmd);
        process.waitFor();
        thisRun.waitFor(10);
        //logger.debug(process.getInputStream().);

        logger.debug(process.exitValue());
       // process.
    }

    public static void runShellCommand(String cmd, String message, int sleepFor) {
        try {
            logger.info(message + " - '" + (cmd) + "'");
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            thisRun.waitFor(sleepFor);
            logger.info("Done running command - '" + message + "' - " +(cmd) + "'");
        } catch (Exception e) {
            logger.error("Error running command: '" + message + "' - '" + (cmd) + "'\n" + e.getMessage());
        }
    }
}
