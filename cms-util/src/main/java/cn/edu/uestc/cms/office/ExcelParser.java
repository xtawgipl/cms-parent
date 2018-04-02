package cn.edu.uestc.cms.office;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * excel解析工具类
 *
 * @author zhangjj
 * @create 2017-08-23 10:53
 **/
public class ExcelParser {

    private static Logger logger = LoggerFactory.getLogger(ExcelParser.class);

    /**
     * read the Excel file
     * @param path the path of the Excel file
     * @return
     * @throws IOException
     */
    public static List<Map<String, String>> parser(String path, Map<String, Integer> params, Integer numSheet) throws IOException {
        if (path == null || Common.EMPTY.equals(path)) {
            return null;
        } else {
            String postfix = getPostfix(path);
            if (!Common.EMPTY.equals(postfix)) {
                if (Common.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                    return readXls(path, params, numSheet);
                } else if (Common.OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                    return readXlsx(path, params, numSheet);
                }
            } else {
                logger.info(path + Common.NOT_EXCEL_FILE);
            }
        }
        return null;
    }

    public static List<Map<String, String>> parser(InputStream is, Integer type, Map<String, Integer> params, Integer numSheet) throws IOException {
        if (type == 1) {//2003
            return readXls(is, params, numSheet);
        } else {//2007
            return readXlsx(is, params, numSheet);
        }
    }

    /**
     * Read the Excel 2010
     * @param path the path of the excel file
     * @return
     * @throws IOException
     */
    public static List<Map<String, String>> readXlsx(String path, Map<String, Integer> params, Integer numSheet) throws IOException {
        logger.info(Common.PROCESSING + path);
        InputStream is = new FileInputStream(path);
        return readXlsx(is, params, numSheet);
    }


    public static List<Map<String, String>> readXlsx(InputStream is, Map<String, Integer> params, Integer numSheet) throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        Map<String, String> map = null;
        List<Map<String, String>> list = new ArrayList<>();
        // Read the Sheet
        int len = numSheet == -1 ? xssfWorkbook.getNumberOfSheets() : numSheet + 1;
        numSheet = numSheet == -1 ? 0 : numSheet;
        for (; numSheet < len; numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            XSSFRow xssfRow1 = xssfSheet.getRow(0);
            for(int i = 0; i < xssfRow1.getPhysicalNumberOfCells(); ++i){
                System.out.println(i + " --> " + xssfRow1.getCell(i));
            }

            // Read the Row
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    map = new HashMap<>();
                    Set<Map.Entry<String, Integer>> entries = params.entrySet();
                    for(Map.Entry<String, Integer> entry : entries){
                        XSSFCell cell = xssfRow.getCell(entry.getValue());
                        if(cell != null){
                            map.put(entry.getKey(), cell.toString());
                        }
                    }
                    list.add(map);
                }
            }
        }
        return list;
    }

    /**
     * Read the Excel 2003-2007
     * @param path the path of the Excel
     * @return
     * @throws IOException
     */
    public static List<Map<String, String>> readXls(String path, Map<String, Integer> params, Integer numSheet) throws IOException {
        logger.info(Common.PROCESSING + path);
        InputStream is = new FileInputStream(path);
        return readXls(is, params, numSheet);
    }

    public static List<Map<String, String>> readXls(InputStream is, Map<String, Integer> params, Integer numSheet) throws IOException {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        Map<String, String> map = null;
        List<Map<String, String>> list = new ArrayList<>();
        // Read the Sheet
        int len = numSheet == -1 ? hssfWorkbook.getNumberOfSheets() : numSheet + 1;
        numSheet = numSheet == -1 ? 0 : numSheet;
        for (; numSheet < len; numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            HSSFRow hssfRow1 = hssfSheet.getRow(0);
            for(int i = 0; i < hssfRow1.getPhysicalNumberOfCells(); ++i){
                System.out.println(i + " --> " + hssfRow1.getCell(i));
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    map = new HashMap<>();
                    Set<Map.Entry<String, Integer>> entries = params.entrySet();
                    for(Map.Entry<String, Integer> entry : entries){
                        if(hssfRow.getCell(entry.getValue()) != null){
                            map.put(entry.getKey(), hssfRow.getCell(entry.getValue()).toString());
                        }
                    }
                    list.add(map);
                }
            }
        }
        return list;
    }


    private static String getPostfix(String path) {
        if (path == null || Common.EMPTY.equals(path.trim())) {
            return Common.EMPTY;
        }
        if (path.contains(Common.POINT)) {
            return path.substring(path.lastIndexOf(Common.POINT) + 1, path.length());
        }
        return Common.EMPTY;
    }

    public static void main(String[] args) throws IOException {
        Map<String, Integer> params = new HashMap<>();
        params.put("date", 0);//日期
        params.put("name", 6);//姓名
        params.put("policeNumber", 7);//民警警号
        params.put("mileage", 8);//里程
        params.put("lawAmount", 11);//纠违总数
        params.put("alarmAmount", 13);//接处警
        params.put("workingTime", 31);//工作时长

        List<Map<String, String>> list = ExcelParser.parser("D:\\project\\partyBuild\\party-build-portal\\src\\main\\resources\\data\\station\\6月份勤务平台小铁骑勤务数据统计.xlsx", params, 0);

    }
}
