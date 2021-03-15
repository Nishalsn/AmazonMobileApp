package com.automation.amazon.pages;

import com.automation.amazon.Base;
import com.automation.amazon.LoggingTool;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import java.util.HashMap;
import java.util.List;

public class HomeScreen extends Base {


    private By searchField = By.id("com.amazon.mShop.android.shopping:id/rs_search_src_text");
    private By searchList = By.id("com.amazon.mShop.android.shopping:id/rs_vertical_stack_view");
    private By descriptionId = By.id("com.amazon.mShop.android.shopping:id/list_product_description_layout");
    private By searchElementDescription =  By.id("com.amazon.mShop.android.shopping:id/item_title");
    private By searchElementPrice = By.id("com.amazon.mShop.android.shopping:id/rs_results_price");


    /**
     * Search for a item in the home screen
     * @param itemName Name of the item to be searched
     * @throws InterruptedException
     */
    public void searchAnItem(String itemName) throws InterruptedException {
        LoggingTool.logStep("Searching for the item: "+ itemName);
        enterTextFollowedByEnter(searchField,itemName);
        isElementDisplayed_WithWait(searchList);

    }


    /**
     * Select a random element from the search list and return its product description and price
     * @return
     * @throws Exception
     */

    public HashMap selectRandomItemFromSearchList() throws Exception{
        LoggingTool.logStep("Selecting a random item from the search list ");
        Thread.sleep(1000);
        List<MobileElement> searchElementList = driver.findElements(descriptionId);
        int randomNumber = generateARandomNumber(1, searchElementList.size()-1);
        MobileElement element=  searchElementList.get(randomNumber);
        String description= element.findElement(searchElementDescription).getText();
        String price= element.findElement(searchElementPrice).getAttribute("content-desc").split("₹")[1].replaceAll(" ","");
        searchElementList.get(randomNumber).click();
        HashMap hashMap = new HashMap();
        hashMap.put("description",description);
        hashMap.put("price",price);

        return hashMap;
    }
}
