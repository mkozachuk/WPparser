package com.mkozachuk.wpparser.DAO;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.mkozachuk.wpparser.Main;
import com.mkozachuk.wpparser.parsers.Parser;
import com.mkozachuk.wpparser.parsers.ParserErieaquariumsociety;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class Saver {
    Parser parser = new ParserErieaquariumsociety();

    List<Post> list;

    private HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        return style;
    }

    public void workbook(int nameCounter, String parseUrlsArg) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Posts");
        list = PostDAO.postList(parser.parseUrl(parser.parseUrls(parseUrlsArg)));

        int rownum = 0;
        Cell cell;
        Row row;
        //
        HSSFCellStyle style = createStyleForTitle(workbook);

        row = sheet.createRow(rownum);

        // EmpNo
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("postNum");
        cell.setCellStyle(style);
        // EmpName
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("title");
        cell.setCellStyle(style);
        // Salary
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("description");
        cell.setCellStyle(style);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("2 description");
        cell.setCellStyle(style);
        // Grade
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("metatags");
        cell.setCellStyle(style);

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("2 metatags");
        cell.setCellStyle(style);
        // Bonus
        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("text");
        cell.setCellStyle(style);

        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("2 text");
        cell.setCellStyle(style);

        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("3 text");
        cell.setCellStyle(style);

        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("4 text");
        cell.setCellStyle(style);

        cell = row.createCell(10, CellType.STRING);
        cell.setCellValue("imgLink");
        cell.setCellStyle(style);

        cell = row.createCell(11, CellType.STRING);
        cell.setCellValue("imgFromPosts");
        cell.setCellStyle(style);

        cell = row.createCell(12, CellType.STRING);
        cell.setCellValue("2 imgFromPosts");
        cell.setCellStyle(style);

        cell = row.createCell(13, CellType.STRING);
        cell.setCellValue("fromWhereLink");
        cell.setCellStyle(style);

        // Data
        for (Post post : list) {
            rownum++;
            row = sheet.createRow(rownum);

            // EmpNo (A)
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(post.getPostNo());
            // EmpName (B)
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(post.getTitle());
            // Salary (C)
            try {
                cell = row.createCell(2, CellType.NUMERIC);
                cell.setCellValue(post.getDescription());
            } catch (IllegalArgumentException e) {
                String[] partsOfLongString = tooLongString(post.getDescription());
                cell = row.createCell(2, CellType.NUMERIC);
                cell.setCellValue(partsOfLongString[0]);
                cell = row.createCell(3, CellType.NUMERIC);
                cell.setCellValue(partsOfLongString[1]);
            }
            // Grade (D)
            try {
                cell = row.createCell(4, CellType.NUMERIC);
                cell.setCellValue(post.getMetaTags());
            } catch (IllegalArgumentException e) {
                String[] partsOfLongString = tooLongString(post.getMetaTags());
                cell = row.createCell(4, CellType.NUMERIC);
                cell.setCellValue(partsOfLongString[0]);
                cell = row.createCell(5, CellType.NUMERIC);
                cell.setCellValue(partsOfLongString[1]);
            }

            try {
                cell = row.createCell(6, CellType.NUMERIC);
                cell.setCellValue(post.getText());
            } catch (IllegalArgumentException e) {
                String[] partsOfLongString = tooLongString(post.getText());
                cell = row.createCell(6, CellType.NUMERIC);
                cell.setCellValue(partsOfLongString[0]);
                cell = row.createCell(7, CellType.NUMERIC);
                cell.setCellValue(partsOfLongString[1]);
                cell = row.createCell(8, CellType.NUMERIC);
                cell.setCellValue(partsOfLongString[2]);
                cell = row.createCell(9, CellType.NUMERIC);
                cell.setCellValue(partsOfLongString[3]);
            }

            cell = row.createCell(10, CellType.NUMERIC);
            cell.setCellValue(post.getImgLink());

            try {
                cell = row.createCell(11, CellType.NUMERIC);
                cell.setCellValue(post.getAllImgs());
            } catch (IllegalArgumentException e) {
                String[] partsOfLongString = tooLongString(post.getAllImgs());
                cell = row.createCell(11, CellType.NUMERIC);
                cell.setCellValue(partsOfLongString[0]);
                cell = row.createCell(12, CellType.NUMERIC);
                cell.setCellValue(partsOfLongString[1]);
            }

            cell = row.createCell(13, CellType.NUMERIC);
            cell.setCellValue(post.getFromWhereUrl());

        }
        int random = (int) (Math.random() * 100);
        File file = new File(Saver.class.getResource("chromedriver").getPath() + nameCounter + "-" + random + Main.addTofileNameTag + ".xls");
        PostDAO.list.clear();
        PostDAO.list = new ArrayList<>();

        try {

            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);
            System.out.println("Created file: " + file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doParseAndSave() {

        List<String> categoryUrlsGivenByUser = new ArrayList<>();
        String categoryUrl = "";
        while (!(categoryUrl.equals("stop"))) {
            categoryUrl = Parser.urlForParsingUrls();
            categoryUrlsGivenByUser.add(categoryUrl);
            System.out.println("give me next link");
        }
        for (int i = 0; i < categoryUrlsGivenByUser.size(); i++) {
            workbook(i, categoryUrlsGivenByUser.get(i));
            list = new ArrayList<>();

        }
    }

    public String[] tooLongString(String longString) {
        int m = longString.length() / 2;
        String halve1;
        String halve2;
        String[] parts = new String[4];
        halve1 = longString.substring(0, m);
        halve2 = longString.substring(m);
        int n = halve1.length() / 2;
        int n1 = halve2.length() / 2;
        parts[0] = halve1.substring(0, n);
        parts[1] = halve1.substring(n);
        parts[2] = halve2.substring(0, n1);
        parts[3] = halve2.substring(n1);
        System.out.println("That was too long string");
        return parts;
    }

}

