package org.yujie.oditest.cssr;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;



import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.PDFTextStripperByArea;
import org.junit.Test;


public class CSTRtest {

	@Test
	public void test() {
				
		
		Login logintest = new Login();
	    logintest.execute();
	    
//	       Timestep selecttest = new Timestep();
//	      
	       
	    ExportReport pdf = new ExportReport();
	    pdf.execute();
	  
//		ReportFileCreate report = new ReportFileCreate();;
//		report.excelReportRead();
//		report.ExtractExcelContent();
//		report.pdfReportRead();

//	    report.ExtractPDFContent();
	    
 
	}

}
