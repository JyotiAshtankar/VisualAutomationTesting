package percy.selenium.integration;

import com.sun.net.httpserver.HttpServer;
import io.percy.selenium.Percy;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VisualTest {
    private static final String TEST_URL = "https://www.google.co.in/";
    private static ExecutorService serverExecutor;
    private static HttpServer server;
    private static WebDriver driver;
    private static Percy percy;

    private static int COUNTER = 1;

    @BeforeMethod
    public void launchBrowser() throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\002VMG744\\IdeaProjects\\example-percy-java-selenium\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        driver = new ChromeDriver(options);
       /*  driver=new ChromeDriver();
        driver.manage().window().maximize();*/


        //driver.manage().window().maximize();
        percy = new Percy(driver);
    }

    /*@Test(invocationCount = 5 )
    public void testVisual() {
        driver.get(TEST_URL);
        System.out.println(driver.getTitle());
        Random random = new Random();
// Generates random integers 0 to 49
        int x = random.nextInt(100);
        // Take a Percy snapshot.
        percy.snapshot("Test Parallel "+x);
    }*/
//, invocationCount = 5
    @Test(dataProvider = "readurls")
    private void testVisual(String url) throws InterruptedException {
        driver.get(url);
        Thread.sleep(3000);
        System.out.println(driver.getTitle());
        int x = COUNTER++;
        System.out.print("COUNTER VALUE - " + x);
        percy.snapshot("Test Parallel Target " + x);
    }

    @DataProvider(name = "readurls")
    private Object[][] getURLs() throws Exception {
        ArrayList<Object[]> testObjArray = new ArrayList<>();
        testObjArray.add(new Object[]{"https://ensure.com/"});
        testObjArray.add(new Object[]{"https://www.family.abbott/vn-vi/ensure/home.html"});
        testObjArray.add(new Object[]{"https://ensure.ca/en"});
        testObjArray.add(new Object[]{"https://www.pedialyte.abbott/mx/home.html"});
        testObjArray.add(new Object[]{"https://pediasure.com/"});
        return testObjArray.toArray(new Object[0][]);
    }

    @AfterMethod
    public void quitBrowser() {
        // Close our test browser.
        driver.quit();
    }


}
