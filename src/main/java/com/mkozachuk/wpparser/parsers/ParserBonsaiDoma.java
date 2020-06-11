package com.mkozachuk.wpparser.parsers;

import com.mkozachuk.wpparser.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ParserBonsaiDoma implements Parser {
    public static String link = "https://bonsai-doma.ru/rukovodstvo/bonsai-podocarpus/";
    public static Driver myDriver = new Driver(link);
    public static List<String> urls = new ArrayList<>();

    @Override
    public List<String> parseUrls(String url) {
        try {
            myDriver.driver.get(url);
        } catch (InvalidArgumentException e) {
            System.out.println("everything is done, know you should import all to WP, NOW!!!");
        }

        List<WebElement> postsUrl = myDriver.driver.findElements(By.className("wrapper-grid"));

        for (WebElement element : postsUrl) {
            urls.add(element.findElement(By.tagName("a")).getAttribute("href"));
            System.out.println(element.getText());
        }
        System.out.println(urls);


        return urls;
    }

    @Override
    public String getTitle() {
        String title;
        try {
            WebElement postTitle = myDriver.driver.findElement(By.xpath("/html/body/div/section/div/div[2]/main/article/div[1]/div/div/div/header/div/h1"));
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
            WebElement postDescription = myDriver.driver.findElement(By.xpath("/html/head/meta[7]"));
            description = postDescription.getAttribute("content");
        } catch (NoSuchElementException e) {
            try {
                WebElement postDescription = myDriver.driver.findElement(By.xpath("/html/head/meta[4]"));
                description = postDescription.getAttribute("content");
            } catch (NoSuchElementException e1) {
                description = "";
            }

        }
        return description;
    }

    @Override
    public String getMetatags() {
        String metatags;
        metatags = "бонсай,искусство бонсай,искусство,bonsai,культура,цветы,растения,art,декоративные деревья,ниваки,садоводство,красота,природа,воронеж,сад,дача,лес,экология,растение,япония,хобби";
        return metatags;
    }

    @Override
    public String getText() {
        String text = "";
        System.out.println(myDriver.driver.getCurrentUrl());
        System.out.println(myDriver.driver.getPageSource());

        WebElement postText = myDriver.driver.findElement(By.tagName("article"));
        List<WebElement> postTextElements;
        postTextElements = postText.findElements(By.tagName("p"));
        System.out.println(postTextElements.get(0).getText());
        for (int i = 0; i < postTextElements.size(); i++) {
            text = text + "\n" + postTextElements.get(i).getText();
        }

        System.out.println(text);
        return text;
    }

    @Override
    public String getJsopText(String url) {
        return null;
    }

    @Override
    public String getPostImg() {
        String postImg = "";
        try {
            WebElement img = myDriver.driver.findElement(By.className("attachment-medium size-medium wp-post-image"));
            postImg = img.getAttribute("src");
            System.out.println(postImg);
        } catch (NoSuchElementException e) {
            System.out.println("No such element");
            System.out.println(myDriver.driver.getCurrentUrl());
        }
        return postImg;
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
        String postUrl;
        postUrl = myDriver.driver.getCurrentUrl();
        return postUrl;
    }

    @Override
    public String getAllImageFromPost() {
        String imgUrls = "";

        List<WebElement> urlList = myDriver.driver.findElements(By.xpath("/html/body/div/section/div/div[2]/main/article/div[2]/p"));

        for (WebElement el : urlList) {
            try {
                imgUrls = imgUrls + "<img src=\"" + el.findElement(By.tagName("img")).getAttribute("src") + "\">" + "\n";
            } catch (NoSuchElementException e) {
                System.out.println(" in this P no IMG");
            }
        }
        System.out.println(imgUrls);
        return imgUrls;
    }

    public static int getListOfPostUerlsSize() {
        int listSize = 0;
        listSize = urls.size();
        return listSize;
    }
}
