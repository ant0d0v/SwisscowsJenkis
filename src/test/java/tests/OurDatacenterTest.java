package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.MainPage;
import pages.TestData;
import pages.footer_menu.OurDatacenterPage;
import utils.TestUtils;

public class OurDatacenterTest extends BaseTest {

    @Test
    public void testHTML5VideoPlayerDatacenter() throws Exception {
        final String expectedSource = "https://dev.swisscows.com/video/SWISSCOWS.mp4";
        MainPage mainPage = openBaseURL();
        mainPage
                .scrollToFooterMenu()
                .clickOurDatacenterPageFooterMenu();
        OurDatacenterPage ourDatacenterPage = new OurDatacenterPage(getDriver());
        TestUtils.waitForPageLoaded(getDriver());
        final String source = ourDatacenterPage.getCurrentSrcOfVideo();
        ourDatacenterPage
                .playVideoDatacenter()
                .pauseVideo();
        ourDatacenterPage
                .screen("DatacenterVideo.png");
        Assert.assertEquals(source, expectedSource);
    }

    @Test(dataProvider = "OurDatacenterLinksData", dataProviderClass = TestData.class)
    public void testOurDatacenterLinksNavigateToCorrespondingPages(
            int index, String linkName, String href, String expectedURL) throws InterruptedException {

        MainPage mainPage = openBaseURL();
        mainPage
                .scrollToFooterMenu()
                .clickOurDatacenterPageFooterMenu();

        final String oldURL = mainPage.getCurrentURL();
        mainPage.scrollToFooterMenu();
        OurDatacenterPage ourDatacenterPage = new OurDatacenterPage(getDriver());
        ourDatacenterPage
                .scrollToWhereToH2Header()
                .clickAllLinks(index);

        final String actualURL = mainPage.getCurrentURL();

        Assert.assertNotEquals(oldURL, actualURL);
        Assert.assertEquals(actualURL, expectedURL);
    }

    @Test
    public void testOurDatacenterSlider() throws InterruptedException {

        MainPage mainPage = openBaseURL();
        mainPage
                .scrollToFooterMenu()
                .clickOurDatacenterPageFooterMenu();

        OurDatacenterPage ourDatacenterPage = new OurDatacenterPage(getDriver());
        TestUtils.waitForPageLoaded(getDriver());
        ourDatacenterPage
                .scrollToSlider();
        final String oldAttribute = ourDatacenterPage.getClassAttributeOfImageSlider();

        ourDatacenterPage
                .doubleClickToSecondImageInSlider();
        final String newAttribute = ourDatacenterPage.getClassAttributeOfImageSlider();

        Assert.assertNotEquals(newAttribute,oldAttribute);
        Assert.assertTrue(ourDatacenterPage.elementIsDisplayedInSlider());
    }
}
