package org.yujie.oditest.cssr;

import java.sql.Timestamp;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class timestep extends mainpage {
	
	
	public timestep()
	{
		gotomainpage();
	}
	
	public void filterShouldBeSelectable()
	{
		TrendReport();
		logger.info("ODI6.x-661:Time step value is updated based on time period selection started");
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
				logger.info("Select available option in the drop down list");
				break;
				}
				
		}
		if (test==true)
			logger.info("ODI6.x-661:Time step value is updated based on time period selection passed");
		else
			logger.info("ODI6.x-661:Time step value is updated based on time period selection failed");

		gotomainpage();		
	}
	
	
	public void hourSelection()
	{
		TrendReport();
		logger.info("ODI6.x-662:Time step hour selection started");
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
	

}
