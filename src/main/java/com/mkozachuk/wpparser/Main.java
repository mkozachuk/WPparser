package com.mkozachuk.wpparser;

import com.mkozachuk.wpparser.DAO.Saver;
import com.mkozachuk.wpparser.parsers.Parser;
import com.mkozachuk.wpparser.parsers.ParserErieaquariumsociety;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static String addTofileNameTag = "";

    public static void main(String[] args) {

        icoMaker();
        System.out.println("hi");
        System.out.println("print tag name for files");
        addToFileName();
        ParserErieaquariumsociety parser = new ParserErieaquariumsociety();
        Saver saver = new Saver();
        saver.doParseAndSave();
        parser.myDriver.driver.close();

    }

    public static void icoMaker() {
        JFrame frame = new JFrame();
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getClassLoader().getResource("images/3.ico")));
    }

    public static void addToFileName() {
        addTofileNameTag = Parser.urlForParsingUrls();
    }
}

