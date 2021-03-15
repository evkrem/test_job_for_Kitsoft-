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



public class Test3 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testTask3() throws InterruptedException {

        // 3.1. Перейти по ссылке https://kyivcity.gov.ua/news/
        driver.get("https://kyivcity.gov.ua/news/");
        driver.manage().window().maximize();

        //3.2. Перейти на вкладку “Рішення та розпорядження”
        driver.findElement(new By.ByCssSelector("div.row .slick-track a:nth-child(4)")).click();
        driver.findElement(new By.ById("title")).click();

        //В поле “Назва” ввести “тест”
        driver.findElement(new By.ById("title")).sendKeys("тест\n");

        //3.4. Проверить, что найдено не менее 1 результата
        WebElement element = driver.findElement(new By.ByClassName("timeline__list"));
        List<WebElement> list = element.findElements(By.tagName("li"));
        int count = list.size();
        Assert.assertTrue("Verification failed: результатов оказалось меньше 1, т.е. их нет", count>=1);

        //3.5. Проверить, что результат корректен (содержит “тест”)
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
            bodyText = bodyText.toLowerCase();
            Assert.assertTrue(bodyText.contains(text) | titleText.contains(text)); //проверяем наличие текста "тест" в заголовке или теле статьи
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}