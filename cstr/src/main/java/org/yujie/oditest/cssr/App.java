package org.yujie.oditest.cssr;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	Login logintest = new Login();
	    logintest.execute();
	    
//	       Timestep selecttest = new Timestep();
//	      
	       
	    ExportReport pdf = new ExportReport();
	    pdf.execute();
	  
		ReportFileCreate report = new ReportFileCreate();
		report.cvsRerpotRead();
		report.ExtractCvsContent();
//		report.excelReportRead();
//		report.ExtractExcelContent();
//		report.pdfReportRead();

//	    report.ExtractPDFContent();
    }
}
