package com.automation.utilities;


import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.security.*;
import java.util.Map;

public class RestAPIUtils {

    private static Logger logger = LogManager.getLogger(RestAPIUtils.class.getName());

    private RestAPIUtils() {

    }

    public static void setBaseURI(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    private static RestAssuredConfig sslConfig(String certType, String certPath, String password) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        KeyStore keyStore = null;
        SSLConfig config = null;
        try {
            keyStore = KeyStore.getInstance(certType);
            keyStore.load(
                    new FileInputStream(certPath),
                    password.toCharArray());

        } catch (Exception ex) {
            logger.error("Error while loading keystore >>>>>>>>>");
            ex.printStackTrace();
        }
        if (keyStore != null) {

            org.apache.http.conn.ssl.SSLSocketFactory clientAuthFactory = new org.apache.http.conn.ssl.SSLSocketFactory(keyStore, password);
            // set the config in rest assured
            config = new SSLConfig().with().sslSocketFactory(clientAuthFactory).and().allowAllHostnames();
            return RestAssured.config().sslConfig(config);
        } else {
            throw new RuntimeException("Certificates cannot be loaded");
        }

    }

    public static Response postRequestBySettingCertificate(String baseURL, String certType, String certPath, String password, Map<String, String> headers, String body, String path) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        setBaseURI(baseURL);
        RestAssured.config = sslConfig(certType, certPath, password);
        return RestAssured
                .given()
                .when()
                .headers(headers)
                .body(body)
                .post(path);
    }

    public static Response post(String baseURL, Map<String, String> headers, String body, String path) {
        setBaseURI(baseURL);
        return RestAssured
                .given()
                .when()
                .headers(headers)
                .body(body)
                .post(path);
    }


    public static String postRequestAndGetBodyAsString(String baseURL, Map<String, String> headers, String body, String path) {
        return post(baseURL, headers, body, path).getBody().asString();
    }

    public static Response get(String baseURL, Map<String, String> headers, String body, String path) {
        setBaseURI(baseURL);
        return RestAssured
                .given()
                .when()
                .headers(headers)
                .body(body)
                .get(path);
    }

    public static Response get(String baseURL, Map<String, String> headers, String path) {
        setBaseURI(baseURL);
        return RestAssured
                .given()
                .when()
                .headers(headers)
                .get(path);
    }

}
