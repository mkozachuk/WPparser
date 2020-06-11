package com.mkozachuk.wpparser.parsers;

import com.mkozachuk.wpparser.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ParserPhotoMonster implements Parser {
    public static String link = "https://photo-monster.ru/books/read/kak-izbejat-klishe-v-fotografii.html";
    public static Driver myDriver = new Driver(link);
    public static List<String> urls = new ArrayList<>();

    @Override
    public List<String> parseUrls(String url) {
        try {
            myDriver.driver.get(url);
        } catch (InvalidArgumentException e) {
            System.out.println("everything is done, know you should import all to WP, NOW!!!");
        }

        List<WebElement> postsUrl = myDriver.driver.findElements(By.className("read_more"));

        for (WebElement element : postsUrl) {
            urls.add(element.getAttribute("href"));
            System.out.println(element.getText());
        }
        System.out.println(urls);


        return urls;
    }

    @Override
    public String getTitle() {
        String title;
        try {
            WebElement postTitle = myDriver.driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/div[2]/div/h1"));
            title = postTitle.getText();
        } catch (NoSuchElementException e) {
            title = "Something gone wrong";
        }
        return title;
    }

    @Override
    public int postId() {
        int id;
        id = getTitle().hashCode();
        return id;
    }

    @Override
    public String getDescription() {
        String description = "";
        try {
            WebElement postDescription = myDriver.driver.findElement(By.xpath("/html/head/meta[5]"));
            description = postDescription.getAttribute("content");
        } catch (NoSuchElementException e) {
            try {
                WebElement postDescription = myDriver.driver.findElement(By.xpath("/html/head/meta[3]"));
                description = postDescription.getAttribute("content");
            } catch (NoSuchElementException e1) {
                description = "";
            }

        }
        return description;
    }

    @Override
    public String getMetatags() {
        String metaTags = "";
        WebElement postMeta = myDriver.driver.findElement(By.xpath("/html/head/meta[4]"));
        metaTags = postMeta.getAttribute("content");
        return metaTags;

    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public String getJsopText(String url) {
        return null;
    }

    @Override
    public String getPostImg() {
        return null;
    }

    @Override
    public String[][] parseUrl(List<String> urlsList) {
        return new String[0][];
    }

    @Override
    public String getPostLink() {
        return null;
    }

    @Override
    public String getAllImageFromPost() {
        return null;
    }

    public static int getListOfPostUerlsSize() {
        int listSize = 0;
        listSize = urls.size();
        return listSize;
    }
}
