package org.yujie.oditest.cssr;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.WebConnection;
import com.thoughtworks.selenium.Selenium;


public class ExportReport extends Mainpage {
	
	private String fileUrl = "";
	private Vector<String> WebReportContent = new Vector<String>();
	private ReportFileCreate report = new ReportFileCreate();;

	
	public ExportReport ()
	{
		gotomainpage();
		TrendReport();
		
	}
	
	public Vector<String> getWebContent() {
		
		return this.WebReportContent;
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
		driver.findElement(By.id("PARAM_START_DATE_label")).click();		
		submit.click();
		String mainWinhandler = driver.getWindowHandle();
		List<WebElement> months;
	//	DateFormat formatmonth = new SimpleDateFormat("MMM-yy");

		
		new WebDriverWait(driver, 5).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
		WebElement reportpage = new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
		
		months = reportpage.findElements(By.tagName("span"));
		int startpos  = 0, endpos = 0;		
		
		for (int i = 0;i < months.size(); i++)
		{				
			if (months.get(i).getText().equals("Month")) {
					for (int j = i;j < months.size(); j++) {
						if (NumberUtils.isNumber(months.get(j).getText().trim())) {
							startpos = j;break;
							}
		            }
				
		    }
			else if(months.get(i).getText().contains("[Note:")) {
				endpos = i;
				break;
			}
			else if(months.get(i).getText().contains("Additional Filters:")) {
				endpos = i;
				break;
			}
			
		}
		
		int row = (endpos - startpos) / 8;
		
		String rowValue = ""; 
		String Month = "";
		String CallVolume = "";
		String Transfers = "";
		String TransferRate = "";
		String ContainmentRate = "";
		String CallDuration = "";
		String AverageCallDuration = "";
		String AvgCallDurationForTransferred = "";
		
		for (int i = 0; i < row; i++) {
			
			Month = months.get(startpos + i*8 + 5).getText().trim();
			CallVolume = months.get(startpos + i*8 ).getText().trim(); 
			Transfers = months.get(startpos + i*8 + 1).getText().trim(); 
			TransferRate = months.get(startpos + i*8 + 7).getText().trim(); 
			ContainmentRate = months.get(startpos + i*8 + 2).getText().trim(); 
			CallDuration = months.get(startpos + i*8 + 3).getText().trim(); 
			AverageCallDuration = months.get(startpos + i*8 + 4).getText().trim(); 
			AvgCallDurationForTransferred = months.get(startpos + i*8 + 6).getText().trim(); 
			
			rowValue = Month+CallVolume+Transfers+TransferRate+ContainmentRate+CallDuration+AverageCallDuration+AvgCallDurationForTransferred;
			WebReportContent.add(rowValue.replaceAll(",",""));
			
		}		
		driver.switchTo().window(mainWinhandler);
		
		downloadReport("PDF");
		downloadReport("CSV");
		downloadReport("Excel");
		report.excelReportRead();
		report.ExtractExcelContent();
		report.pdfReportRead();
		report.ExtractPDFContent();
		reportComparison();
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
	
	public void reportComparison() {
		
		boolean test = true;
		
		for(int i = 0; i < WebReportContent.size() ; i++) {
			if (!WebReportContent.get(i).equals(report.getReport("pdf").get(i))) {
				test = false;
			}
		}
		System.out.println(test);
		test = true;
		for(int i = 0; i < WebReportContent.size() ; i++) {
			if (!WebReportContent.get(i).equals(report.getReport("xls").get(i))) {
				System.out.println(WebReportContent.get(i) + "  " +report.getReport("xls").get(i));
				test = false;
			}
		}
		System.out.println(test);
		driver.quit();
	}
	public static void main(String[] args) {
		ReportFileCreate f = new ReportFileCreate();
		f.pdfReportRead();
		f.ExtractPDFContent();
System.out.println(f.getReport("pdf").get(0));
		
	}
	
}
