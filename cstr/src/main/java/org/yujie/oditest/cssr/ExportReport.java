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


public class ExportReport extends Mainpage {
	
	private String fileUrl = "";

	
	public ExportReport ()
	{
		gotomainpage();
		TrendReport();
		
	}
			
	public String urlConstruct(String str) {
		
	    SimpleDateFormat datefor1 = new SimpleDateFormat("yyyy");
	    SimpleDateFormat datefor2 = new SimpleDateFormat("MM");
	    SimpleDateFormat datefor3 = new SimpleDateFormat("dd");
	    SimpleDateFormat datefor4 = new SimpleDateFormat("HH");
	    SimpleDateFormat datefor5 = new SimpleDateFormat("mm");
	    SimpleDateFormat datefor6 = new SimpleDateFormat("ss");

	    Date now = new Date();
	    String date = datefor1.format(now)+datefor2.format(now)+datefor3.format(now)+datefor4.format(now)+datefor5.format(now)+datefor6.format(now);
	    
	    String url = "https://sun-qa-ncp03clone.engca.bevocal.com:8443/np/odiAdvancedReporting/"+str+date;
		
		return url;
		
	}
	
	public void execute()
	{
		
		
		setTime("1 year");
		submit.click();
		String mainWinhandler = driver.getWindowHandle();
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
		
	
		driver.switchTo().window(mainWinhandler);
		
		downloadReport("PDF");
		downloadReport("CSV");
		downloadReport("Excel");
		
		
	}
	
	public void downloadReport(String type) {
		
		try{
			int source = 0;
			
			if(type.equals("PDF"))
				source = driver.getPageSource().indexOf("ExportPDF");
			else if (type.equals("CSV"))
				source = driver.getPageSource().indexOf("ExportCSV");
			else if (type.equals("Excel"))
				source = driver.getPageSource().indexOf("ExportExcel");
			
			int end = driver.getPageSource().substring(source).indexOf("\'");
			String pdfParameter = driver.getPageSource().substring(source, source+end);
					
			fileUrl = urlConstruct(pdfParameter);
			FileDownloader downloader = new FileDownloader();

			try {
				downloader.downloader(fileUrl,type);
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			} catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	
	
}
