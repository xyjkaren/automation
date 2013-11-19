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
		
	}
			
	
	public void exportpdf()
	{
		setTime("3 months");
		
		submit.click();
		
		String mainWinhandler = driver.getWindowHandle();
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
		
		
		
		driver.switchTo().window(mainWinhandler);
		try{
		int source = driver.getPageSource().indexOf("ExportPDF");
		int end = driver.getPageSource().substring(source).indexOf("\'");
		String str = driver.getPageSource().substring(source, source+end);
		//System.out.println(str);
		
		WebElement export = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.id("iExport")));
		export.click();
		
		WebElement pdf = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.linkText("Export to PDF")));
		
		
		FileDownloader downloader = new FileDownloader();
	    SimpleDateFormat datefor1 = new SimpleDateFormat("yyyy");
	    SimpleDateFormat datefor2 = new SimpleDateFormat("MM");
	    SimpleDateFormat datefor3 = new SimpleDateFormat("dd");
	    SimpleDateFormat datefor4 = new SimpleDateFormat("HH");
	    SimpleDateFormat datefor5 = new SimpleDateFormat("mm");
	    SimpleDateFormat datefor6 = new SimpleDateFormat("ss");

	    Date now = new Date();
	    String date = datefor1.format(now)+datefor2.format(now)+datefor3.format(now)+datefor4.format(now)+datefor5.format(now)+datefor6.format(now);
	    
	    String url = "https://sun-qa-ncp03clone.engca.bevocal.com:8443/np/odiAdvancedReporting/"+str+date;
//	    System.out.println(url);
	
	    try {
			downloader.downloader(url);
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
