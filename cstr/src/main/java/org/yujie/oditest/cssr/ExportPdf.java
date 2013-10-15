package org.yujie.oditest.cssr;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.Selenium;


public class ExportPdf extends Mainpage {

	private List<String> operations;
	private Selenium selenium;
	public ExportPdf ()
	{
		gotomainpage();
		TrendReport();
		
		String[] steps = {"Open","Save","Cancel"};
		operations = Arrays.asList(steps);
	}
			
	
	public void exportpdf()
	{
		  JavascriptExecutor js=(JavascriptExecutor) driver;  
		submit.click();
		try{
		
		String mainWinhandler = driver.getWindowHandle();
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
		
		
		
		driver.switchTo().window(mainWinhandler);
		
		WebElement export = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.id("iExport")));
		export.click();
		
		//autoitThread tt = new autoitThread();
	//	tt.start();
		WebElement pdf = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.linkText("Export to PDF")));
		//pdf.click();
		
		
		
//		try{
//		Object response = ((JavascriptExecutor) driver).executeScript(" var exportFormat = 'pdf';var xhr = null;" +
//			      "if (window.XMLHttpRequest) {xhr = new XMLHttpRequest();}" +
//			       "else if (window.ActiveXObject) {xhr = new ActiveXObject(\"Microsoft.XMLHTTP\"); }" +
//			       "xhr.open('POST', 'https://sun-qa-ncp03clone.engca.bevocal.com:8443/np/odiAdvancedReporting/TestSSOSession.jsp', true);" +			   
//			 //      "xhr.open('GET','/',true);" +
//					"xhr.onreadystatechange = function() {" +				
//					"if (xhr.readyState == 4) {if (xhr.status == 200) {" +
//					"var response = xhr.responseText;" +
//					"if(response == 'TestSSOSession') {" +
//					"var currentTime = new Date;" +
//					"var yyyy = currentTime.getFullYear().toString(10);" +
//					"var mm = formatNumber(currentTime.getMonth()+1);" +
//					"var dd = formatNumber(currentTime.getDate());" +
//					"var HH = formatNumber(currentTime.getHours());" +
//					"var MM = formatNumber(currentTime.getMinutes());" +
//					"var SS = formatNumber(currentTime.getSeconds());" +
//					"var exportTime = yyyy + mm + dd + HH + MM + SS;" +
//					"if (exportFormat == 'pdf') {" +
//		//	       "var frame = document.getElementById('reportContent');" +
//					"	 window.open('ExportPDF.jsp?rand=IWXY-2IE5&exportTime='+exportTime,'_blank');} " +
//					"else if (exportFormat == 'excel') {" +
//					"	window.location = 'ExportExcel.jsp?rand=SNCI-2ICG&exportTime='+exportTime;}" +
//					"else if (exportFormat == 'csv') {" +
//					"	window.location = 'ExportCSV.jsp?rand=SNCI-2ICG&exportTime='+exportTime; }" +
//					"else if(exportFormat == 'cr') {" +
//		            "    window.location = 'ExportCrystalReport.jsp?rand=SNCI-2ICG&exportTime='+exportTime; } }" +
//					"else {window.location = 'http://www.yahoo.com';} }" +
//					"else {window.location = 'http://www.sina.com.cn';}}};" +
//				//	"window.location='/np/welcome/index.jsp'};" +    // typo here
//			       "xhr.send(null);" +
//	//		       "frame.contentWindow.src = (arguments[0]);" +
//			       "","pdf");
//		System.out.println(response);
//		
//		}catch(WebDriverException e)
//		{
//			e.getClass();driver.quit();
//		}
//		
		Thread.sleep(5000);
		logger.info("Test case: Export to PDF open"); 
	//	driver.quit();
//		WebElement pdf = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.linkText("Export to PDF")));
//		pdf.click();
		
//		FileDownloader downloader = new FileDownloader();
//	    SimpleDateFormat datefor1 = new SimpleDateFormat("yyyy");
//	    SimpleDateFormat datefor2 = new SimpleDateFormat("MM");
//	    SimpleDateFormat datefor3 = new SimpleDateFormat("dd");
//	    SimpleDateFormat datefor4 = new SimpleDateFormat("HH");
//	    SimpleDateFormat datefor5 = new SimpleDateFormat("mm");
//	    SimpleDateFormat datefor6 = new SimpleDateFormat("ss");
//
//	    Date now = new Date();
//	    String date = datefor1.format(now)+datefor2.format(now)+datefor3.format(now)+datefor4.format(now)+datefor5.format(now)+datefor6.format(now);
//	    
//	    String url = "https://sun-qa-ncp03clone.engca.bevocal.com:8443/np/odiAdvancedReporting/ExportPDF.jsp";
//	  // System.out.print(url);
//	    try {
//			downloader.downloader(url);
//		} catch (NullPointerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		//Thread.sleep(2000);
//		String[] dialog =  new String[]{ "Save_Dialog_IE.exe","Download","Cancel"}; 
//		Runtime.getRuntime().exec(dialog);
		
		//gotomainpage();
//driver.switchTo().frame(driver.findElement(By.name("reportContent")));
		//WebElement export  = new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.className("filterbutton")));
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	private class autoitThread extends Thread {
		public autoitThread() {
			// TOO Auto-generated constructor stub
		}
		
		public synchronized void run() {
			try {
				
				Thread.sleep(5000);logger.info("after sleep");			
				String[] dialog =  new String[]{ "Save_Dialog_IE.exe","Download","Cancel"};logger.info("after string[]"); 
				Runtime.getRuntime().exec(dialog);logger.info("after call autoit");
				Thread.sleep(100);
				} catch (InterruptedException e) {
					logger.debug("handle thread interrupt");
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
	}
	
}
