package com.mkozachuk.wpparser.parsers;

import com.mkozachuk.wpparser.Driver;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserFunFishka implements Parser {
    static String link = "https://fanfishka.ru/akvariumnye-stati/akvaskeyp/2016-sekrety-filtracii-akvariuma-s-rasteniyami-travnika.html";
    private Document document;

    static Driver myDriver = new Driver(link);
    public static List<String> urls = new ArrayList<>();

    @Override
    public List<String> parseUrls(String url) {
        try {
            myDriver.driver.get(url);
        } catch (InvalidArgumentException e) {
            System.out.println("everything is done, know you should import all to WP, NOW!!!");
        }

        List<WebElement> postsUrl = myDriver.driver.findElements(By.className("title_news"));

        for (WebElement element : postsUrl) {
            urls.add(element.findElement(By.tagName("a")).getAttribute("href"));
            System.out.println(element.getText());
        }
        System.out.println(urls);


        return urls;
    }

    public String getTitle() {
        String title;
        try {
            WebElement postTitle = myDriver.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]"));
            title = postTitle.getText();
        } catch (NoSuchElementException e) {
            title = "Something gone wrong";
        }
        return title;
    }

    public int postId() {
        int id;
        id = getTitle().hashCode();
        return id;
    }

    public String getDescription() {
        String description = "";
        WebElement postDescription = myDriver.driver.findElement(By.xpath("/html/head/meta[2]"));
        description = postDescription.getAttribute("content");
        return description;
    }

    public String getMetatags() {
        String metaTags = "";
        WebElement postMeta = myDriver.driver.findElement(By.xpath("/html/head/meta[3]"));
        metaTags = postMeta.getAttribute("content");
        return metaTags;

    }

    public String getText() {
        String text = "";
        WebElement postText = myDriver.driver.findElement(By.className("fullstory"));
        List<WebElement> postTextElements;
        postTextElements = postText.findElements(By.tagName("p"));
        System.out.println(postTextElements.get(0).getText());
        for (int i = 0; i < postTextElements.size(); i++) {
            text = text + postTextElements.get(i).getText();
        }
        return text;
    }

    public String getAllImageFromPost() {
        String imgUrls = "";
        List<WebElement> urlList = myDriver.driver.findElements(By.className("highslide"));
        for (WebElement element : urlList) {
            imgUrls = imgUrls + "<img src=\"" + element.getAttribute("href") + "\">" + "\n";
        }
        System.out.println("All imgs from post " + imgUrls);
        return imgUrls;
    }


    @Override
    public String getPostImg() {
        String postImg = "";
        try {
            WebElement img = myDriver.driver.findElement(By.className("highslide"));
            postImg = img.getAttribute("href");
            System.out.println(postImg);
        } catch (NoSuchElementException e) {
            System.out.println("No such element");
            System.out.println(myDriver.driver.getCurrentUrl());
        }
        return postImg;
    }


    public static int getListOfPostUerlsSize() {
        int listSize = 0;
        listSize = urls.size();
        return listSize;
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
    public String getJsopText(String url) {
        try {
            document = Jsoup.connect(url).get();
            Elements element = document.getElementsByTag("span");
            return element.html();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOE");
            return "Smth gone wrong";
        }

    }

    @Override
    public String getPostLink() {
        String postUrl;
        postUrl = myDriver.driver.getCurrentUrl();
        return postUrl;
    }


}
