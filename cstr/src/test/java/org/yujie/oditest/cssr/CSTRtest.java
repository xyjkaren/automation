package org.yujie.oditest.cssr;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.SimpleFormatter;

import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.junit.Test;
import org.testng.annotations.IExpectedExceptionsAnnotation;

import com.thoughtworks.selenium.Selenium;

public class CSTRtest {

	@Test
	public void test() {
		
		Login logintest = new Login();
	       logintest.execute();
//	       Timestep selecttest = new Timestep();
	       
	       
//	       selecttest.ReportLayout();-
//	       GenerateHtml FinalRep = new GenerateHtml();
//	       FinalRep.execute();
//	      
	   ExportPdf pdf = new ExportPdf();
	   pdf.exportpdf();
	   
	   
	      
 
	}

}
