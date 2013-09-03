package org.yujie.oditest.cssr;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class timestep extends mainpage {
	
	
	public timestep()
	{
		ReportFile.addTestCase("Test step", false);
		gotomainpage();
		ReportFile.WriteToFile();
	}
	
	public void filterShouldBeSelectable()
	{
		
		TrendReport();
	//	logger.info("ODI6.x-661:Time step value is updated based on time period selection started");
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
		//logger.info("Select available option in the drop down list");
				break;
				}
				
		}
	//	if (test==true)
	//		logger.info("ODI6.x-661:Time step value is updated based on time period selection passed");
//		else
	//		logger.info("ODI6.x-661:Time step value is updated based on time period selection failed");

		gotomainpage();		
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
	
	public void updatedBasedOnTimePeriod ()
	{
		TrendReport();
		
		WebElement startdate = driver.findElement(By.id("PARAM_START_DATE"));
		startdate.clear();
		String a = "08/12/2012";
//		logger.info(startdate.getText());
		Calendar cal = Calendar.getInstance();
		DateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		try{
			cal.setTime(format1.parse(a));
			cal.add(cal.DATE, -1);
			logger.info(format1.format(cal.getTime()));
			startdate.sendKeys(format1.format(cal.getTime()));
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
}
