package com.automation.amazon.pages;

import com.automation.amazon.Base;
import com.automation.amazon.LoggingTool;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;

public class CheckoutScreen extends Base {


    private By favoriteButton = By.xpath("//android.widget.Image[@text=\"Heart to save an item to your default list\"]");
    private By addToCart_Button = By.xpath("//android.widget.Button[@resource-id=\"add-to-cart-button\"]");
    private By done_Button = By.xpath("//android.view.View[@text=\"DONE\"]");
    private MobileBy cart = (MobileBy) MobileBy.AccessibilityId("Cart");
    private By proceed_Button = By.xpath("//android.widget.Button[@text=\"Proceed to Buy\"]");
    private By continue_Button = By.xpath("//android.widget.Button[@text=\"Continue\"]");
    private By deliverToAddress_Button = By.xpath("//android.widget.Button[@resource-id=\"a-autoid-0-announce\"]");
    private By netBanking_RadioButton = By.xpath("//android.widget.RadioButton[@text=\"Net Banking\"]");
    private By netBanking_choseOption = By.xpath("//android.view.View[@text=\"Choose an Option\"]");
    private By bankDropDown = By.xpath("//android.view.View[@resource-id=\"a-popover-1\"]//following-sibling::android.view.View[3]");
    private By addGiftOption = By.xpath("//android.widget.Button[contains(@text,'Add gift')]|//android.widget.TextView[contains(@text,'Gift options not available.')]");
    private By orderNowTitle = By.xpath("//android.view.View[@text=\"Order now\"]");
    private By quantityDropDownOption = By.xpath("//android.view.View[@text=\"Quantity:\"]");
    private By deleteOptionDropDown = By.xpath("//android.view.View[contains(@text,'0 (Delete this item)')]");
    private By amazonBasketEmpty = By.xpath("//android.widget.TextView[@text = \"Your Amazon Basket is empty\"]");
    private By productPriceElement = By.xpath("//android.widget.TextView[contains(@text,'₹')][2]");
    private By productDescriptionElement = By.xpath("//android.widget.TextView[contains(@text,'₹')]//preceding-sibling::android.widget.TextView[1]");

    /**
     * Navigate to the initial checkout screen from product description page
     */
        public void navigateToCheckout() {
            LoggingTool.logStep("Navigating to the checkout screen");
            driverWait.until(ExpectedConditions.presenceOfElementLocated(favoriteButton));
            scrollToElementView(addToCart_Button);
            clickElement(addToCart_Button);
            if (isElementDisplayed(done_Button)) clickElement(done_Button);
            clickElement(cart);
        }


    /**
     * Completes the checkout process
     */

    public void processCheckout() throws InterruptedException {
        LoggingTool.logStep("Processing the checkout");
            clickElement(proceed_Button);
            Thread.sleep(500);
            clickElement(deliverToAddress_Button);
            clickElement(continue_Button);
            if(isElementDisplayed(continue_Button))clickElement(continue_Button);
            clickElement(netBanking_RadioButton);
            clickElement(netBanking_choseOption);
            clickElement(bankDropDown);
            clickElement(continue_Button);
            isElementDisplayed_WithWait(orderNowTitle);

        }


    /**
     * Removes all the items added to the cart
     */
    public void removeItem(){
            scrollToElementView(quantityDropDownOption);
            scrollDown();
            clickElement(quantityDropDownOption);
            clickElement(deleteOptionDropDown);
            if(!isElementDisplayed_WithWait(amazonBasketEmpty)){
                scrollDown();
                clickElement(quantityDropDownOption);
                clickElement(deleteOptionDropDown);
                isElementDisplayed_WithWait(amazonBasketEmpty);
            }

        }


    /**
     * Retrieves the product details as a HashMap from the checkout screen
     * @return HashMap contains product description and price
     */
    public HashMap getProductDetails(){
        scrollToElementView(addGiftOption);
        String priceCart = driverWait.until(ExpectedConditions.presenceOfElementLocated(productPriceElement)).getText().replace("₹ ","");
        String descriptionCart = driverWait.until(ExpectedConditions.presenceOfElementLocated(productDescriptionElement)).getText();
        HashMap productDetails = new HashMap();
        productDetails.put("description",descriptionCart);
        productDetails.put("price",priceCart);
        return productDetails;
    }







}
