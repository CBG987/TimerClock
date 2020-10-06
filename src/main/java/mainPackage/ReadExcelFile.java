/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;  
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author chris
 */
public class ReadExcelFile {
    public ArrayList<Map> readFile() throws IOException{
        ArrayList<Map> allPartisipants = new ArrayList();
        int count = 0;
        int cellCount = 0;
        
        FileInputStream fis=new FileInputStream(new File("C:\\Users\\chris\\Documents\\NetBeansProjects\\Deltakere.xls"));
        HSSFWorkbook wb=new HSSFWorkbook(fis);
        HSSFSheet sheet=wb.getSheetAt(0);  
        FormulaEvaluator formulaEvaluator=wb.getCreationHelper().createFormulaEvaluator(); 
        
        String name = "", adress = "", birthDate = "", startNumber = "", startTime = "";
        for(Row row: sheet){
            Map<String, String> personString = new HashMap<String, String>();
            for(Cell cell: row){
                if(count == 0){
                    if(cellCount == 0) name = cell.getStringCellValue();
                    if(cellCount == 1) adress = cell.getStringCellValue();
                    if(cellCount == 2) birthDate = cell.getStringCellValue();
                    if(cellCount == 3) startNumber = cell.getStringCellValue();
                    if(cellCount == 4) startTime = cell.getStringCellValue();
                    System.out.println("count: "+ count);
                }else{
                    if(cellCount == 0) personString.put(name, cell.getStringCellValue()+"");
                    if(cellCount == 1) personString.put(adress, cell.getStringCellValue()+"");
                    //if(cellCount == 2) personString.put(birthDate, cell.getNumericCellValue()+"");
                    if(cellCount == 2) personString.put(birthDate, convertDate(cell.getDateCellValue())+"");
                    if(cellCount == 3) personString.put(startNumber, cell.getNumericCellValue()+"");
                    if(cellCount == 4) personString.put(startTime, cell.getNumericCellValue()+"");
                    System.out.println("count: "+ count);
                } 
                cellCount++;
            }
            cellCount = 0;
            System.out.println();
            allPartisipants.add(personString);
            count += 1;
            
        }
        
        //LocalTime lm = LocalTime.of(00, 00, 00).plus(allPartisipants.get(1).get("Starttime").toString());
        //System.out.println(lm);
        return allPartisipants;
    }
    public String convertDate(Date birthdate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthdate);
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);
        return day+"-"+month+"-"+year;
    }
    public String convertTime(Double time){
        
        return "";
    }
}
