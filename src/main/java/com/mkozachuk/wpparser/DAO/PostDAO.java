package com.mkozachuk.wpparser.DAO;

import com.mkozachuk.wpparser.parsers.Parser;
import com.mkozachuk.wpparser.parsers.ParserErieaquariumsociety;

import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    public static List<Post> list = new ArrayList<>();

    public static List<Post> postList(String[][] allposts) {
        Parser parser = new ParserErieaquariumsociety();
        int urlsInPage = ParserErieaquariumsociety.getListOfPostUerlsSize();
        for (int i = 0; i < urlsInPage; i++) {
            int inPostCount = 0;
            list.add(new Post(Integer.parseInt(allposts[i][inPostCount]), allposts[i][inPostCount + 1], allposts[i][inPostCount + 2], allposts[i][inPostCount + 3], allposts[i][inPostCount + 4], allposts[i][inPostCount + 5], allposts[i][inPostCount + 6], allposts[i][inPostCount + 7]));
        }

        return list;


    }
}
