package com.automation.amazon.listners;


import com.automation.amazon.Base;
import com.automation.amazon.LoggingTool;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


public class TestListener extends Base implements ITestListener {

    @Override
    public void onTestFailure(ITestResult testResult) {
        LoggingTool.logWarning("Failed method");
        takeScreenshot("fail");
    }



    @Override
    public void onFinish(ITestContext arg0) {}

    @Override
    public void onStart(ITestContext arg0) {}

    @Override
    public void onTestSkipped(ITestResult arg0) {}

    @Override
    public void onTestStart(ITestResult arg0) {}

    @Override
    public void onTestSuccess(ITestResult arg0) {
        LoggingTool.logComment("Method passed");
        takeScreenshot("pass");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

}
