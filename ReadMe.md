
#  About Project

It is Selenium - Cucumber (BDD) project will be used to automate web application.
Project has Page Object model as its design pattern.


## Pre-requisites:

1. Java8 or higher should be installed.
2. Set JAVA_HOME
3. Install maven and set MAVEN_HOME
4. Add JAVA_HOME and MAVEN_HOME in your Path variable.

For Appium installation:

1. NodeJS should be installed (Version - 6.4.1 or higher) NodeJS installation guide
2. Install appium - npm install -g appium (I have used 1.13.0)
3. Install appium npm install -g appium-doctor [to check paths are setup properly]
4. Steps for Android sdk can be found in Android Libraries and SDK installation Guide
5. Add ANDROID_HOME and other required values in path variable using Step 4.
6. Windows CMD type appium-doctor to verify JAVA_HOME, ANDROID_HOME, adb , Bin dir for JAVA is set.
7. While executing test in Chrome if issues come for chromedriver version run this command
    appium --allow-insecure chromedriver_autodownload

## Framework structure:

1. Project is a maven project.
2. Our framework code lies mainly in src/test folder.
3. Project has three layer approach or calling:
    feature -> stepdefinitions -> businessflows -> screens

    1.      feature -> It is test/resources. It will contain all feature files (.feature).
    2.      stepdefinitions -> This folder has java files which contains step definitions corresponding in feature file.
    3.      businessflows -> This folder has java files which contains which contains business logic or assertions
    4.      screens -> This folder has java files which perform actual actions on the web browser.

4. In resources there are couple of more files like .exe, .properties.
5. In CommonProperties.properties we are passing browser name it will have more keys as framework expands.
6. We have utilities package which contains utilities for driver, reporting and maintaing session state of objects(ThisRun.java)
7. Call flows are as follows:
    1. run commands:
        a. mvn clean
        b. mvn install or mvn test
    2. Now execution will begin

        Call hierarchy are as follows:

        Runcuckes ->  Hooks:  -> features ->  stepdefinitions -> businessflows ->  screens

        ######  Note: ThisRun.java will be called in Hooks itself and driver intialization and other Keys are setup here

 ##  Rules to be followed:

 1. We should not call screens directly from stepdefinitions call hierarchy should be maintained (stepdefinitions -> businessflows -> screens).
 2. All assertions should be done in businessflows only
 3. Mutiple businessflows can be called from stepdefinitions

 ##  How to run Test:

 1. Maven command to run is mvn clean test -DsuiteXmlFile=[testngfilename.xml].
 2. There are three XMLs for desktop, mobile and emulator respectively in test/resources.
 3. Each XML has parameters inside it if platform, subPlatform, browser, deviceId, tags.
 4. For testngForWeb.xml we should change only tags, we can comment out the "test" that we dont want to execute.
 5. For testngForMobile we need to change only deviceId, subPlatform, tags, we can comment out the "test" that we dont want to execute.
 6. For testngForMobile we need to change only deviceId, tags, we can comment out the "test" that we dont want to execute.
 7. Right now for testngForMobile.xml, "data-provider-thread-count" at first line at should be 1.
