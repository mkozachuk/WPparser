package com.mkozachuk.wpparser.parsers;

import com.mkozachuk.wpparser.Driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public interface Parser {
    static String urlForParsingUrls() {
        String urlForParse = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Paste url to \" all posts page \"");
        try {
            urlForParse = reader.readLine();
        } catch (IOException e) {
            System.out.println("IOE");
        }
        return urlForParse;
    }

    List<String> parseUrls(String url);

    String getTitle();

    int postId();

    String getDescription();

    String getMetatags();

    String getText();

    String getJsopText(String url);

    String getPostImg();

    String[][] parseUrl(List<String> urlsList);

    String getPostLink();

    String getAllImageFromPost();
}
