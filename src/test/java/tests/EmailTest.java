package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MainPage;
import pages.TestData;
import pages.top_menu.EmailPage;

import java.util.List;

public class EmailTest extends BaseTest {
    @Test
    public void testH2TextsEmailPage() {
        final List<String> expectedH2Texts = List.of(
                "Advantages",
                "What we guarantee with Swisscows.email:",
                "Prices",
                "Install Swisscows.email"

        );
        final List<String> actualH2Texts = openBaseURL()
                .clickEmailTopMenu()
                .closeWindow()
                .switchToEmailPage()
                .getH2Texts();
        Assert.assertTrue(actualH2Texts.size() > 0);
        Assert.assertEquals(actualH2Texts, expectedH2Texts);
    }

    @Test
    public void testEmailLogoNavigatesToBaseURL() {
        final String expectedURL = "https://dev.swisscows.com/en";
        final String expectedTitle = "Your private and anonymous search engine Swisscows";

        MainPage mainPage = openBaseURL();
        final String actualURL = mainPage
                .clickEmailTopMenu()
                .closeWindow()
                .switchToEmailPage()
                .clickLogo()
                .getCurrentURL();

        final String actualTitle = mainPage.getTitle();

        Assert.assertEquals(actualURL, expectedURL);
        Assert.assertEquals(actualTitle, expectedTitle);
    }
    @Test(dataProvider = "EmailLinksData", dataProviderClass = TestData.class)
    public void testEmailLinksNavigateToCorrespondingPages(
            int index, String expectedH1text) {
        EmailPage emailPage = new EmailPage(getDriver());

        MainPage mainPage = openBaseURL();
        mainPage
                .clickEmailTopMenu()
                .closeWindow()
                .switchToEmailPage();

        final String oldURL = mainPage.getCurrentURL();
        final String oldH1Text = mainPage.getH1Text();
        emailPage
                .clickAllLinksOnEmailPage(index);

        final String actualURL = mainPage.getCurrentURL();
        final String actualH1Text = mainPage.getH1Text();

        Assert.assertNotEquals(oldURL, actualURL);
        Assert.assertNotEquals(oldH1Text, actualH1Text);
        Assert.assertTrue(actualURL.contains("https://accounts.swisscows.com/Account/Login?ReturnUrl"));
        Assert.assertEquals(actualH1Text, expectedH1text);
    }

    @Test
    public void testLinksColorsEmailPage() {
        List<String> expectedLinksColors = List.of(
                "rgba(255, 255, 255, 1)",
                "rgba(223, 93, 93, 1)",
                "rgba(223, 93, 93, 1)",
                "rgba(255, 255, 255, 1)"
        );
        List<String> actualLinksColors = openBaseURL()
                .clickEmailTopMenu()
                .closeWindow()
                .switchToEmailPage()
                .getColorLinks();

        Assert.assertTrue(actualLinksColors.size() > 0);
        Assert.assertEquals(actualLinksColors, expectedLinksColors);
    }

    @Test
    public void testTextsFontSizesEmailPage(){
        final List<String> expectedH1FontSizes = List.of(
                "40px",
                "40px",
                "40px",
                "40px"
        );
        final List<String>  actualH2FontSizes = openBaseURL()
                .clickEmailTopMenu()
                .closeWindow()
                .switchToEmailPage()
                .getH2FontSizes();

        Assert.assertTrue(actualH2FontSizes.size() > 0);
        Assert.assertEquals(actualH2FontSizes, expectedH1FontSizes);
    }
    @Test
    public void testStartForFreeLinkLinkNavigateToCorrespondingPage() {
        final String expectedUrl = "https://dev.swisscows.com/en";
        final String actualUrl = openBaseURL()
                .clickEmailTopMenu()
                .closeWindow()
                .switchToEmailPage()
                .clickStartForFreeLink()
                .getCurrentURL();
        final String actualTitle = new MainPage(getDriver()).getTitle();
        Assert.assertEquals(actualUrl, expectedUrl);
        Assert.assertTrue(actualTitle.contains("Your private and anonymous search engine Swisscows"));
    }

    @Test
    public void testInstallWebLinkNavigateToCorrespondingPage() {
        final String actualUrl = openBaseURL()
                .clickEmailTopMenu()
                .closeWindow()
                .switchToEmailPage()
                .scrollToWhereToInstallEmail()
                .clickInstallWebLink()
                .getCurrentURL();
        final String actualTitle = getExternalPageTitle();

        Assert.assertTrue(actualUrl.contains("https://accounts.swisscows.com/Account/Login?ReturnUrl"));
        Assert.assertTrue(actualTitle.contains("Sign in - Swisscows Account"));
    }


}
