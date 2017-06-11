package com.create.pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class TestCreatePDF {
	 public static void main(String[] args) {
		 TestCreatePDF p001 = new TestCreatePDF();
		 
		    String filename = "D:\\P001.pdf";
		    p001.createPDF(filename);
		  }
		 
		  public void createPDF(String filename) {
		    // step 1
		    Document document = new Document(PageSize.A4);
		    // step 2
		    try {
		      PdfWriter.getInstance(document, new FileOutputStream(filename));
		       
		      document.addTitle("ID.NET");
		      document.addAuthor("dotuian"); 
		      document.addSubject("This is the subject of the PDF file."); 
		      document.addKeywords("This is the keyword of the PDF file.");
		       
		      // step 3
		      document.open();
		      // step 4
		      document.add(new Paragraph("Hello World!"));
		 
		       
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } catch (DocumentException e) {
		      e.printStackTrace();
		    } finally {
		      // step 5
		      document.close();
		    }
		  }
		 
}
