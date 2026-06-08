package com.swaglabs.utils;

import com.github.automatedowl.tools.AllureEnvironmentWriter;
import com.google.common.collect.ImmutableMap;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Path;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;
import static java.nio.file.Files.newInputStream;

public class AllureUtils {
    public static void cleanAllureResults() {

        FileUtils.deleteQuietly(new File("target/allure-results"));
    }

    public static void attachScreenshot(String screenshotName, String screenshotPath) {
        try {
            Allure.addAttachment(screenshotName, newInputStream(Path.of(screenshotPath)));
        } catch (Exception e) {
            System.err.println("Failed to attach screenshot: " + e.getMessage());
        }
    }


    public static void setAllureEnvironment() {
        AllureEnvironmentWriter.allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Application", "SauceDemo (Swag Labs)")
                        .put("Base URL", ConfigReader.get("base.url"))
                        .put("Browser", ConfigReader.get("browser"))
                        .put("Environment", "Production demo")
                        .put("Java Version", System.getProperty("java.version"))
                        .put("OS", System.getProperty("os.name"))
                        .build(),
                System.getProperty("user.dir") + "/target/allure-results/");
    }
}
