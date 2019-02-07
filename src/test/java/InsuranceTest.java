import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static java.lang.System.*;
import static junit.framework.TestCase.assertEquals;

public class InsuranceTest {
    WebDriver driver;
    String baseUrl;

    @Before
    public void testBefore() {
        setProperty("webdriver.chrome.driver", "drv/chromedriver_win32/chromedriver.exe");
        String baseUrl = "https://www.sberbank.ru/ru/person";
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        // Перейти на страницу
        driver.get(baseUrl);
    }

        @Test
        public void testInsurance () {
            // Нажать "Страхование"
            driver.findElement(By.xpath("//*[text()='Страхование'][@class='lg-menu__text']")).click();

            // Выбрать "Путешествия и покупки"
            driver.findElement(By.xpath("//*[text()='Путешествия и покупки'][@class='lg-menu__sub-link']")).click();

            // Проверить наличие на странице заголовка "Страхование путешественников"
            assertEquals("Страхование путешественников",driver.findElement(By.xpath("//*[text()='Страхование путешественников']")).getText());

            // Нажать на кнопку "Оформить онлайн"
            driver.findElement(By.xpath("//div[@data-pid='SBRF_ColList_sb_bundle-3523855']//a[@href='https://online.sberbankins.ru/store/vzr/index.html']")).click();

            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));

//            Wait <WebDriver> wait = new WebDriverWait(driver, 5, 1000);
//            WebElement until = wait.until(ExpectedConditions.visibilityOf(
//                    driver.findElement(By.xpath("//*[text()='Минимальная']/ancestor::div[1]"))));

            // На вкладке выбрать сумму страховой защиты "Минимальная"
            driver.findElement(By.xpath("//*[contains(@class,'b-form-prog-box-check-pos')]/parent::div/parent::div")).click();

            // Нажать на кнопку "Оформить"
            Wait <WebDriver> wait = new WebDriverWait(driver, 5, 1000);
            WebElement until = wait.until(ExpectedConditions.visibilityOf(
                    driver.findElement(By.xpath("//span[@class=\"b-button-block-center\"]//*[text()='Оформить']"))));

            driver.findElement(By.xpath("//span[@class=\"b-button-block-center\"]//*[text()='Оформить']")).click();

            // Ввести фамилию, имя и дату рождения застрахованных
            fillField(By.xpath("//*[@name='insured0_surname']"),"IVANOV");
            fillField(By.xpath("//*[@name='insured0_name']"),"IVAN");
            fillField(By.xpath("//*[@name='insured0_birthDate']"),"19.08.1990");

            // Ввести данные страхователя
            fillField(By.xpath("//*[@name='surname']"),"Иванов");
            fillField(By.xpath("//*[@name='name']"),"Иван");
            fillField(By.xpath("//*[@name='middlename']"),"Иванович");
            fillField(By.xpath("//*[@name='birthDate']"),"19.08.1992");
            driver.findElement(By.xpath("//*[@name='male']/parent::span")).click();

            fillField(By.xpath("//*[@name='passport_series']"),"1234");
            fillField(By.xpath("//*[@name='passport_number']"),"567891");
            fillField(By.xpath("//*[@name='issueDate']"),"01.12.2018");
            fillField(By.xpath("//*[@name='issuePlace']"),"ОВД Москвы");

            // Проверить, что все поля заполнены правильно
            assertEquals("IVANOV", driver.findElement(By.xpath("//*[@name='insured0_surname']")).getAttribute("value"));
            assertEquals("IVAN", driver.findElement(By.xpath("//*[@name='insured0_name']")).getAttribute("value"));
            assertEquals("19.08.1990", driver.findElement(By.xpath("//*[@name='insured0_birthDate']")).getAttribute("value"));
            assertEquals("Иванов", driver.findElement(By.xpath("//*[@name='surname']")).getAttribute("value"));
            assertEquals("Иван", driver.findElement(By.xpath("//*[@name='name']")).getAttribute("value"));
            assertEquals("Иванович", driver.findElement(By.xpath("//*[@name='middlename']")).getAttribute("value"));
            assertEquals("19.08.1992", driver.findElement(By.xpath("//*[@name='birthDate']")).getAttribute("value"));
            assertEquals("1234", driver.findElement(By.xpath("//*[@name='passport_series']")).getAttribute("value"));
            assertEquals("567891", driver.findElement(By.xpath("//*[@name='passport_number']")).getAttribute("value"));
            assertEquals("01.12.2018", driver.findElement(By.xpath("//*[@name='issueDate']")).getAttribute("value"));
            assertEquals("ОВД Москвы", driver.findElement(By.xpath("//*[@name='issuePlace']")).getAttribute("value"));

            // Нажать на кнопку "Продолжить"
            driver.findElement(By.xpath("//*[text()='Продолжить']")).click();

            // Проверить наличие на странице "Заполнены не все обязательные поля"
            assertEquals("Заполнены не все обязательные поля",driver.findElement(By.xpath("//*[text()='Заполнены не все обязательные поля']")).getText());
        }

        @After
        public void testAfter () {
            driver.quit();
        }

        private void fillField(By locator, String value){
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(value);
        }
}