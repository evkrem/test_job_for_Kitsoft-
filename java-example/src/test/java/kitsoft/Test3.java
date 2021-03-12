package kitsoft;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Formatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class Test3 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testTask() throws InterruptedException {
        driver.get("https://kyivcity.gov.ua/news/");
        driver.manage().window().maximize();
        driver.findElement(new By.ByCssSelector("div.row .slick-track a:nth-child(4)")).click();
        driver.findElement(new By.ById("title")).click();
        driver.findElement(new By.ById("title")).sendKeys("тест\n");
        WebElement element = driver.findElement(new By.ByClassName("timeline__list"));
        List<WebElement> list = element.findElements(By.tagName("li"));
        int count = list.size();
        Assert.assertTrue("Verification failed: результатов оказалось меньше 1, т.е. их нет", count>=1);
        System.out.println(count); //количество найденных результатов

        String text = "тест";

        for(int a=1; a<=count; a++)
        {
            Formatter title = new Formatter();
            title.format("#ultab7 li:nth-child(%s) div.timeline__item-inner-title", count);//подставляем в селектор номер найденной поиском статьи (если найдено больше 1)
            String path_title = title.toString();
            String titleText = driver.findElement(new By.ByCssSelector(path_title)).getText();
            titleText = titleText.toLowerCase();

            Formatter body = new Formatter();
            body.format("#ultab7 li:nth-child(%s) div.timeline__item-inner-text", count);//подставляем в селектор номер найденной поиском статьи (если найдено больше 1)
            String path_body = body.toString();
            String bodyText = driver.findElement(new By.ByCssSelector(path_body)).getText();

//            String bodyText = driver.findElement(new By.ByCssSelector("#ultab7 li:nth-child(1) div.timeline__item-inner-text")).getText();
            bodyText = bodyText.toLowerCase();
            Assert.assertTrue(bodyText.contains(text) | titleText.contains(text));
//            Assert.assertTrue("Text not found!", bodyText.contains(text));

        }
        TimeUnit.SECONDS.sleep(2);





//        assertEquals("Verification failed: Topic 'ALL' not selected", "Усі", driver.findElement(By.cssSelector(".chosen-single span")).getText());
//        String right_old_date = "30.01.2006";
//        String news = driver.findElement(By.cssSelector(".active.slick-slide")).getText();
//        System.out.println("news");
//        String tems = driver.findElement(By.cssSelector(".chosen-single span")).getText();
//        System.out.println(tems);
//        driver.findElement(By.name("q")).sendKeys("webdriver");


//        driver.findElement(By.name("btnK")).click();

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
