package percy.selenium.integration;

import io.percy.selenium.Percy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class VisualTest {
    private static WebDriver driver;
    private static Percy percy;
    private static String USERNAME = "";
    private static String PASSWORD = "";
    private static int TEST_ID = 1;

    /*********************************************************************************************************
     * @function this method will launch browser
     * @description this function is used to create driver object and launch the Chrome browser. same driver
     *              reference is passed to create Percy reference.
     *
     *********************************************************************************************************/
    @BeforeMethod
    public void launchBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\002VMG744\\IdeaProjects\\VisualAutomationTesting\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        percy = new Percy(driver);
    }


    /**************************************************************************************************************************************************************
     * This the actual test we are going to perform, where percy's snapshot method is called to capture screenshot
     * @notes 1. if we want to target multiple urls/pages for visual testing depending on number of urls pass the same number for invocationCount
     * eg: If we want to check 5 pages/urls for Visual Testing  then test will be => @Test(dataProvider = "readurls", invocationCount = 5)
     *     and make sure only single url which is reference for Visual Testing need to pass from @DataProvider(name = "readurls")
     * 2. For second build remove invocationCount and execute the same script for those targeted 5 urls.
     *    pass needs to be validated 5 url from our @DataProvider(name = "readurls") defined below.
     ************************************************************************************************************************************************************/
    @Test(dataProvider = "readurls", invocationCount = 1)
    private void testVisual(String url) throws Exception {
        driver.get(url);
        Thread.sleep(5000);
        System.out.println(driver.getTitle());
        loginIntoAEM(url);
        percy.snapshot("Test " + TEST_ID++);
    }

    /***********************************************************************************************
     * this the data provider in TestNg. It is the way to parameter same script with multiple data.
     * With @DataProvider(name = "readurls") we can execute single percy build for multiple pages/urls.
     *
     ************************************************************************************************/
    @DataProvider(name = "readurls")
    private Object[][] getURLs() throws Exception {
        ArrayList<Object[]> testObjArray = new ArrayList<>();
        testObjArray.add(new Object[]{"https://ensure.com/"});
       /* testObjArray.add(new Object[]{"https://author-p14557-e52630.adobeaemcloud.com/content/qa-team/regression/group/en/components/jobopenings/test.html?wcmmode=disabled"});
        testObjArray.add(new Object[]{"https://author-p14557-e52630.adobeaemcloud.com/content/qa-team/regression/group/en/components/jobopenings/job-cdn-slide.html?wcmmode=disabled"});
        testObjArray.add(new Object[]{"https://author-p14557-e52630.adobeaemcloud.com/content/qa-team/regression/group/en/components/jobopenings/jo-author.html?wcmmode=disabled"});
        testObjArray.add(new Object[]{"https://author-p14557-e52630.adobeaemcloud.com/content/qa-team/regression/group/en/components/jobopenings/jo-cdn-grid.html?wcmmode=disabled"});
        testObjArray.add(new Object[]{"https://author-p14557-e52630.adobeaemcloud.com/content/qa-team/regression/group/en/components/jobopenings/jo-cdn-slider.html?wcmmode=disabled"});
       */
        return testObjArray.toArray(new Object[0][]);
    }

    public void loginIntoAEM(String url) throws Exception {
        if (url.contains("author-p14557-e52630")) {
            driver.findElement(By.cssSelector("._coral-Accordion-itemHeader coral-accordion-item-label")).click();
            driver.findElement(By.id("username")).sendKeys(USERNAME);
            driver.findElement(By.id("password")).sendKeys(PASSWORD);
            driver.findElement(By.id("submit-button")).click();
            Thread.sleep(3000);
        }
    }

    @AfterMethod
    public void quitBrowser() {
        // Close our test browser.
        driver.quit();
    }


}
