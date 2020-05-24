package com.automation.utilities;

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

    public Reporter() {

    }

    public static void main(String arg) {

        String reportsDir = arg;
        System.out.println(reportsDir);
        File reportOutputDirectory = new File(reportsDir);

        List<String> jsonReportFiles = getListOfJsonReports(reportOutputDirectory);

        if (jsonReportFiles.size() == 0) {
            throw new RuntimeException("ERROR - NO json reporter available to create HTML report");
        }

        try {
            Configuration configuration = createReportBuilderConfiguration(reportOutputDirectory);

            ReportBuilder reportBuilder = new ReportBuilder(jsonReportFiles, configuration);
            reportBuilder.generateReports();
            logger.info("\n\tHTML Reports are available here - " + reportsDir + "/cucumber-html-reports \n");
        } catch (Exception e) {
            logger.error("ERROR in creating consolidated reporter - " + e);
        }
    }

    private static Configuration createReportBuilderConfiguration(File reportOutputDirectory) {
        Configuration configuration = new Configuration(reportOutputDirectory, "Test Automation");
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
