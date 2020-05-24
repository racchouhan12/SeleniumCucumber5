package com.automation.utilities;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.StringJoiner;


/**
 * GeneralMathUtils contains random String generator method
 */
public class StringUtil {

    /**
     * This method generates a unique String (alphabets + numbers)
     **/
    public static String generateUniqueName() {
        int n = 20;

        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString = new String(array, Charset.forName("UTF-8"));

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();

        // Append first 20 alphanumeric characters from the generated random String into
        // the result
        for (int k = 0; k < randomString.length(); k++) {
            char ch = randomString.charAt(k);

            if (((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) && (n > 0)) {

                r.append(ch);
                n--;
            }
        }

        // return the resultant string
        return r.toString();
    }

    /**
     * This method generates a unique number set
     **/
    public static int generateRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(10000000);
    }

    /**
     * This method generates a unique String (alphabets)
     **/
    public static String generateRandomString() {
        int n = 20;

        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString = new String(array, Charset.forName("UTF-8"));

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();

        // Append first 20 alphanumeric characters from the generated random String into
        // the result
        for (int k = 0; k < randomString.length(); k++) {
            char ch = randomString.charAt(k);

            if (((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') && (n > 0))) {

                r.append(ch);
                n--;
            }
        }

        // return the resultant string
        return r.toString();
    }

    public static synchronized String getRandomAlphaStringInUpperCaseOfLen(int length) {
        return RandomStringUtils.randomAlphabetic(length).toUpperCase();
    }

    public static synchronized String getRandomAlphaStringInLowerCaseOfLen(int length) {
        return RandomStringUtils.randomAlphabetic(length).toLowerCase();
    }

    public static synchronized String getRandomAlphaStringOfLen(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static synchronized String getRandomAlphaNumStringOfLen(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String convertStringToTitleCase(String inputText) {
        StringBuffer stringBuffer = new StringBuffer(inputText.trim().toLowerCase());
        char firstChar = stringBuffer.charAt(0);
        return stringBuffer.replace(0, 1, Character.toString(firstChar).toUpperCase()).toString();

    }

    public static String convertSpaceSeparatedStringToTitleCase(String inputText) {
        String[] strArr = inputText.split("\\s+");
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : strArr) {
            stringBuffer.append(convertStringToTitleCase(str));
        }
        return stringBuffer.toString();
    }

    public static String convertSpaceSeparatedStringToTitleCaseWithSpaceInBetween(String inputText) {
        StringJoiner join = new StringJoiner(" ");
        String[] strArr = inputText.split("\\s+");
        for (String str : strArr) {
            join.add(convertStringToTitleCase(str));
        }
        return join.toString();
    }

    public static synchronized boolean isStringEmpty(String value) {
        return (StringUtils.isEmpty(value) || value.equals(""));
    }

    public static boolean checkForDuplicates(String values) {
        String[] splitValues = values.split(",");
        for (int i = 0; i < splitValues.length - 1; i++) {
            for (int j = i + 1; j < splitValues.length; j++) {
                if (splitValues[i].equalsIgnoreCase(splitValues[j]))
                    return true;
            }
        }
        return false;
    }

    public static synchronized Boolean isStringNumeric(String stringToBeVerified) {
        return StringUtils.isNumeric(stringToBeVerified);
    }
}
