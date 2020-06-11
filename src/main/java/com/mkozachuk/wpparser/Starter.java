package com.mkozachuk.wpparser;

import com.mkozachuk.wpparser.DAO.Saver;
import com.mkozachuk.wpparser.parsers.Parser;
import com.mkozachuk.wpparser.parsers.ParserBonsaiDoma;
import com.mkozachuk.wpparser.parsers.ParserFunFishka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Starter {
    String userText = "";
    int caseNumber = 0;
    public String parserType = "";
    Parser parser;

    void start() {
        System.out.println("Choose site for parse: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!userText.equals("0")) {
            System.out.println("1. FunFishka");
            System.out.println("2. BonsaiDoma");
            System.out.println("3. ");
            System.out.println("4. ");
            System.out.println("0. For exit from loop");
            try {
                userText = reader.readLine();
                caseNumber = Integer.parseInt(userText);
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод");
            } catch (IOException e1) {
                System.out.println("IOE");
            }

            switch (caseNumber) {
                case 1:
                    parserType = "FunFishka";
                    parser = new ParserFunFishka();
                    Saver saver = new Saver();
                    saver.doParseAndSave();
                    break;
                case 2:
                    parserType = "BonsaiDoma";
                    parser = new ParserBonsaiDoma();
                    break;
                case 3:
            }
            System.out.println("Loop is finished");
        }
    }

}
