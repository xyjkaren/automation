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

import com.thoughtworks.selenium.Selenium;


public class ExportReport extends Mainpage {
	
	private String fileUrl = "";
	private Vector<String> WebReportContent = new Vector<String>();
	private HashMap<String,Integer> elementpos = new HashMap<String,Integer>();
	
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

		
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
		WebElement reportpage = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
		
		months = reportpage.findElements(By.tagName("div"));
		int startpos  = 0, endpos = 0;		
		
		for (int i = 0;i < months.size(); i++)
		{				
			if (months.get(i).getText().equals("Month")) {
					for (int j = i;j < months.size(); j++) {
						if (NumberUtils.isNumber(months.get(j).getText().trim())) {
							startpos = j;break;
							}
		            }
				break;
		    }
		}
		
		
		for (int i = startpos; i < months.size(); i+=2) {
			if(months.get(i).getText().contains("[Note:")) {
				endpos = i;
				break;
			}
			else if(months.get(i).getText().contains("Additional Filters:")) {
				endpos = i;
				break;
			}
			
		}
		
		String row = "";
		
		for (int i = 1; i < endpos - startpos + 1 ;  i+=3){
			if (!months.get(startpos+i-1).getCssValue("left").equals("auto"))
			elementpos.put(months.get(startpos + i -1).getText().trim(),Integer.parseInt(months.get(startpos+i-1).getCssValue("left").substring(0,months.get(startpos+i-1).getCssValue("left").length()-2)));
		}
		
		int elementsize = 0;
		
		if (elementpos.size() % 8 == 0)
			elementsize = elementpos.size() /8;
		else
			elementsize = elementpos.size() / 8 + 1;
		
		String[] rowValue = new String[elementsize];
		
		for (int i = 0; i < elementsize ; i++) {
			rowValue[i] = "";
			for (int j = 0; j < 8; j ++) {System.out.println(months.get(startpos + i*8 + j ).getText());
				int min = elementpos.get(months.get(startpos + i*8 + j ).getText().trim());
				for (int k = j; k < 8; k++) {System.out.println("k"+elementpos.get(months.get(startpos + i*8 + k  ).getText().trim()) + months.get(startpos + i*8 + k  ).getText());
					if (min > elementpos.get(months.get(startpos + i*8 + k  ).getText().trim())) {
						min =  elementpos.get(months.get(startpos + i*8 + k ).getText().trim());
					}
				}
				for (Entry<String, Integer> s : elementpos.entrySet()) {
					if (min == s.getValue()){System.out.println("map " + s.getValue() + " + " + min);
						rowValue[i] = rowValue[i] + " " + s.getKey();
						elementpos.remove(s.getKey());
						break;
					}
				}
			}
			System.out.println(rowValue[i]);
		}
		
		driver.quit();
//		startpos += 35;
//		for (;startpos < startpos + 10; startpos++) {	
//		if (months.get(startpos).getText().matches("\\w{3}-\\d{2}")) {
//			break;
//		}
//		}
//		int rowlength = startpos;
//		startpos += 2;
//		for (;startpos < startpos + 25; startpos ++) {
//			if (months.get(startpos).getText().matches("\\w{3}-\\d{2}")) {
//				break;
//			} 
//		}
//		rowlength = startpos - rowlength;
//		
//		for (int i = startpos - rowlength;i < endpos; i += rowlength ) {
//			for (int j = 0; j < rowlength ; j ++) {
//				System.out.printf(" %s",months.get(j+i).getText());
//			}
//			System.out.println();
//		}
		
		driver.switchTo().window(mainWinhandler);
		
//		downloadReport("PDF");
//		downloadReport("CSV");
//		downloadReport("Excel");
		
		
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
