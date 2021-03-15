package kitsoft;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class Test1 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testTask1() throws InterruptedException {
        driver.get("https://kyivcity.gov.ua/news/");
        driver.manage().window().maximize();

        //1.2. Проверить, что выбрана вкладка “Новини”
        wait.until(titleIs("Новини - Портал Києва"));
//        String news = driver.findElement(By.cssSelector(".breadcrumb__item.active")).getText();

        // 1.2. Проверить, что выбрана вкладка “Новини”
        assertEquals("Verification failed: Вкладака 'Новини' не выбрана", "Новини", driver.findElement(By.cssSelector(".breadcrumb__item.active")).getText());;

        // 1.3. Проверить, что выбрана тема “Усі”
        assertEquals("Verification failed: Topic 'ALL' not selected", "Усі", driver.findElement(By.cssSelector(".chosen-single span")).getText());

        //Проверить, что выбран период с 30.01.2006 по текущий день
        String right_old_date = "30.01.2006";
        String old_date = driver.findElement(By.id("dt1")).getAttribute("Value");
        assertEquals("Verification failed: Начальная дата выбрана не правильно", right_old_date, old_date);

        String today_date_ot_site = driver.findElement(By.id("dt2")).getAttribute("Value");
        SimpleDateFormat date_today = new SimpleDateFormat("dd.MM.yyyy");
        String right_today_date = date_today.format(new Date());
        assertEquals("Verification failed: Сегодняшняя дата выбрана не правильно", today_date_ot_site, right_today_date);
        TimeUnit.SECONDS.sleep(1);

    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
