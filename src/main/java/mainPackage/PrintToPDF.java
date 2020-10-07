/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPackage;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.stream.Stream;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author chris
 */
public class PrintToPDF {
    ArrayList<Participant> participants;
    
    public PrintToPDF(ArrayList<Participant> participants){
        this.participants = participants;
        
        try{
            Document document = new Document();
            //PdfWriter.getInstance(document, new FileOutputStream("iTextTable.pdf"));
            String fileName = "results.pdf";
            File directory = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"\\TimerResults");
            if(!directory.exists()) directory.mkdir();
            
            PdfWriter.getInstance(document, new FileOutputStream(directory+"\\"+fileName));
            //System.out.println(FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"\\TimerResults");
            document.open();
    
            PdfPTable table = new PdfPTable(5);
            addTableHeader(table);
            addRows(table);
            addCustomRows(table);
 
            document.add(table);
            document.close();
        }catch(FileNotFoundException fnfe){
        }catch(DocumentException | URISyntaxException | IOException de){
        }
        
    }
    //public static void main(String[] args){}
    
    private void addTableHeader(PdfPTable table) {
        Stream.of("Startnummer", "Navn", "Starttid", "Innkomsttid", "Sluttid")
            .forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });
    }
    private void addRows(PdfPTable table) {
        for(int i = 0; i < participants.size(); i++){
            Participant p = (Participant) participants.get(i);
            table.addCell(p.getStartnumber()+"");
            table.addCell(p.getName());
            table.addCell(p.getStartime());
            table.addCell(p.getEndtime());
            table.addCell(p.getTotaltime());
        }
    }
    private void addCustomRows(PdfPTable table) throws URISyntaxException, BadElementException, IOException {
        /*Path path = Paths.get(ClassLoader.getSystemResource("Java_logo.png").toURI());
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.scalePercent(10);
 
        PdfPCell imageCell = new PdfPCell(img);
        table.addCell(imageCell);*/
 
        /*PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
        horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(horizontalAlignCell);
 
        PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
        verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(verticalAlignCell);*/
    }
}
