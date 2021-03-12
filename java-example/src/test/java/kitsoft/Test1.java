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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
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

    @Test
    public void testTask2() throws InterruptedException {
        //2.1. Перейти по ссылке https://kyivcity.gov.ua/news/
        driver.get("https://kyivcity.gov.ua/news/");
        driver.manage().window().maximize();

        //2.2. Выбрать период с 30.01.2020 по 10.02.2020
        driver.findElement(By.id("dt1")).click();
        driver.findElement(new By.ByCssSelector(".show-calendar .applyBtn.btn.btn-sm.btn-success ")).click(); //формируем ссылку с датами по умолчанию
        String url_with_date = driver.getCurrentUrl();
        SimpleDateFormat date_today = new SimpleDateFormat("dd.MM.yyyy");
        String right_today_date = date_today.format(new Date());
        String url_with_new_date = url_with_date.replace(right_today_date, "10.02.2020"); //заменяем в ссылке старую дату на требуемую

        String curl_with_new_date = url_with_new_date.replace("30.01.2006", "30.01.2020"); //сформировали ссылку с  периодом с 30.01.2006 по текущий день
        driver.get(curl_with_new_date); //загрузили страницу с новостями за период с 30.01.2006 по текущий день

        //2.3. Проверить, что в тайм-лайне отображаются даты в порядке убывания (начинается с “10 лютого 2020” и заканчивается на “31 січня 2020”)
        //Вот тут я окончательно загруз, правда отвлекся на третий тест, а вернувшись тут и погиб в неравном бою с java

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
        System.out.println(count); //количество найденных результатов

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
