package com.automation.amazon.tests;

import com.automation.amazon.Base;
import com.automation.amazon.LoggingTool;
import com.automation.amazon.pages.CheckoutScreen;
import com.automation.amazon.pages.HomeScreen;
import com.automation.amazon.pages.LoginScreen;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

public class Amazon_TestCase1 extends Base {

    LoginScreen loginPage = new LoginScreen();
    HomeScreen homePage= new HomeScreen();
    CheckoutScreen checkoutPage = new CheckoutScreen();




    @Test()
    public void test() throws Exception {
        LoggingTool.beginTest("Amazon_App_Automation");
        loginPage.login(propertiesFile.getProperty("emv.amazon_test.emailId"),propertiesFile.getProperty("emv.amazon_test.emailPassword"));
        loginPage.checkLoginSuccessfull();
        homePage.searchAnItem(propertiesFile.getProperty("emv.amazon_test.searchText"));
        HashMap productDetails = homePage.selectRandomItemFromSearchList();
        checkoutPage.navigateToCheckout();
        checkoutPage.processCheckout();
        Assert.assertTrue(productDetails.equals(checkoutPage.getProductDetails()),"There is a mismatch in description and amount of the product in search list and checkout screen");
        checkoutPage.removeItem();
        LoggingTool.logComment("Product Description & Price data in the search list and Checkout screen matches ");
        LoggingTool.endTest("Amazon_App_Automation");
    }





}
