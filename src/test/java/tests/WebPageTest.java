package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MainPage;
import pages.top_menu.ImagePage;
import pages.top_menu.NewsPage;
import pages.top_menu.VideoPage;
import pages.top_menu.WebPage;
import tests.retrytest.Retry;

import java.util.List;

public class WebPageTest extends BaseTest {
    @Test
    public void testSuggestEqualsSearchCriteria_WebSearch() {
        final String query = "ivanka";

        MainPage mainPage = openBaseURL();
        openBaseURL()
                .inputSearchCriteriaAndEnter(query)
                .waitUntilVisibilityWebResult()
                .clickSearchFieldHeader();
        mainPage
                .waitForSuggestToBeVisible();

        final List<String> actualSuggestion = mainPage.getAllElementsText();
        final int actualSizeSuggest = mainPage.countElementsInSuggestContainer();

        Assert.assertEquals(mainPage.getAllElementsText().size(), 5);

        for (String searchCriteria : actualSuggestion) {
            Assert.assertTrue(mainPage.suggestIsDisplayed());
            Assert.assertTrue(actualSizeSuggest > 0);
            Assert.assertTrue(searchCriteria.contains(query));
        }
    }

    @Test
    public void test404PageError_WebPage() {
        WebPage webPage = new WebPage(getDriver());

        final String expectedTitle404Error = "No results found for \"@#@$%^$^dasdsad1231\"";
        final String expectedFontSizeTitle404Error = "40px";
        openBaseURL()
                .inputSearchCriteriaAndEnter("rona")
                .waitUntilVisibilityWebResult()
                .clickHamburgerMenu()
                .clickRegionTopMenu()
                .clickRegionGerman()
                .searchAfterClearSearchField("@#@$%^$^dasdsad1231")
                .clickEnter();

        final String actualTitle404Error = webPage
                .waitUntilVisibilityErrorImage()
                .getTitleErrorText();
        final String actualFontSizeTitle404Error = webPage.getH2FontSize();

        Assert.assertEquals(actualTitle404Error, expectedTitle404Error);
        Assert.assertTrue(webPage.errorImageIsDisplayed());
        Assert.assertEquals(actualFontSizeTitle404Error, expectedFontSizeTitle404Error);
    }

    @Test
    public void testHoverTextsRelatedSearch_WebPage() throws InterruptedException {
        WebPage webPage = new WebPage(getDriver());
        openBaseURL()
                .inputSearchCriteriaAndEnter("ronaldo")
                .waitUntilVisibilityWebResult();

        final List<String> oldTextsColorsWhenHover = webPage.getTextColors();
        final List<String> newTextsColorsWhenHover = webPage.getTextsColorsWhenHover();

        Assert.assertNotEquals(newTextsColorsWhenHover, oldTextsColorsWhenHover);
    }

    @Test
    public void testRelatedSearchCriteria_WebPage() {
        WebPage webPage = new WebPage(getDriver());
        openBaseURL()
                .inputSearchCriteriaAndEnter("ronaldo")
                .waitUntilVisibilityWebResult()
                .clickHamburgerMenu()
                .clickRegionTopMenu()
                .clickRegionGerman()
                .waitForUrlContains("https://dev.swisscows.com/en/web?query=ronaldo&region=");

        final String actualRegion = webPage.getCurrentURL();
        final List<String> titleAllVideo = webPage.getTitleInRelatedSearches();

        Assert.assertEquals(actualRegion, "https://dev.swisscows.com/en/web?query=ronaldo&region=de-DE");
        for (String search : titleAllVideo) {
            Assert.assertTrue(search.toLowerCase().contains("ronaldo"));
        }
    }

    @Test
    public void testClickSearchCriteriaInRelatedSearch_WebPage() {
        WebPage webPage = new WebPage(getDriver());
        final List<String> oldSearchResult = openBaseURL()
                .inputSearchCriteriaAndEnter("ronaldo")
                .waitUntilVisibilityWebResult()
                .getTitleInWebResult();

        final List<String> newSearchResult = webPage
                .clickFirstTitleInRelatedSearches()
                .getTitleInWebResult();

        Assert.assertNotEquals(oldSearchResult, newSearchResult);
        Assert.assertEquals(webPage.getTitleInRelatedSearches().size(), 8);

    }

    @Test
    public void testSearchFieldDidYouMeanMessage_webPage() {

        final String expectedResult = "[Do you want results only for appple?]";
        final String actualResult = openBaseURL()
                .inputSearchCriteriaAndEnter("appple")
                .waitUntilVisibilityWebResult()
                .getTextDidYpuMeanMessage();


        Assert.assertNotEquals(expectedResult, actualResult);

    }

    @Test
    public void testWebResultsEqualsSearchCriteria() {
        WebPage webPage = new WebPage(getDriver());
        final List<String> titles = openBaseURL()
                .inputSearchCriteriaAndEnter("ronaldo")
                .waitUntilVisibilityWebResult()
                .getTitleInWebResult();

        final int actualSize = webPage.getTitleInWebResult().size();

        Assert.assertTrue(actualSize >= 8);
        for (String searchCriteria : titles) {
            Assert.assertTrue(searchCriteria.toLowerCase().contains("ronaldo"));
        }
        Assert.assertEquals(webPage.getTitle(),"ronaldo in Web search - Swisscows");

    }

    @Test
    public void testNextButtonAndPrevButtonVideoWidget_WebPage() {
        WebPage webPage = new WebPage(getDriver());
        openBaseURL()
                .inputSearchCriteriaAndEnter("ronaldo video")
                .waitUntilVisibilityWebResult()
                .clickHamburgerMenu()
                .clickRegionTopMenu()
                .clickRegionGerman()
                .waitForUrlContains("https://dev.swisscows.com/en/web?query=ronaldo+video&region=");
        webPage
                .clickNextButtonVideoWidget();
        Assert.assertTrue(webPage.lastImageInVideoWidgetIsDisplayed());
        webPage
                .clickPrevButtonVideoWidget();
        Assert.assertTrue(webPage.firstImageInVideoWidgetIsDisplayed());

    }

    @Test
    public void testClickMoreVideoButtonInVideoWidget_WebPage() {
        WebPage webPage = new WebPage(getDriver());
        openBaseURL()
                .inputSearchCriteriaAndEnter("ronaldo youtube")
                .waitUntilVisibilityWebResult()
                .clickHamburgerMenu()
                .clickRegionTopMenu()
                .clickRegionGerman()
                .waitForUrlContains("https://dev.swisscows.com/en/web?query=ronaldo+youtube&region=");
        webPage
                .clickMoreVideoInVideoWidget();
        Assert.assertTrue(getExternalPageURL().contains("https://dev.swisscows.com/en/video?query=ronaldo%20youtube&region=de-DE"));

    }

    @Test
    public void testOpenVideoInVideoWidget_WebPage() {
        WebPage webPage = new WebPage(getDriver());
        final String expectedTitle = "Your private and anonymous search engine Swisscows";
        openBaseURL()
                .inputSearchCriteriaAndEnter("ronaldo youtube")
                .waitUntilVisibilityWebResult()
                .clickHamburgerMenu()
                .clickRegionTopMenu()
                .clickRegionGerman()
                .waitForUrlContains("https://dev.swisscows.com/en/web?query=ronaldo+youtube&region=");
        webPage
                .clickFirstVideoInVideoWidget()
                .waitIUntilVisiblyVideoPlayer();

        Assert.assertTrue(webPage.getCurrentURL().contains("https://dev.swisscows.com/en/video/watch?query=ronaldo%20youtube&region=de-DE&id"));
        Assert.assertEquals(getExternalPageTitle(), expectedTitle);

    }

    @Test
    public void testClickMoreImageButtonInImageWidget_WebPage() {
        WebPage webPage = new WebPage(getDriver());

        final String expectedTitle = "ronaldo in Images search - Swisscows";
        openBaseURL()
                .inputSearchCriteriaAndEnter("ronaldo")
                .waitUntilVisibilityWebResult()
                .clickMoreImagesInVideoWidget()
                .waitForImageIsVisible();

        Assert.assertEquals(webPage.getCurrentURL(), "https://dev.swisscows.com/en/images?query=ronaldo");
        Assert.assertEquals(webPage.getTitle(), expectedTitle);

    }

    @Test
    public void testImagesAndTitleIsDysplaedInImageWidget_WebPage() {
        WebPage webPage = new WebPage(getDriver());
        final String expectedTitle = "Images for flover";

        final String actualTitle = openBaseURL()
                .inputSearchCriteriaAndEnter("flover")
                .waitUntilVisibilityWebResult()
                .waitForImageIsVisibleInImagesWidget()
                .getTittleImagesWidget();


        Assert.assertTrue(webPage.imagesInImageWidgetIsDisplayed());
        Assert.assertEquals(actualTitle, expectedTitle);

    }

    @Test
    public void testOpenImageInTheImageWidget_WebPage() {
        WebPage webPage = new WebPage(getDriver());

        final String oldUrl = openBaseURL()
                .inputSearchCriteriaAndEnter("ronaldo")
                .waitUntilVisibilityWebResult()
                .getCurrentURL();
        webPage
                .clickFirstImageInImageWidget()
                .switchToExternalPage();

        Assert.assertNotEquals(getExternalPageURL(), oldUrl);


    }

    @Test
    public void testOpenNewsInTheNewsWidget_WebPage() {
        WebPage webPage = new WebPage(getDriver());

        final String oldUrl = openBaseURL()
                .inputSearchCriteriaAndEnter("ronaldo")
                .waitUntilVisibilityWebResult()
                .getCurrentURL();
        webPage
                .clickFirstNewsInNewsWidget()
                .switchToExternalPage();

        Assert.assertNotEquals(getExternalPageURL(), oldUrl);

    }

    @Test
    public void testImagesAndTitleIsDysplaedInNewsWidget_WebPage() {
        WebPage webPage = new WebPage(getDriver());
        final String expectedTitle = "News for ronaldo";

        final String actualTitle = openBaseURL()
                .inputSearchCriteriaAndEnter("ronaldo")
                .waitUntilVisibilityWebResult()
                .waitForImageIsVisibleInNewsWidget()
                .getTittleNewsWidget();

        Assert.assertTrue(webPage.imagesInNewsWidgetIsDisplayed());
        Assert.assertEquals(actualTitle, expectedTitle);
    }
    @Test
    public void testAnyNumberInPaging_WebPage() {
        WebPage webPage = new WebPage(getDriver());
        openBaseURL()
                .inputSearchCriteriaAndEnter("ronaldo")
                .waitUntilVisibilityWebResult();
        final String oldTitle = webPage.getTitleH2Text();
        webPage
                .clickThirdPagePagination_WebPage()
                .waitForUrlContains("https://dev.swisscows.com/en/web?query=ronaldo&offset=20");

        final String newTitle = webPage.getTitleH2Text();
        final String actualAttribute = webPage
                .waitUntilVisibilityWebResult()
                .getAttributeThirdButtonPagination();

        Assert.assertNotEquals(oldTitle,newTitle);
        Assert.assertEquals(actualAttribute,"number active");

    }
    @Test
    public void testNextButtonInPaging_WebPage() {
        WebPage webPage = new WebPage(getDriver());
        openBaseURL()
                .inputSearchCriteriaAndEnter("ronaldo")
                .waitUntilVisibilityWebResult()
                .clickNextPagePagination_WebPage()
                .waitForUrlContains("https://dev.swisscows.com/en/web?query=ronaldo&offset=10");

        final String actualAttribute = webPage
                .waitUntilVisibilityWebResult()
                .getAttributeSecondButtonPagination();

        Assert.assertTrue(webPage.getTitleInWebResult().size() >= 8);
        Assert.assertEquals(webPage.getTitle(),"ronaldo in Web search - Swisscows");
        Assert.assertEquals(actualAttribute,"number active");

    }
    @Test
    public void testPreviousButtonInPaging_WebPage() {
        WebPage webPage = new WebPage(getDriver());
        openBaseURL()
                .inputSearchCriteriaAndEnter("ronaldo")
                .waitUntilVisibilityWebResult()
                .clickNextPagePagination_WebPage()
                .clickPreviousPagePagination_WebPage()
                .waitForUrlContains("https://dev.swisscows.com/en/web?query=ronaldo");

        final String oldTitle = webPage.getTitleH2Text();
        final String newTitle = webPage
                .waitUntilVisibilityWebResult()
                .getTitleH2Text();

        Assert.assertTrue(webPage.getTitleInWebResult().size() >= 8);
        Assert.assertEquals(webPage.getTitle(),"ronaldo in Web search - Swisscows");
        Assert.assertEquals(oldTitle,newTitle);

    }
    @Test
    public void testUsingFilter_WebPage() {
        WebPage webPage = new WebPage(getDriver());
        openBaseURL()
                .inputSearchCriteriaAndEnter("ronaldo")
                .waitUntilVisibilityWebResult()
                .clickFilterButton();
        webPage
                .clickButtonDateInFilter()
                .clickPastYearInDropDownOfFilter()
                .waitForUrlContains("https://dev.swisscows.com/en/web?query=ronaldo&freshness=Year");

        Assert.assertTrue(webPage.getCurrentURL().contains(("https://dev.swisscows.com/en/web?query=ronaldo&freshness=Year")));
        Assert.assertTrue(webPage.getTitleInWebResult().size() >= 8);
        Assert.assertEquals(webPage.getTitle(),"ronaldo in Web search - Swisscows");


    }
    @Test
    public void testCancelFilter_WebPage() {
        WebPage webPage = new WebPage(getDriver());

        openBaseURL()
                .inputSearchCriteriaAndEnter("ronaldo")
                .waitUntilVisibilityWebResult()
                .clickFilterButton();

        webPage
                .clickButtonDateInFilter()
                .clickPastYearInDropDownOfFilter()
                .waitForUrlContains("https://dev.swisscows.com/en/web?query=ronaldo&freshness=Year");
        webPage
                .clickFilterButton()
                .waitForUrlContains("https://dev.swisscows.com/en/web?query=ronaldo");

        Assert.assertTrue(webPage.getTitleInWebResult().size() >= 8);
        Assert.assertEquals(webPage.getCurrentURL(),"https://dev.swisscows.com/en/web?query=ronaldo");
    }
}