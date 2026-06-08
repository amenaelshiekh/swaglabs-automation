package com.swaglabs.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class ScreenshotUtils {
    public static void takeScreenshot(WebDriver driver, String name){
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File("target/screenshots/" + name + ".png");
            FileUtils.copyFile(src, dest);
            AllureUtils.attachScreenshot(name, dest.getPath());
        } catch (Exception e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
        }

    }
}
