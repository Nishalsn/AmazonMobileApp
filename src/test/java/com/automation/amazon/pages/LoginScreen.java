package com.automation.amazon.pages;

import com.automation.amazon.Base;
import com.automation.amazon.LoggingTool;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.testng.Assert;


public class LoginScreen extends Base {


    private By loginScreen = By.id("com.amazon.mShop.android.shopping:id/sso_splash_logo");
    private By useDifferAccount_Button = By.id("com.amazon.mShop.android.shopping:id/sso_use_different_account");
    private By signIn_Button = By.id("com.amazon.mShop.android.shopping:id/sign_in_button");
    private By emailField = By.xpath("//android.widget.EditText[@resource-id=\"ap_email_login\"]");
    private By continue_Button = By.xpath("//android.widget.Button[@text=\"Continue\"]");
    private By passwordField = By.xpath("//android.widget.EditText[@resource-id=\"ap_password\"]");
    private By signInSubmit = By.xpath("//android.widget.Button[@resource-id=\"signInSubmit\"]");
    private MobileBy homeScreen = (MobileBy) MobileBy.AccessibilityId("Home");


    /**
     * Login to the Amazon app based on the credentials passed as the parameter
     * @param emailId
     * @param pwd
     */



    public void login(String emailId, String pwd){
        LoggingTool.logStep("Login to the application");
        isElementDisplayed_WithWait(loginScreen);
        if(isElementDisplayed(useDifferAccount_Button))clickElement(useDifferAccount_Button);
        if(isElementDisplayed(signIn_Button))clickElement(signIn_Button);
        enterText(emailField,emailId);
        clickElement(continue_Button);
        enterText(passwordField,pwd);
        clickElement(signInSubmit);
    }

    public void checkLoginSuccessfull(){
        LoggingTool.logStep("Checking login was successful");
        Assert.assertTrue(isElementDisplayed_WithWait(homeScreen),"Login was not successful check the credential entered ");

    }
}
