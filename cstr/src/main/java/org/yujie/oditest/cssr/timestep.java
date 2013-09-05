package org.yujie.oditest.cssr;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class timestep extends mainpage {
	
	
	public timestep()
	{
		ReportFile.addTestCase("Test step", false);
		gotomainpage();
		ReportFile.WriteToFile();
	}
	
	public boolean filterShouldBeSelectable()
	{
		
		TrendReport();
		boolean test = false;
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
				test = true;
				break;
				}				
		}

		gotomainpage();		
		return test;
	}
	
	
	public void hourSelection()
	{
		TrendReport();
	//	logger.info("ODI6.x-662:Time step hour selection started");
		boolean test = false;
		
		WebElement timeRange = driver.findElement(By.id("date_range"));
		Select select = new Select(timeRange);
		select.selectByValue("td");
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		select = new Select(timesteps); 
		select.selectByValue("HOUR");
		
		submit.click();
		
	}
	
	public void updatedBasedOnTimePeriod (String period)
	{
		TrendReport();
		//String mainWinhandler = driver.getWindowHandle();
		String testResult = "";
		
		WebElement startdate = driver.findElement(By.id("PARAM_START_DATE"));
		WebElement enddate = driver.findElement(By.id("PARAM_END_DATE"));
		WebElement startdatetime = driver.findElement(By.id("PARAM_START_DATEtime"));
		WebElement enddatetime = driver.findElement(By.id("PARAM_END_DATEtime"));
		
		String enddateValue = enddate.getAttribute("value");
		String enddatetimeValue = enddatetime.getAttribute("value");
		
		
		
		Calendar cal = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		try {
			Date date = format.parse(enddateValue + " " + enddatetimeValue);
			cal.setTime(date);
			logger.info(format.format(cal.getTime()));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		startdate.clear();
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
		
		((JavascriptExecutor)driver).executeScript("$('#PARAM_START_DATEtime').attr('readonly',false)");	
		startdatetime.clear();
		
		if (period.equals("one hour"))
			{
			
			String[] starttimes = format.format(cal.getTime()).split(" ");
			cal.add(Calendar.HOUR,1);
			String[] endtimes = format.format(cal.getTime()).split(" ");
			
			startdate.sendKeys(starttimes[0]);
			startdatetime.sendKeys(starttimes[1]);
			
			enddate.sendKeys(endtimes[0]);
			enddate.sendKeys(endtimes[1]);

			WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
			timesteps.click();
			
			Select select = new Select(timesteps);
			List<WebElement> timestep = select.getOptions();
			
			if (timestep.get(0).isSelected() == true  )
			{
			for (int i = 0; i <timestep.size(); i++)
			{
				select.selectByIndex(i);
				
					test = true;
					break;
					
			}
			
			}
		}
		else if (period.equals("one day"))
		{
				cal.add(Calendar.DATE,-1);
				String[] starttimes = format.format(cal.getTime()).split(" ");
		}
		else if (period.equals("25 hours"))
		{
				cal.add(Calendar.HOUR, -25);
				String[] starttimes = format.format(cal.getTime()).split(" ");
				
				//((JavascriptExecutor)driver).executeScript("$('#PARAM_START_DATE').val('" + times[0] + "')");
				//((JavascriptExecutor)driver).executeScript("$('#PARAM_START_DATEtime').val('"+ times[1] +"')");		
				
				
							
				startdate.sendKeys(times[0]);
				startdatetime.sendKeys(times[1]);
				
				WebElement paramtime =  driver.findElement(By.id("PARAM_START_DATEtime"));
				paramtime.click();
		}
		
//		startdate.clear();
//			Alert alert = driver.switchTo().alert();
//			alert.dismiss();
//		
//		String endDateValue = 
//		Calendar cal = Calendar.getInstance();
//		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
//		
//		
//		if (period.equals("one hour"))
//		{
//			DateFormat formathour = new SimpleDateFormat("HH:mm"); 
//			Date hour = formathour.parse(enddatetime.getAttribute("value"));
//			alert
//			
//			if(enddatetime.getAttribute("value").equals("00:00"))
//			{
//				((JavascriptExecutor)driver).executeScript("$('#PARAM_START_DATEtime').val('23:00')");
//			}
//			else
//				
//		}
//			
//		
//		
//		
//		Calendar cal = Calendar.getInstance();
//		DateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
//		
//		cal.add(cal.HOUR, -4383);
//		logger.info(format1.format(cal.getTime()));
//		String time=  format1.format(cal.getTime());
//		startdate.sendKeys(time);
//		
		
		
		
		//driver.switchTo().window(mainWinhandler);
		//submit.click();
		
			
	}
	
}
