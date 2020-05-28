package com.automation.utilities;

import com.automation.helpers.KEYS;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Reporter {

    private static Logger logger = LogManager.getLogger(Reporter.class.getName());
    private static ThisRun thisRun = ThisRun.getInstance();

    public Reporter() {

    }

    public static void main(String jsonDirPath, String reportsDir) {

        System.out.println(reportsDir);
        File reportOutputDirectory = new File(jsonDirPath);

        List<String> jsonReportFiles = getListOfJsonReports(reportOutputDirectory);

        if (jsonReportFiles.size() == 0) {
            throw new RuntimeException("ERROR - NO json reporter available to create HTML report");
        }

        try {
            Configuration configuration = createReportBuilderConfiguration(new File(reportsDir));

            ReportBuilder reportBuilder = new ReportBuilder(jsonReportFiles, configuration);
            reportBuilder.generateReports();
            logger.info("\n\tHTML Reports are available here - " + reportsDir + "/cucumber-html-reports \n");
        } catch (Exception e) {
            logger.error("ERROR in creating consolidated reporter - " + e);
        }
    }

    private static Configuration createReportBuilderConfiguration(File reportOutputDirectory) {
        Configuration configuration = new Configuration(reportOutputDirectory, "Litmus Automation");

        configuration.addClassifications("Operating System", thisRun.getAsString(KEYS.OS_NAME.name()));
        configuration.addClassifications("Browser", thisRun.getAsString(KEYS.BROWSER.name()));
        configuration.addClassifications("Platform", thisRun.getAsString(KEYS.PLATFORM.name()));
        configuration.addClassifications("Sub Platform", thisRun.getAsString(KEYS.SUB_PLATFORM.name()));
        configuration.addClassifications("Device Id/Name", thisRun.getAsString(KEYS.DEVICE_ID.name()));
        return configuration;
    }

    private static List<String> getListOfJsonReports(File reportOutputDirectory) {
        List<File> jsonReportFiles =
                Arrays.asList(reportOutputDirectory.listFiles());

        List<String> files = jsonReportFiles.stream().map(file -> file.getAbsolutePath())
                .filter(file -> file.endsWith(".json"))
                .collect(Collectors.toList());

        return files;

    }
}
