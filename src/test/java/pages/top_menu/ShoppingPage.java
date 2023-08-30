package pages.top_menu;

import io.qase.api.annotation.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base_abstract.TopMenuPage;
import utils.ProjectConstants;

import java.util.List;

public class ShoppingPage extends TopMenuPage<ShoppingPage> {
    @FindBy(xpath = "//div[@class='shopping-results']")
    private WebElement shoppingResultContainer;
    @FindBy(xpath = "//h2[@class = 'title']")
    private WebElement h2Shopping;
    @FindBy(xpath = "//h2[@class = 'title']")
    private List<WebElement> h2TextShopping;
    @FindBy(xpath = "//div['shopping-results']//ul[@class]//li[3]")
    private WebElement thirdPagePagination;
    @FindBy(xpath = "//div['shopping-results']//ul[@class]//li[4]")
    private WebElement attributeThirdPagePagination;
    @FindBy(xpath = "//div['shopping-results']//ul[@class]//li[3]")
    private WebElement attributeSecondPagePagination;
    @FindBy(xpath = "//div['shopping-results']//ul[@class]//li[1]")
    private WebElement previousPagePagination;
    @FindBy(xpath = "//div['shopping-results']//ul[@class]//li[last()]")
    private WebElement nextPagePagination;
    public ShoppingPage(WebDriver driver) {
        super(driver);
    }
    public ShoppingPage createGeneric() {

        return new ShoppingPage(getDriver());
    }
    @Step("Wait until the news search results are visible")
    public ShoppingPage waitUntilVisibilityShoppingResult() {
        wait20ElementToBeVisible(shoppingResultContainer);

        return new ShoppingPage(getDriver());
    }
    public ShoppingPage selectBrazilRegion() {
        selectRegionBrazil();
        return new ShoppingPage(getDriver());
    }
    public ShoppingPage waitVisibilityErrorImage() {
        waitUntilVisibilityErrorImage();
        return new ShoppingPage(getDriver());
    }
    @Step("Get the title text of the shopping page")
    public String getTitleShopping()  {
        wait10ElementToBeVisible( h2Shopping);
        return getText( h2Shopping);
    }
    @Step("Get the title text of the shopping page")
    public List<String> getH2TextShoppingResult()  {
        return getTexts( h2TextShopping);
    }
    @Step("Click third number in pagination")
    public ShoppingPage clickThirdPagePagination() {
        click(thirdPagePagination);
        waitForUrlContains(ProjectConstants.DOMAIN + "/en/shopping?query=laptop&region=de-DE&offset=48");
        return new ShoppingPage(getDriver());
    }
    @Step("Click previous button in pagination")
    public ShoppingPage clickPreviousPagePagination() {
        click(previousPagePagination);
        waitForUrlContains(ProjectConstants.DOMAIN + "/en/shopping?query=laptop&region=de-DE");

        return new ShoppingPage(getDriver());
    }
    @Step("Click next button in pagination")
    public ShoppingPage clickNextPagePagination() {
        click20(nextPagePagination);
        waitForUrlContains(ProjectConstants.DOMAIN + "/en/shopping?query=laptop&region=de-DE&offset=24");
        return new ShoppingPage(getDriver());
    }
    @Step("Get attribute third number in the pagination")
    public String getAttributeThirdButtonPagination() {
        return getAttribute(attributeThirdPagePagination,"class");
    }
    @Step("Get attribute second number in the pagination")
    public String getAttributeSecondButtonPagination() {

        return getAttribute(attributeSecondPagePagination,"class");
    }
    @Step("Wait url to be changed")
    public ShoppingPage waitUntilUrlToBeChanged(String parametr){
        waitForUrlContains(ProjectConstants.DOMAIN +parametr);
        return new  ShoppingPage(getDriver());
    }
    public ShoppingPage waitUntilToBeInVisibleLoader(){
        waitForLoaderToBeInVisible();
        return new ShoppingPage(getDriver());
    }
}
