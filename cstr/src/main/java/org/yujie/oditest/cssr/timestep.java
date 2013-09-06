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
		ReportFile.addTestCase("Test step", false);
		gotomainpage();
		ReportFile.WriteToFile();
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
	
	public void updatedBasedOnTimePeriod (String period)
	{
		TrendReport();
		boolean result = false;
		//String mainWinhandler = driver.getWindowHandle();
		testResult = "";
		
		startdate = driver.findElement(By.id("PARAM_START_DATE"));
		enddate = driver.findElement(By.id("PARAM_END_DATE"));
		startdatetime = driver.findElement(By.id("PARAM_START_DATEtime"));
		enddatetime = driver.findElement(By.id("PARAM_END_DATEtime"));
		
		String enddateValue = enddate.getAttribute("value");
		String enddatetimeValue = enddatetime.getAttribute("value");
		
		
		
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
		
		((JavascriptExecutor)driver).executeScript("$('#PARAM_START_DATEtime').attr('readonly',false)");	
		((JavascriptExecutor)driver).executeScript("$('#PARAM_END_DATEtime').attr('readonly',false)");	

		startdatetime.clear();
		
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
				logger.info("one day "+ result);
				
				
		}
		else if (period.equals("25 hours"))
		{
				cal.add(Calendar.HOUR,-25);
				result = teststeps(cal, true, true, false, false, false, false);
				logger.info("25 hours " +result);
		}
		else if (period.equals("26 hours"))
		{
			cal.add(Calendar.HOUR,-26);
			result = teststeps(cal, false, true, false, false, false, false);
			logger.info("26 hours " +result);
		}
		else if (period.equals("one week"))
		{
			cal.add(Calendar.DATE,-7);
			result = teststeps(cal, false, true, true, false, false, false);
			logger.info("one week " +result);
		}
		else if (period.equals("one month"))
		{
			cal.add(Calendar.MONTH, -1);
			result = teststeps(cal, false, true, true, true, false, false);
			logger.info("one month " +result);
		}
		else if (period.equals("32 days"))
		{
			cal.add(Calendar.DATE, -32);
			result = teststeps(cal, false, true, true, true, false, false);
			logger.info("32 days " +result);
		}
		else if (period.equals("33 days"))
		{
			cal.add(Calendar.DATE, -33);
			result = teststeps(cal, false, false, true, true, false, false);
			logger.info("33 days " +result);
		}
		else if (period.equals("3 months"))
		{
			cal.add(Calendar.MONTH, -3);
			result = teststeps(cal, false, false, true, true, true, false);
			logger.info("3 months " +result);
		}
		else if (period.equals("5 months"))
		{
			cal.add(Calendar.MONTH, -5);
			result = teststeps(cal, false, false, true, true, true, false);
			logger.info("5 months " +result);
		}
		else if (period.equals("6 months & 1 day"))
		{
			cal.add(Calendar.MONTH, -6);
			cal.add(Calendar.DATE, -1);
			result = teststeps(cal, false, false, false, true, true, false);
			logger.info("6 months & 1 day " +result);
		}
		else if (period.equals("7 months"))
		{
			cal.add(Calendar.MONTH, -7);
			result = teststeps(cal, false, false, false, true, true, false);
			logger.info("7 months " +result);
		}
		else if (period.equals("1 year"))
		{
			cal.add(Calendar.YEAR, -1);
			result = teststeps(cal, false, false, false, true, true, true);
			logger.info("1 year " +result);
		}
		else if (period.equals("13 months"))
		{
			cal.add(Calendar.MONTH, -13);
			result = teststeps(cal, false, false, false, true, true, true);
			logger.info("13 months " +result);
		}
		else if (period.equals("5 years"))
		{
			cal.add(Calendar.YEAR, -5);
			result = teststeps(cal, false, false, false, false, true, true);
			logger.info("5 years " +result);
		}
		else if (period.equals("6 years"))
		{
			cal.add(Calendar.YEAR, -6);
			result = teststeps(cal, false, false, false, false, false, true);
			logger.info("6 years " +result);
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
	
	public boolean teststeps(Calendar calender, boolean hour, boolean day, boolean week, boolean month, boolean quarter,boolean year)
	{
		boolean result = false;
		
		String[] starttimes = format.format(calender.getTime()).split(" ");
		
		startdate.sendKeys(starttimes[0]);
		startdatetime.sendKeys(starttimes[1]);
		
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps);
		List<WebElement> timestep = select.getOptions();
		
		if(timestep.get(0).isSelected() == hour )
			{
				logger.info("1:" + timestep.get(0).isSelected());
				if(timestep.get(1).isEnabled() == day)
				{				logger.info("2:" + timestep.get(1).isSelected());

					if (timestep.get(2).isEnabled() == week)
					{				logger.info("3" + timestep.get(2).isSelected());

						if (timestep.get(3).isEnabled() == month)
						{				logger.info("4:" + timestep.get(3).isSelected());

							if (timestep.get(4).isEnabled() == quarter)
							{				logger.info("5:" + timestep.get(4).isSelected());

								if (timestep.get(5).isEnabled() == year)
								{				logger.info("6:" + timestep.get(5).isSelected());

									result = true;
								}
							}
						}
					}
				}
			}
		
			return result;
	}
	
	public void timestepTest ()
	{
		String[] timeperiod = {"one hour","one day","25 hours","26 hours","one week","one month","32 days","33 days","3 months","5 months","6 month & 1 day","7 months",	"1 year","13 months","5 years","6 years"}; 
		for (String s : timeperiod)
		{
			updatedBasedOnTimePeriod(s);
		}
	
	}
	
}
	

