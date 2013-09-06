package org.yujie.oditest.cssr;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;


import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class timestep extends mainpage {
	
	private String testResult;
	private DateFormat format;
	private WebElement startdate;
	private WebElement enddate;
	private WebElement startdatetime;
	private WebElement enddatetime;
	
	
	public timestep()
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
	
	
	public void hourSelection()
	{
		TrendReport();
	//	logger.info("ODI6.x-662:Time step hour selection started");
		boolean result = false;
		
		WebElement timeRange = driver.findElement(By.id("date_range"));
		Select select = new Select(timeRange);
		select.selectByValue("td");
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		select = new Select(timesteps); 
		select.selectByValue("HOUR");
		
		submit.click();
		
	}
	
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
					testResult = testResult + " Test case one hour period failed;";  
			//		test = true;
					break;
					}
				else
					testResult = testResult + " Test case one hour period succeed;";
					
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
		}
		else if (period.equals("25 hours"))
		{
				cal.add(Calendar.HOUR,-25);
				result = teststeps(cal, true, true, false, false, false, false);
		}
		else if (period.equals("26 hours"))
		{
			cal.add(Calendar.HOUR,-26);
			result = teststeps(cal, false, true, false, false, false, false);
		}
		else if (period.equals("one week"))
		{
			cal.add(Calendar.DATE,-7);
			result = teststeps(cal, false, true, true, false, false, false);
		}
		else if (period.equals("one month"))
		{
			cal.add(Calendar.MONTH, -1);
			result = teststeps(cal, false, true, true, true, false, false);
		}
		else if (period.equals("32 days"))
		{
			cal.add(Calendar.DATE, -32);
			result = teststeps(cal, false, true, true, true, false, false);
		}
		else if (period.equals("33 days"))
		{
			cal.add(Calendar.DATE, -33);
			result = teststeps(cal, false, false, true, true, false, false);
		}
		else if (period.equals("3 months"))
		{
			cal.add(Calendar.MONTH, -3);
			result = teststeps(cal, false, false, true, true, true, false);
		}
		else if (period.equals("5 months"))
		{
			cal.add(Calendar.MONTH, -5);
			result = teststeps(cal, false, false, true, true, true, false);
		}
		else if (period.equals("6 months & 1 day"))
		{
			cal.add(Calendar.MONTH, -6);
			cal.add(Calendar.DATE, -1);
			result = teststeps(cal, false, false, false, true, true, false);
		}
		else if (period.equals("7 months"))
		{
			cal.add(Calendar.MONTH, -7);
			result = teststeps(cal, false, false, false, true, true, false);
		}
		else if (period.equals("1 year"))
		{
			cal.add(Calendar.YEAR, -1);
			result = teststeps(cal, false, false, false, true, true, true);
			testResult += "Time period 1 year " + result + ";";
		}
		else if (period.equals("13 months"))
		{
			cal.add(Calendar.MONTH, -13);
			result = teststeps(cal, false, false, false, true, true, true);
			testResult += "Time period 13 months " + result + ";";
		}
		else if (period.equals("5 years"))
		{
			cal.add(Calendar.YEAR, -5);
			result = teststeps(cal, false, false, false, false, true, true);
			testResult += "Time period 5 years " + result + ";";
		}
		else if (period.equals("6 years"))
		{
			cal.add(Calendar.YEAR, -6);
			result = teststeps(cal, false, false, false, false, false, true);
			testResult += "Time period 6 years " + result + ";";
		}
		
	
}
	
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
	
	public void updatedBasedOnTimePeriod()
	{
		TrendReport();
		testResult = "";
		String[] timeperiod = {"7 months",	"1 year","13 months","5 years","6 years"}; 
		for (String s : timeperiod)
		{
			timeperiodchange(s);
		}
		logger.info(testResult);
		ReportFile.addTestCase(testResult, false);
		
		ReportFile.WriteToFile();
		driver.quit();
	}
	
}
	

