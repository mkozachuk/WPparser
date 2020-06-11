package com.mkozachuk.wpparser;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Driver {
    public WebDriver driver;
    private String driverPath = Driver.class.getResource("chromedriver").getPath();

    public Driver() {

    }

    public Driver(String href) {
        try {
            connect(href);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("InteruptE");
            driver.close();
        }
    }

    private void connect(String href) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", driverPath);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors");

        driver = new ChromeDriver(options);

        driver.get(href);
        Thread.sleep(500);  // Let the user actually see something!

        System.out.println("500ms later...");


    }

}
