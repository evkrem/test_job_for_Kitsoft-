package kitsoft;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Test2 {

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
        driver.findElement(By.id("dt1")).click();
        driver.findElement(new By.ByCssSelector(".show-calendar .applyBtn.btn.btn-sm.btn-success ")).click();
        String url_with_date = driver.getCurrentUrl();

        SimpleDateFormat date_today = new SimpleDateFormat("dd.MM.yyyy");
        String right_today_date = date_today.format(new Date());
        String url_with_new_date = url_with_date.replace(right_today_date, "10.02.2020");

        String curl_with_new_date = url_with_new_date.replace("30.01.2006", "30.01.2020"); //сформировали ссылку с  периодом с 30.01.2006 по текущий день
        driver.get(curl_with_new_date); //загрузили страницу с новостями за период с 30.01.2006 по текущий день
        TimeUnit.SECONDS.sleep(5);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(new By.ByClassName(".timeline__item-time")));

//        List<WebElement> all_date_news = driver.findElements(new By.ByClassName(".timeline__item-time")).toString();
        String str = driver.findElement(new By.ByClassName("timeline__item-time")).getText();
        if (str.matches("(\\d+):(\\d+)")==true){

            String[] time_new = str.split(":"); // Parsing
            int hours = Integer.parseInt(time_new[0]);
            int minutes = Integer.parseInt(time_new[1]);
            System.out.println("Время размещения новости int: " + Integer.parseInt(time_new[0]) + ":" + Integer.parseInt(time_new[1]));
            System.out.println("hours minutes: " + hours + ":" + minutes);
        }


        List<WebElement> all_date_news = driver.findElements(new By.ByClassName("timeline__item-time"));
//        int i=0;
//        for(WebElement element:all_date_news){
//            System.out.println("Section "+i+":"+element.getText());
//            i++;
//        }

        System.out.println(all_date_news.get(0));
        System.out.println("Время размещения новости:" + str);

//        System.out.println(curl_with_new_date);

        TimeUnit.SECONDS.sleep(1);




//        2.3. Проверить, что в тайм-лайне отображаются даты в порядке убывания (начинается с “10 лютого 2020” и заканчивается на “31 січня 2020”)
//        2.4. Проверить, что за 10.02.2020 тайм-лайн по времени построен верно (в порядке убывания)
//        2.5. Проверить, что в couner отображается верное кол-во результатов (кол-во новостей на странице должно совпадать со значением в поле “Знайдено новин”)
//        2.6. Проверить, что в тайм-лайне нет дублей (под дублями подразумеваются одинаковые заголовки у новостей

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}


//Перейти к VCS > Import into Version Control > Create Git Repository
//Затем перейдите к VCS > Git > Remotes
//Нажмите кнопку + , оставьте origin as-is и вставьте Git URL вашего репозитория
//VCS > Git > Git Pull затем VCS > Git > Git Push