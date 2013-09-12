package org.yujie.oditest.cssr;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ExportPdf extends Mainpage {

	private List<String> operations;
	
	public ExportPdf ()
	{
		gotomainpage();
		TrendReport();
		
		String[] steps = {"Open","Save","Cancel"};
		operations = Arrays.asList(steps);
	}
			
	
	public void exportpdf()
	{
		
		submit.click();
		Iterator<String> iterator  = operations.iterator();
		
		while (iterator.hasNext())
		{
		
		String mainWinhandler = driver.getWindowHandle();
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
		
		driver.switchTo().window(mainWinhandler);
		
		WebElement export = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.id("iExport")));
		export.click();
		
		if (iterator.next().equals("Open"))
		{
			//logger.info("this is to open file");
		}
		else if (iterator.next().equals("Save"))
		{
			//logger.info("this is to save file");
		}
		else if (iterator.next().equals("Cancel"))
		{
			//logger.info("this is to cancel");
		}
		}
//		WebElement pdf = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.linkText("Export to PDF")));
//		pdf.click();
		
		//logger.info("Test case: Export to PDF open"); 
		
		
		//driver.switchTo().frame(driver.findElement(By.name("reportContent")));
		//WebElement export  = new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.className("filterbutton")));
		
	}
	
	
	
}
