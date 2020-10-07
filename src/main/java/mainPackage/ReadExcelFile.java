/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;  
import org.apache.poi.ss.usermodel.Row;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author chris
 */
public class ReadExcelFile {
    public ArrayList<Map> readFile() throws IOException{
        ArrayList<Map> allPartisipants = new ArrayList();
        int count = 0;
        int cellCount = 0;
        
        //FileInputStream fis=new FileInputStream(new File(System.getProperty("user.dir")+"\\Deltakere.xls"));
        FileInputStream fis=new FileInputStream(new File(chooseFile()));
        HSSFWorkbook wb=new HSSFWorkbook(fis);
        HSSFSheet sheet=wb.getSheetAt(0);  
        FormulaEvaluator formulaEvaluator=wb.getCreationHelper().createFormulaEvaluator(); 
        
        String name = "", startNumber = "", startTime = "";
        for(Row row: sheet){
            Map<String, String> personString = new HashMap<String, String>();
            for(Cell cell: row){
                if(count == 0){
                    if(cellCount == 0) name = cell.getStringCellValue();
                    if(cellCount == 1) startNumber = cell.getStringCellValue();
                    if(cellCount == 2) startTime = cell.getStringCellValue();
                    System.out.println("count: "+ count);
                }else{
                    if(cellCount == 0) personString.put(name, cell.getStringCellValue()+"");
                    if(cellCount == 1) personString.put(startNumber, cell.getNumericCellValue()+"");
                    if(cellCount == 2) personString.put(startTime, cell.getStringCellValue()+""); //System.out.println(cell.getStringCellValue()+"");
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
    public String chooseFile(){
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        String path = "";
        int returnValue = jfc.showOpenDialog(null);

	if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            path = selectedFile.getAbsolutePath();
            //System.out.println(selectedFile.getAbsolutePath());
	}
        return path;
    }
}
