package com.nguyentuanminh.demo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testLoginSuccess() {

        driver.get("https://sinhvien1.tlu.edu.vn/#/login");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("username")));

        driver.findElement(By.id("username"))
                .sendKeys("2351067101");

        driver.findElement(By.id("password"))
                .sendKeys("068205009904");

        driver.findElement(
                By.cssSelector("button[data-ng-click='vm.login()']"))
                .click();

        wait.until(ExpectedConditions.not(
                ExpectedConditions.urlContains("/login")));

        String currentUrl = driver.getCurrentUrl();

        System.out.println("Current URL: " + currentUrl);

        Assert.assertFalse(
                currentUrl.contains("/login"),
                "Đăng nhập thành công phải chuyển sang trang khác");
    }

    @Test
    public void testLoginFailure() {

        driver.get("https://sinhvien1.tlu.edu.vn/#/login");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("username")));

        driver.findElement(By.id("username"))
                .sendKeys("2351067101");

        driver.findElement(By.id("password"))
                .sendKeys("123456789");

        driver.findElement(
                By.cssSelector("button[data-ng-click='vm.login()']"))
                .click();

        wait.until(ExpectedConditions.urlContains("/login"));

        String currentUrl = driver.getCurrentUrl();

        System.out.println("Current URL: " + currentUrl);

        Assert.assertTrue(
                currentUrl.contains("/login"),
                "Đăng nhập sai phải vẫn ở trang login");
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}