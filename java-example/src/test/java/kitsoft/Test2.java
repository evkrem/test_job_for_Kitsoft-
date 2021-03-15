package kitsoft;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.text.SimpleDateFormat;
import java.util.*;
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
        String curl_with_new_date = url_with_new_date.replace("30.01.2006", "30.01.2020"); //сформировали ссылку с  периодом с 30.01.2006 по 10.02.2020
        driver.get(curl_with_new_date); //загрузили страницу с новостями за период с 30.01.2006 по 10.02.2020

        //2.3. Проверить, что в тайм-лайне отображаются даты в порядке убывания (начинается с “10 лютого 2020” и заканчивается на “31 січня 2020”)
        while (driver.findElements(By.id("btnmore4")).size() != 0) //отображаем все найденные новости на странице
        {
            driver.findElement(By.id("btnmore4")).click();
            TimeUnit.SECONDS.sleep(1);
        }
        int total_news = driver.findElements(new By.ByClassName("timeline__item-date")).size();
        String a = "";
        int i = 0;
        while (i < total_news)
        {
            int day = 31, day_old = 0;
            String month = "";
            int month_int = 12, month_int_old = 0;
            i++;
            String selector_day = String.format("#ultab4 li:nth-child(%d) .timeline__item-date div:nth-child(%d)", i, 1);  //селектор для дня
            String selector_month = String.format("#ultab4 li:nth-child(%d) .timeline__item-date div:nth-child(%d)", i, 2); // селектор для месяца
            if (!driver.findElement(new By.ByCssSelector(selector_day)).getText().equals(a)) {
                day_old = Integer.parseInt(driver.findElement(new By.ByCssSelector(selector_day)).getText());
                Assert.assertTrue("Новость размещена после более новой",day_old<=day);
                day = day_old;
                month = driver.findElement(new By.ByCssSelector(selector_month)).getText();
                if (month.equals("лютого"))
                    month_int_old = 2;
                if (month.equals("січня"))
                    month_int_old = 1;
                Assert.assertTrue("Новость размещена после более новой",month_int_old<=month_int);
                month_int = month_int_old;
            }
            else
                continue;
        }

       // 2.4. Проверить, что за 10.02.2020 тайм-лайн по времени построен верно (в порядке убывания)
        String url_with_date_on_10_02 = driver.getCurrentUrl();
        url_with_date_on_10_02 = curl_with_new_date.replace("30.01.2020", "10.02.2020"); //сформировали ссылку с периодом з 10.02.2020
        driver.get(url_with_date_on_10_02); //загрузили страницу с новостями за период 10.02.2006
        while (driver.findElements(By.id("btnmore4")).size() != 0) //отображаем все найденные новости на странице
        {
            driver.findElement(By.id("btnmore4")).click();
            TimeUnit.SECONDS.sleep(1);
        }
        int count_news_for_10_02 = driver.findElements(new By.ByClassName("timeline__item-time")).size();
        int b = 0;
        while (b < count_news_for_10_02)
        {
            int hour = 24;
            int minute = 60;
            b++;
            String selector_time = String.format("#ultab4 li:nth-child(%d) .timeline__item-time", b);  //селектор для времени новости
            String time_news_temp = driver.findElement(new By.ByCssSelector(selector_time)).getText();
            String[] time_news = time_news_temp.split(":");
            int hour_old = Integer.parseInt(time_news[0]);
            int minute_old = Integer.parseInt(time_news[1]);
            Assert.assertTrue("Новость размещена после более новой",hour_old<=hour);
            if (hour_old < hour) {
                hour = hour_old;
                minute = minute_old;
            }
            else
                if(minute_old <= minute) {
                    hour = hour_old;
                    minute = minute_old;
                }
                else
                    Assert.assertTrue("Новость размещена после более новой",minute_old<=minute);
        }

        //2.5. Проверить, что в couner отображается верное кол-во результатов (кол-во новостей на странице должно совпадать со значением в поле “Знайдено новин”)
        driver.get(curl_with_new_date);  //загружаем выборку новостей с с 30.01.2006 по 10.02.2020
        while (driver.findElements(By.id("btnmore4")).size() != 0) //отображаем все найденные новости на странице
        {
            driver.findElement(By.id("btnmore4")).click();
            TimeUnit.SECONDS.sleep(1);
        }
        total_news = driver.findElements(new By.ByClassName("timeline__item-date")).size();  // количество новостей на странице
        String counter_string = driver.findElement(new By.ByClassName("count-materials")).getText();
        String [] counter = counter_string.split(":");
        System.out.println("text counter 1 =" + counter[1]);
        int counter_int = Integer.parseInt(counter[1].trim());
        Assert.assertTrue("Счетчик сломался",total_news == counter_int);
        TimeUnit.SECONDS.sleep(10);

        //2.6. Проверить, что в тайм-лайне нет дублей (под дублями подразумеваются одинаковые заголовки у новостей)
        int number_title = driver.findElements(new By.ByClassName("timeline__item-inner-title")).size();
        System.out.println("number_title = " + number_title);
        HashSet<String> title_newsHashSet = new HashSet<String>();
        for (int c = 0; c < number_title; c++)
        {
            title_newsHashSet.add(driver.findElement(new By.ByCssSelector(String.format("#ultab4 li:nth-child(%d) .timeline__item-inner-title a", c+1))).getText());
        }
//        for (String r : title_newsHashSet)
//            System.out.println("hash = " + r);
        Assert.assertTrue("В названиях новостей есть дубликаты", title_newsHashSet.size() == number_title); //проверяем наличие дубликатов - т.к. в HashSet одинаковые значения не добавляются,
                                                                                                                    // то при совпадении количества заголовком с количеством элементов HashSet дубликатов нет
    }



    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
