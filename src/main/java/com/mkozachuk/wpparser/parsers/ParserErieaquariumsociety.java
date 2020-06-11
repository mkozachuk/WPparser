package com.mkozachuk.wpparser.parsers;

import com.mkozachuk.wpparser.Driver;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ParserErieaquariumsociety implements Parser {

    static String link = "https://ru.erieaquariumsociety.com/aquarium/";
    private Document document;

    public static Driver myDriver = new Driver(link);
    public static List<String> urls = new ArrayList<>();

    @Override
    public List<String> parseUrls(String url) {
        myDriver.driver.get(url);

        System.out.println(myDriver.driver.getCurrentUrl());

        List<WebElement> postsUrl = myDriver.driver.findElements(By.className("post-content"));

        for (WebElement element : postsUrl) {
            urls.add(element.findElement(By.tagName("a")).getAttribute("href"));
            System.out.println(element.getText());
        }
        urls.remove(0);
        System.out.println(urls);
        return urls;
    }

    @Override
    public String getTitle() {
        String title;
        try {
            WebElement postTitle = myDriver.driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div/div[1]/article/div/div[1]/div[2]/h1"));
            title = postTitle.getText();
        } catch (NoSuchElementException e) {
            title = "Something gone wrong";
        }
        return title;
    }

    @Override
    public int postId() {
        return getTitle().hashCode();
    }

    @Override
    public String getDescription() {
        String description = "";
        WebElement postDescription = myDriver.driver.findElement(By.xpath("/html/head/meta[4]"));
        description = postDescription.getAttribute("content");
        return description;
    }

    @Override
    public String getMetatags() {
        return "null";
    }

    @Override
    public String getText() {
        String text = "";
        String endText = "";
        WebElement postText = myDriver.driver.findElement(By.className("entry"));
        text = postText.getAttribute("innerHTML");
        //

        text = text.replaceAll("\\{....*?\\}", "");
        text = text.replaceAll("\\<ins.*?\\</ins>", "");
        text = text.replaceAll("\\<script.*?\\</script>", "");
        text = text.replaceAll("\\<p><script.*?\\</p></script>", "");
        text = text.replaceAll("<script>", "");
        text = text.replaceAll("</script>", "");
        text = text.replaceAll("\\(adsbygoogle = window.adsbygoogle \\|\\| \\[]\\).push\\(\\{}\\);", "");
        text = text.replaceAll("\\<!-- Composite Start -->.*?\\<!-- Composite End -->>", "");
        endText = text.substring(text.indexOf("<!-- Composite Start -->"));
        text = text.replace(endText, "");
        text = text.replaceAll("<!-- 336-280 -->", "");
        text = text.replaceAll("</ins></ins>", "");
        text = text.replace("<div class=\"ag-box\">", "");
        System.out.println(text);
        return text;
    }

    @Override
    public String getJsopText(String url) {
        return "null";
    }

    @Override
    public String getPostImg() {
        return "null";
    }

    @Override
    public String[][] parseUrl(List<String> urlsList) {
        String currentUrl;
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
        return "";
    }

    @Override
    public String getAllImageFromPost() {
        return "";
    }

    public static int getListOfPostUerlsSize() {
        int listSize = 0;
        listSize = urls.size();
        return listSize;
    }
}
