package com.mkozachuk.wpparser.parsers;

import com.mkozachuk.wpparser.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ParserHabr implements Parser {
    public static String link = "https://habr.com/";
    static Driver myDriver = new Driver(link);
    public static List<String> urls = new ArrayList<>();

    @Override
    public List<String> parseUrls(String url) {
        myDriver.driver.get(url);

        List<WebElement> postsUrl = myDriver.driver.findElements(By.className("post__title_link"));

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
            WebElement postTitle = myDriver.driver.findElement(By.className("post__title-text"));
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
        WebElement postDescription = myDriver.driver.findElement(By.xpath("/html/head/meta[3]"));
        description = postDescription.getAttribute("content");
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
        String text = "";
        WebElement postText = myDriver.driver.findElement(By.id("post-content-body"));
        List<WebElement> postTextElements;
        postTextElements = postText.findElements(By.tagName("br"));
        System.out.println(postTextElements.get(0).getText());
        for (int i = 0; i < postTextElements.size(); i++) {
            text = text + "\n" + postTextElements.get(i).getText();
        }
        //text = postTextElements.toString();
        return text;
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
        String currentUrl = link;
        int count = 0;
        String[][] allPostsFromUrl = new String[urls.size()][8];
        for (String url : urlsList) {
            currentUrl = url;
            myDriver.driver.get(currentUrl);
            String[] post = new String[8];
            post[0] = postId() + "";
            post[1] = getTitle();
            post[2] = getDescription();
            post[3] = getMetatags();
            post[4] = getText();
            post[5] = getPostImg();
            post[6] = getAllImageFromPost();
            post[7] = getPostLink();
            allPostsFromUrl[count] = post;
            count++;

        }
        return allPostsFromUrl;
    }

    @Override
    public String getPostLink() {
        return null;
    }

    @Override
    public String getAllImageFromPost() {
        return null;
    }
}
