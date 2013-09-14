package org.yujie.oditest.cssr;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import junit.framework.Assert;


import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.Sets.SetView;

public class Timestep extends Mainpage {
	
	private String timestepTestresult;
	private DateFormat format;
	private WebElement startdate;
	private WebElement enddate;
	private WebElement startdatetime;
	private WebElement enddatetime;
	
	
	public Timestep()
	{
		gotomainpage();
	}
	
	public boolean filterShouldBeSelectable()
	{
		
		TrendReport();
		boolean result = false;
		WebElement timeRange = driver.findElement(By.id("date_range"));
		Select select = new Select(timeRange);
		select.selectByValue("l30d");
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		
		timesteps.click();
		
		select = new Select(timesteps);
		List<WebElement> timestep = select.getOptions();
		for (int i = 0; i <timestep.size(); i++)
		{
			select.selectByIndex(i);
			if (timestep.get(i).isSelected() == true)
			{
				result = true;
				break;
				}				
		}

		gotomainpage();		
		return result;
	}
	
	//	ODI6.x-646: CSTR - Search
	
	//	ODI6.x-650:Report look'n Feel: Enhancement from Portal
	public void EnhancementFromPortal()
	{
		TrendReport();
		try{			
		
			WebElement timeRange = driver.findElement(By.id("date_range"));
			Select select = new Select(timeRange);
			select.selectByValue("l30d");
		
			WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
			timesteps.click();
			
			select = new Select(timesteps); 
			select.selectByValue("WEEK");
			
			submit.click();
		} catch (NoSuchElementException e)
		{
			gotomainpage();
			weekSelection();
		}
		
		try{
			
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			
			WebElement header = driver.findElement(By.xpath("//*[text() = '']"));
			//header.getCssValue(arg0)
			
		} catch(TimeoutException e)
		{
			logger.info("enhancementFromPotal find element exception");
			gotomainpage();
			EnhancementFromPortal();
		}
		                                          
		
		
	}
	
	public void DNISFilterNewLook()
	{
		TrendReport();
		WebElement DNIS;
		Select select;
		
		try{
			
			DNIS = driver.findElement(By.id("PARAM_DNIS"));
			select = new Select(DNIS);
			WebElement firstOption = select.getFirstSelectedOption();
			
			if (firstOption.getText().equals("All"))
			{
				List<WebElement> allop = select.getOptions();
				for (WebElement option : allop) { //iterate over the options
				    logger.info(option.getAttribute("bgcolor"));
				}
				}
		} catch (NoSuchElementException e)
		{
			gotomainpage();
			DNISFilterNewLook();
		}
		
		
			
		
		driver.quit();
		
	}
	
	//	ODI6.x-662:Time step hour selection started"
	public void daySelection()
	{
		TrendReport();
			
		WebElement timeRange = driver.findElement(By.id("date_range"));
		Select select = new Select(timeRange);
		select.selectByValue("");
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		select = new Select(timesteps); 
		select.selectByValue("DAY");
		
		submit.click();
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
		try{
	
		WebElement Hour = driver.findElement(By.xpath("//*[text()='Day']"));
		ReportFile.addTestCase("ODI6.x-662:Time step hour selection started", "ODI6.x-662:Time step hour selection started => succeed");

		}
		catch(NoSuchElementException e)
		{
			ReportFile.addTestCase("ODI6.x-662:Time step hour selection started", "ODI6.x-662:Time step hour selection started => fail");
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
	}
	
	public void weekSelection()
	{
		TrendReport();
		try{			
		
			WebElement timeRange = driver.findElement(By.id("date_range"));
			Select select = new Select(timeRange);
			select.selectByValue("l30d");
		
			WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
			timesteps.click();
			
			select = new Select(timesteps); 
			select.selectByValue("WEEK");
			
			submit.click();
		} catch (NoSuchElementException e)
		{
			gotomainpage();
			weekSelection();
		}
		
		try{
			
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			WebElement week = driver.findElement(By.xpath("//*[text()='Week']"));
			
			ReportFile.addTestCase("ODI6.x-664:Time step week selection started", "ODI6.x-664:Time step week selection started => succeed");
		}
		catch(NoSuchElementException e)
		{
			ReportFile.addTestCase("ODI6.x-664:Time step week selection started", "ODI6.x-664:Time step week selection started => fail");
		} catch(TimeoutException  e)
		{
			driver.switchTo().defaultContent();
			gotomainpage();
			weekSelection();
		}
		
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
		
	}
	
	public void monthSelection()
	{
		TrendReport();
		
		setTime("3 months");
		
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps); 
		select.selectByValue("MONTH");
		
		submit.click();
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
		try{
			
			WebElement week = driver.findElement(By.xpath("//*[text()='Month']"));
			
			ReportFile.addTestCase("ODI6.x-664:Time step week selection started", "ODI6.x-664:Time step month selection started => succeed");
		}
		catch(NoSuchElementException e)
		{
		ReportFile.addTestCase("ODI6.x-664:Time step week selection started", "ODI6.x-664:Time step month selection started => fail");
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
			
	}
	
	public void quarterSelection()
	{
		TrendReport();
		
		setTime("1 year");
	
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps); 
		select.selectByValue("QUARTER");
		
		submit.click();
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
		try{
			
			WebElement week = driver.findElement(By.xpath("//*[text()='Quarter']"));
			
			ReportFile.addTestCase("ODI6.x-664:Time step week selection started", "ODI6.x-664:Time step quarter selection started => succeed");
		}
		catch(NoSuchElementException e)
		{
		ReportFile.addTestCase("ODI6.x-664:Time step week selection started", "ODI6.x-664:Time step quarter selection started => failed");
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
			
	}
	
	public void yearSelection()
	{
		TrendReport();
		
		setTime("2 years");
		
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps); 
		select.selectByValue("YEAR");
		
		submit.click();
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
		try{
			
			WebElement week = driver.findElement(By.xpath("//*[text()='Year']"));
			
			ReportFile.addTestCase("ODI6.x-664:Time step week selection started", "ODI6.x-664:Time step Year selection started => succeed");
		}
		catch(NoSuchElementException e)
		{
		ReportFile.addTestCase("ODI6.x-664:Time step week selection started", "ODI6.x-664:Time step Year selection started => failed");
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
			
	}
	
	
	
	
	public void weekSelectionSorting()
	{
		TrendReport();
		
		List<WebElement> week;
		ArrayList<Date> Weeklist = new ArrayList<Date>();	
		
		setTime("3 months");
		
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps); 
		select.selectByValue("WEEK");
		
		submit.click();
		
		try{	
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement reportpage = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			
			
			week = reportpage.findElements(By.tagName("div"));
			int startpos  = 0, endpos = 0;		
			
			for (int i = 0;i < week.size(); i++)
			{
				if (week.get(i).getText().equals("Week"))
					startpos = i;
				else if(week.get(i).getText().equals("Additional Filters:"))
					endpos = i;
			}
			startpos += 35;
			
			for (;startpos < endpos; startpos += 15)
			{
				logger.info(week.get(startpos).getText());
				String weeksub = week.get(startpos).getText().substring(0, 10);
				try {
					Date weekdate = format.parse(weeksub);
					Weeklist.add(weekdate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					driver.quit();
				}
				
			}
			
			for (int i = 0; i < Weeklist.size() - 1; i ++)
			{
				if (Weeklist.get(i).before(Weeklist.get(i+1)))
					ReportFile.addTestCase("ODI6.x-670:Time step week sorting", "ODI6.x-670:Time step week sorting => succeed");
				else
					ReportFile.addTestCase("ODI6.x-670:Time step week sorting", "ODI6.x-670:Time step week sorting => failed");
					
			}
		
		}
			
		catch(NoSuchElementException e)
		{
			logger.info("can't find element");
		}	
		catch(TimeoutException e) 
		{
			gotomainpage();
			weekSelectionSorting();
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
		
	}
	
	public void daySelectionSorting()
	{
		TrendReport();
		
		List<WebElement> week;
		ArrayList<Date> Weeklist = new ArrayList<Date>();	
		
		WebElement timeRange = driver.findElement(By.id("date_range"));
		Select select = new Select(timeRange);
		select.selectByValue("l30d");
		
		enddate = driver.findElement(By.id("PARAM_END_DATE"));
		String enddateValue = enddate.getAttribute("value");
		format = new SimpleDateFormat("MM/dd/yyyy");
		
		Calendar cal = Calendar.getInstance();
		try {
			Date date = format.parse(enddateValue);
			cal.setTime(date);
						
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		select = new Select(timesteps); 
		select.selectByValue("DAY");
		
		submit.click();
		
		try{	
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement reportpage = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			
			
			week = reportpage.findElements(By.tagName("div"));
			int startpos  = 0, endpos = 0;		
			
			for (int i = 0;i < week.size(); i++)
			{
				if (week.get(i).getText().equals("Day"))
					{startpos = i;logger.info("1"+i);}
				else if (week.get(i).getText().equals("2013/08/13"))
				{startpos = i;logger.info("2"+i);}
				else if (week.get(i).getText().equals("2013/08/20"))
				{startpos = i;logger.info("3"+i);}
				else if (week.get(i).getText().equals("2013/08/22"))
				{startpos = i;logger.info("4"+i);}
				else if(week.get(i).getText().equals("Additional Filters:"))
					{endpos = i; logger.info("5"+i);}
			}
			startpos += 35;
			
			for (;startpos < endpos; startpos += 15)
			{
				logger.info(week.get(startpos).getText());
				String weeksub = week.get(startpos).getText().substring(0, 10);
				try {
					Date weekdate = format.parse(weeksub);
					Weeklist.add(weekdate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					driver.quit();
				}
				
			}
			
			for (int i = 0; i < Weeklist.size() - 1; i ++)
			{
				if (Weeklist.get(i).before(Weeklist.get(i+1)))
					ReportFile.addTestCase("ODI6.x-670:Time step day sorting", "ODI6.x-670:Time step week sorting => succeed");
				else
					ReportFile.addTestCase("ODI6.x-670:Time step day sorting", "ODI6.x-670:Time step week sorting => failed");
					
			}
		
		}
			
		catch(NoSuchElementException e)
		{
			logger.info("can't find element");
		}	
		catch(TimeoutException e) 
		{
			gotomainpage();
			weekSelectionSorting();
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
		
	}
	
	public void monthSelectionSorting()
	{
		TrendReport();
		
		List<WebElement> week;
		ArrayList<Date> Weeklist = new ArrayList<Date>();	
	
		setTime("1 year");
		
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps); 
		select.selectByValue("MONTH");
		
		submit.click();
		
		try{	
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement reportpage = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			
			
			week = reportpage.findElements(By.tagName("div"));
			int startpos  = 0, endpos = 0;		
			
			for (int i = 0;i < week.size(); i++)
			{
				if (week.get(i).getText().equals("Month"))
					startpos = i;
				else if(week.get(i).getText().equals("Additional Filters:"))
					endpos = i; 
			}
			
			startpos += 35;
			
			for (;startpos < endpos; startpos += 15)
			{
				logger.info(week.get(startpos).getText());
				String weeksub = week.get(startpos).getText().substring(0, 10);
				try {
					Date weekdate = format.parse(weeksub);
					Weeklist.add(weekdate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					driver.quit();
				}
				
			}
			
			for (int i = 0; i < Weeklist.size() - 1; i ++)
			{
				if (Weeklist.get(i).before(Weeklist.get(i+1)))
					ReportFile.addTestCase("ODI6.x-670:Time step day sorting", "ODI6.x-670:Time step month sorting => succeed");
				else
					ReportFile.addTestCase("ODI6.x-670:Time step day sorting", "ODI6.x-670:Time step month sorting => failed");
					
			}
		
		}
			
		catch(NoSuchElementException e)
		{
			logger.info("can't find element");
		}	
		catch(TimeoutException e) 
		{
			gotomainpage();
			weekSelectionSorting();
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
		
	}
	
	public void quarterSelectionSorting()
	{
		TrendReport();
		
		List<WebElement> week;
		ArrayList<Date> Weeklist = new ArrayList<Date>();	
	
		setTime("13 months");
		
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps); 
		select.selectByValue("QUARTER");
		
		submit.click();
		
		try{	
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement reportpage = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			
			
			week = reportpage.findElements(By.tagName("div"));
			int startpos  = 0, endpos = 0;		
			
			for (int i = 0;i < week.size(); i++)
			{
				if (week.get(i).getText().equals("Quarter"))
					startpos = i;
				else if(week.get(i).getText().equals("Additional Filters:"))
					endpos = i; 
			}
			
			startpos += 35;
			
			for (;startpos < endpos; startpos += 15)
			{
				logger.info(week.get(startpos).getText());
				String weeksub = week.get(startpos).getText().substring(0, 10);
				try {
					Date weekdate = format.parse(weeksub);
					Weeklist.add(weekdate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					driver.quit();
				}
				
			}
			
			for (int i = 0; i < Weeklist.size() - 1; i ++)
			{
				if (Weeklist.get(i).before(Weeklist.get(i+1)))
					ReportFile.addTestCase("ODI6.x-670:Time step day sorting", "ODI6.x-670:Time step quarter sorting => succeed");
				else
					ReportFile.addTestCase("ODI6.x-670:Time step day sorting", "ODI6.x-670:Time step quarter sorting => failed");
					
			}
		
		}
			
		catch(NoSuchElementException e)
		{
			logger.info("can't find element");
		}	
		catch(TimeoutException e) 
		{
			gotomainpage();
			weekSelectionSorting();
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
		
	}
		
	public void yearSelectionSorting()
	{
		TrendReport();
		
		List<WebElement> week;
		ArrayList<Date> Weeklist = new ArrayList<Date>();	
	
		setTime("3 years");
		
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps); 
		select.selectByValue("YEAR");
		
		submit.click();
		
		try{	
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement reportpage = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			
			
			week = reportpage.findElements(By.tagName("div"));
			int startpos  = 0, endpos = 0;		
			
			for (int i = 0;i < week.size(); i++)
			{
				if (week.get(i).getText().equals("Year"))
					startpos = i;
				else if(week.get(i).getText().equals("Additional Filters:"))
					endpos = i; 
			}
			
			startpos += 35;
			
			for (;startpos < endpos; startpos += 15)
			{
				logger.info(week.get(startpos).getText());
				String weeksub = week.get(startpos).getText().substring(0, 10);
				try {
					Date weekdate = format.parse(weeksub);
					Weeklist.add(weekdate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					driver.quit();
				}
				
			}
			
			for (int i = 0; i < Weeklist.size() - 1; i ++)
			{
				if (Weeklist.get(i).before(Weeklist.get(i+1)))
					ReportFile.addTestCase("ODI6.x-670:Time step day sorting", "ODI6.x-670:Time step year sorting => succeed");
				else
					ReportFile.addTestCase("ODI6.x-670:Time step day sorting", "ODI6.x-670:Time step year sorting => failed");
					
			}
		
		}
			
		catch(NoSuchElementException e)
		{
			logger.info("can't find element");
		}	
		catch(TimeoutException e) 
		{
			gotomainpage();
			weekSelectionSorting();
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//operations for different time range
	public void timeperiodchange (String period)
	{
		boolean result = false;
		//String mainWinhandler = driver.getWindowHandle();
	
		startdate = driver.findElement(By.id("PARAM_START_DATE"));
		enddate = driver.findElement(By.id("PARAM_END_DATE"));
		startdatetime = driver.findElement(By.id("PARAM_START_DATEtime"));
		enddatetime = driver.findElement(By.id("PARAM_END_DATEtime"));
		
		String enddateValue = enddate.getAttribute("value");
		String enddatetimeValue = enddatetime.getAttribute("value");
		
		((JavascriptExecutor)driver).executeScript("$('#PARAM_START_DATEtime').attr('readonly',false)");	
		((JavascriptExecutor)driver).executeScript("$('#PARAM_END_DATEtime').attr('readonly',false)");	

		
		Calendar cal = Calendar.getInstance();
		format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		try {
			Date date = format.parse(enddateValue + " " + enddatetimeValue);
			cal.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		startdate.clear();
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
				
		if (period.equals("one hour"))
			{
			try {
			
				String[] starttimes = format.format(cal.getTime()).split(" ");
				
				cal.add(Calendar.HOUR,1);			
				String[] endblock = format.format(cal.getTime()).split(" ");
				
				startdate.sendKeys(starttimes[0]);
				startdatetime.sendKeys(starttimes[1]);
				
				enddate.clear();
				Alert alert2 = driver.switchTo().alert();
				alert2.dismiss();
				
				enddate.sendKeys(endblock[0]);
				
				enddatetime.clear();
							
				enddatetime.sendKeys(endblock[1]);
	
				WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
				timesteps.click();
				
				Select select = new Select(timesteps);
				List<WebElement> timestep = select.getOptions();
				
				if (timestep.get(0).isEnabled() == true  )
				{
					for (int i = 1; i <timestep.size(); i++)
					{
						select.selectByIndex(i);
						if (timestep.get(i).isEnabled() == true)
							{
							timestepTestresult = timestepTestresult + " Test case one hour period failed;";  
							break;
							}
						else
							timestepTestresult = timestepTestresult + " Test case one hour period succeed;";							
					}
			}
		}
		catch (NoAlertPresentException e)
		{
			e.printStackTrace();
			driver.quit();
		}
		}
		else if (period.equals("one day"))
		{
			cal.add(Calendar.DATE,-1);		
			result = teststeps(cal, true, true, false, false, false, false);		
			timestepTestresult += "Time period one day " + result + ";/s";

		}
		else if (period.equals("25 hours"))
		{
			cal.add(Calendar.HOUR,-25);
			result = teststeps(cal, true, true, false, false, false, false);
			timestepTestresult += "Time period 25 hours " + result + ";/s";

		}
		else if (period.equals("26 hours"))
		{
			cal.add(Calendar.HOUR,-26);
			result = teststeps(cal, false, true, false, false, false, false);
			timestepTestresult += "Time period 26 hours " + result + ";/s";

		}
		else if (period.equals("one week"))
		{
			cal.add(Calendar.DATE,-7);
			result = teststeps(cal, false, true, true, false, false, false);
			timestepTestresult += "Time period one week " + result + ";/s";

		}
		else if (period.equals("one month"))
		{
			cal.add(Calendar.MONTH, -1);
			result = teststeps(cal, false, true, true, true, false, false);
			timestepTestresult += "Time period one month " + result + ";/s";

		}
		else if (period.equals("32 days"))
		{
			cal.add(Calendar.DATE, -32);
			result = teststeps(cal, false, true, true, true, false, false);
			timestepTestresult += "Time period 32 days " + result + ";/s";

		}
		else if (period.equals("33 days"))
		{
			cal.add(Calendar.DATE, -33);
			result = teststeps(cal, false, false, true, true, false, false);		
			timestepTestresult += "Time period 33 days " + result + ";/s";

		}
		else if (period.equals("3 months"))
		{
			cal.add(Calendar.MONTH, -3);
			result = teststeps(cal, false, false, true, true, true, false);
			timestepTestresult += "Time period 3 months " + result + ";/s";

		}
		else if (period.equals("5 months"))
		{
			cal.add(Calendar.MONTH, -5);
			result = teststeps(cal, false, false, true, true, true, false);
			timestepTestresult += "Time period 5 months " + result + ";/s";

		}
		else if (period.equals("6 months & 1 day"))
		{
			cal.add(Calendar.MONTH, -6);
			cal.add(Calendar.DATE, -1);
			result = teststeps(cal, false, false, false, true, true, false);
			timestepTestresult += "Time period 6 months & 1 day " + result + ";/s";
		}
		else if (period.equals("7 months"))
		{
			cal.add(Calendar.MONTH, -7);
			result = teststeps(cal, false, false, false, true, true, false);
			timestepTestresult += "Time period 7 months " + result + ";/s";
		}
		else if (period.equals("1 year"))
		{
			cal.add(Calendar.YEAR, -1);
			result = teststeps(cal, false, false, false, true, true, true);
			timestepTestresult += "Time period 1 year " + result + ";";
		}
		else if (period.equals("13 months"))
		{
			cal.add(Calendar.MONTH, -13);
			result = teststeps(cal, false, false, false, true, true, true);
			timestepTestresult += "Time period 13 months " + result + ";/s";
		}
		else if (period.equals("5 years"))
		{
			cal.add(Calendar.YEAR, -5);
			result = teststeps(cal, false, false, false, false, true, true);
			timestepTestresult += "Time period 5 years " + result + ";/s";
		}
		else if (period.equals("6 years"))
		{
			cal.add(Calendar.YEAR, -6);
			result = teststeps(cal, false, false, false, false, false, true);
			timestepTestresult += "Time period 6 years " + result + ";/s";
		}
		
	
}
		
	//test different time period.	
	public boolean teststeps(Calendar calender, boolean hour, boolean day, boolean week, boolean month, boolean quarter,boolean year)
	{
		boolean result = false;
		
		String[] starttimes = format.format(calender.getTime()).split(" ");
		startdate.sendKeys(starttimes[0]);
		
		if (day == true)
			{
				startdatetime.clear();
				startdatetime.sendKeys(starttimes[1]);
			}
		
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps);
		List<WebElement> timestep = select.getOptions();
		
		if(timestep.get(0).isEnabled() == hour )
			{			
				if(timestep.get(1).isEnabled() == day)
				{				
					if (timestep.get(2).isEnabled() == week)
					{				
						if (timestep.get(3).isEnabled() == month)
						{				
							if (timestep.get(4).isEnabled() == quarter)
							{			
								if (timestep.get(5).isEnabled() == year)
								{			
									result = true;
								}
							}
						}
					}
				}
			}
		
			return result;
	}
	
	// test case :updated based on time period
	public void updatedBasedOnTimePeriod()
	{
		String TestCase = "ODI6.x-661:Time Step value is updated based on time period selection";
		
		TrendReport();
		
		timestepTestresult = "";
		
		String[] timeperiod = {"one hour","one day","25 hours","26 hours","one week","one month","32 days","33 days",
				"3 months","5 months","6 months","7 months","1 year","13 months","5 years","6 years"}; 
		for (String s : timeperiod)
		{
			timeperiodchange(s);
		}
		logger.info(timestepTestresult);
		ReportFile.addTestCase(TestCase, timestepTestresult);
		
		//ReportFile.WriteToFile();
		//driver.quit();
	}

	//set start time with specific time range
	public void setTime (String period)
	{
		startdate = driver.findElement(By.id("PARAM_START_DATE"));
		enddate = driver.findElement(By.id("PARAM_END_DATE"));
		String enddateValue = enddate.getAttribute("value");

		Calendar cal = Calendar.getInstance();
		format = new SimpleDateFormat("MM/dd/yyyy");
		try {
			Date date = format.parse(enddateValue);
			cal.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		startdate.clear();
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
		if(period.equals("1 month"))
		{
			cal.add(Calendar.MONTH, -1);
			String starttime = format.format(cal.getTime());
			startdate.sendKeys(starttime);
		}
		else if(period.equals("3 months"))
		{
			cal.add(Calendar.MONTH, -3);
			String starttime = format.format(cal.getTime());
			startdate.sendKeys(starttime);
		}
		else if(period.equals("1 year"))
		{
			cal.add(Calendar.YEAR, -1);
			String starttime = format.format(cal.getTime());
			startdate.sendKeys(starttime);
		}
		else if(period.equals("13 months"))
		{
			cal.add(Calendar.MONTH, -13);
			String starttime = format.format(cal.getTime());
			startdate.sendKeys(starttime);
		}
		else if(period.equals("2 years"))
		{
			cal.add(Calendar.YEAR, -2);
			String starttime = format.format(cal.getTime());
			startdate.sendKeys(starttime);
		}
		else if (period.equals("3 years"))
		{
			cal.add(Calendar.YEAR, -3);
			String starttime = format.format(cal.getTime());
			startdate.sendKeys(starttime);
		}		
	}
	
}
	

