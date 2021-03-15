package com.automation.amazon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.log4testng.Logger;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Random;


public class Base {

    protected static AppiumDriver<WebElement> driver;
    protected static WebDriverWait driverWait;
    private static DesiredCapabilities capabilities;
    private static TouchAction<?> action = null;
    private int waitTime= 10;
    protected static FileReader reader= null;

    protected static  Properties propertiesFile=new Properties();


    /**
     * Initialise the appium driver to launch the app in the android device
     */


    @BeforeClass
    public void initialiseDriver() throws IOException {
        LoggingTool.logAction("Initializing the Android driver");
        initializePropertiesFile();
        capabilities = new DesiredCapabilities().android();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, propertiesFile.getProperty("env.amazon_test.deviceName"));
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,propertiesFile.getProperty("env.amazon_test.newCommandTimeOut"));
        File app = new File(propertiesFile.getProperty("env.amazon_test.apkPath"));
        String appPAthPathCapability = app.getAbsolutePath();
        capabilities.setCapability(MobileCapabilityType.APP,appPAthPathCapability);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,propertiesFile.getProperty("env.amazon_test.automationName"));
        capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY,propertiesFile.getProperty("env.amazon_test.androidWaitActivity"));
        capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_DURATION,"4000");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME,"");
        capabilities.setCapability(MobileCapabilityType.FULL_RESET,true);
        capabilities.setCapability(AndroidMobileCapabilityType.ANDROID_NATURAL_ORIENTATION,true);
        capabilities.setCapability("–session-override",true);
        try {
            driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
            LoggingTool.logAction("Driver initialized successfully, app is launched");
            System.out.println();
        } catch (MalformedURLException e) {
            System.out.println("Driver didn't start. The cause is :=================================>>>>>>>>>>>>>>>>>>>>>>>>>>>>.\n ");
            e.printStackTrace();
        }
        driverWait = new WebDriverWait(driver, waitTime);
        action=new TouchAction(driver);
    }



 private static void initializePropertiesFile() throws IOException {
     reader=new FileReader("./src/test/java/com/automation/amazon/resources/Config.properties");
     propertiesFile.load(reader);

 }


    /**
     * Quit app after executing all test scripts.
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDown() throws Exception {
        LoggingTool.logAction("Running the tear down method");
        driver.quit();
    }


    /**
     * Clicks on the element which is passed as the parameter
     * @param element
     */

    public void clickElement(By element){
        LoggingTool.logAction("Clicking on the element "+element.toString());
        try {
            driverWait.until(ExpectedConditions.presenceOfElementLocated(element)).click();
        }catch (Exception exception){
            LoggingTool.logError("Element not found: "+element.toString());
        }
    }


    /**
     * Enter the text in to the field which is passed as a parameter and before entering it clear the field
     * @param element text field into which the text has to be entered
     * @param text the text which is to be entered
     */

    public static void enterText(By element,String text){
        LoggingTool.logAction("Entering text:"+ text+ " in the text field");
        driverWait.until(ExpectedConditions.presenceOfElementLocated(element)).clear();
        driverWait.until(ExpectedConditions.presenceOfElementLocated(element)).sendKeys(text);

    }







    /**
     * Enter the text in to the field which is passed as a parameter and also mimics the enter key in the keyboard
     * @param element text field into which the text has to be entered
     * @param text the text which is to be entered
     */


    public static void enterTextFollowedByEnter(By element,String text) throws InterruptedException {
        LoggingTool.logAction("Entering text:"+ text+ " in the text field");
        driverWait.until(ExpectedConditions.presenceOfElementLocated(element)).clear();
        driverWait.until(ExpectedConditions.presenceOfElementLocated(element)).click();
        Thread.sleep(1000);
        driverWait.until(ExpectedConditions.presenceOfElementLocated(element)).sendKeys(text);
        Thread.sleep(1000);
        executeTerminalCommand("adb shell input keyevent 66");
    }


    /**
     * Perform scroll action on the screen to move down till the element is displayed or after 10 tries
     * @param element page is scrolled down till the element is displayed
     */

    public void scrollToElementView(By element) {
        LoggingTool.logAction("Scroll to element "+ element.toString());
        Boolean elementViewFlag = false;
        int maxCount = 10;
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.getHeight() * .90);
        int startX = (int) (size.getWidth() * .90);
        int endY = (int) ((size.getHeight()) * .30);
        PointOption startPoint = PointOption.point(startX, startY);
        PointOption endPoint = PointOption.point(startX, endY);
        while (maxCount > 0 && !elementViewFlag) {
            if (isElementDisplayed(element)) {
                elementViewFlag = true;
                break;
            }
            action.longPress(startPoint).moveTo(endPoint).release().perform();
            maxCount--;
        }
        if (!elementViewFlag)
            LoggingTool.logWarning("Element not found....");
    }


    public void scrollDown(){
        LoggingTool.logAction("scrolling down");
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.getHeight() * .90);
        int startX = (int) (size.getWidth() * .90);
        int endY = (int) ((size.getHeight()) * .30);
        PointOption startPoint = PointOption.point(startX, startY);
        PointOption endPoint = PointOption.point(startX, endY);
        action.longPress(startPoint).moveTo(endPoint).release().perform();
    }





    /**
     * * This method generates and returns a random number
     *
     * @return int
     */
    public static int generateARandomNumber(int min, int max) {
        Random generator = new Random();
        int randomNumber = generator.nextInt(max - min + 1) + min;
        return randomNumber;
    }


    /**
     * Waits for a certain number of time till the element is displayed
     * @param element
     * @return false if element is not displayed and true if element is displayed
     */

    public boolean isElementDisplayed_WithWait(By element){
        try {
            return driverWait.until(ExpectedConditions.presenceOfElementLocated(element)).isDisplayed();
        }catch (Exception e){
            return false;
        }
    }


    /**
     * Performs wait action for certain number of time till the element is displayed
     * @param element
     * @return
     */
    public boolean isElementDisplayed(By element) {
        try {
            return driver.findElement(element).isDisplayed();
        }catch (Exception e){
            return false;
        }






    }
    /**
     * Executes the terminal command and returns its output
     *@param terminalCommand the command which is to be ran in the terminal
     * @return String the output of the ran command
     */
    public static String executeTerminalCommand(String terminalCommand) {
        StringBuffer output = new StringBuffer();

        Process p;
        try {

            p = Runtime.getRuntime().exec(terminalCommand);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();
    }





}
