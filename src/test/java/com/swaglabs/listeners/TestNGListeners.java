package com.swaglabs.listeners;

import com.swaglabs.base.BaseTest;
import com.swaglabs.utils.AllureUtils;
import com.swaglabs.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.IExecutionListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;


public class TestNGListeners implements IInvokedMethodListener, IExecutionListener {

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod() && testResult.getStatus() == ITestResult.FAILURE) {
            Object instance = testResult.getInstance();
            if (instance instanceof BaseTest baseTest && baseTest.getDriver() != null) {
                ScreenshotUtils.takeScreenshot(
                        baseTest.getDriver(),
                        method.getTestMethod().getMethodName());
            }
        }
    }

    @Override
    public void onExecutionStart() {
        AllureUtils.cleanAllureResults();
        AllureUtils.setAllureEnvironment();
    }

}
