package org.ODI.IvrCallRep;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class mainpage {

	public static Logger logger = LoggerFactory.getLogger(Main.class);
	protected static WebDriver driver;
	protected String webUrl; 
	private static final String IEpath = System.getProperty("user.dir");
	protected userInfo user;
	
	
	
	public mainpage()
	{
		try {
			user =  new userInfo("userinfo.xml");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		webUrl = user.getProperties("weburl");
	}
	
	public void setDriver()
	{
		String webdriver = user.getProperties("browser");
		
		if (webdriver.toLowerCase().equals("ie"))
		{
			System.setProperty("webdriver.ie.driver", IEpath+"\\iedriver\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		else
			driver = new FirefoxDriver();
	}
	
	public void gotomainpage()
	{
		//WebElement acc = driver.findElement(By.linkText("Account"));
		//acc.click();
		
		WebElement home = new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.linkText("Home")));
		home.click();
	
//		try {
//			
//			new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("id('sample-menu-1')/x:li[4]/x:a")));
//			
//		}
//		catch(Exception ex)
//		{
//			logger.error("load home page failed");
//		}
	}
	
	public void TrendReport ()
	{
		WebElement OnDemandInsight = new WebDriverWait(driver,10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='sample-menu-1']/li[5]/a")));
		actionClick(OnDemandInsight);
		
		
		WebElement Reports = new WebDriverWait(driver,10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='sample-menu-1']/li[5]/ul/li[2]/a")));
		actionClick(Reports);
		
		
		WebElement CallStatisticsTrendReport = new WebDriverWait(driver,10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='sample-menu-1']/li[5]/ul/li[2]/ul/li[2]/a")));
		actionClick(CallStatisticsTrendReport);
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		String mainWinhandler = driver.getWindowHandle();
		
		Set<String> handles = driver.getWindowHandles();
		
		for (String handle : handles){
			if (!mainWinhandler.equals(handle))
			{
				WebDriver popup  = driver.switchTo().window(handle);
				popup.close();
			}
		}
		
		
	//	driver.switchTo().activeElement();
		
	}
	
	public void actionClick (WebElement element)
	{
		Actions mover = new Actions(driver);
		mover.moveToElement(element).click(element).perform();
	}
	
	public static void main(String[] args)
	{
		//WebElement home = mm.driver.findElement(By.xpath(homePath));
		//home.submit();
	}
	
}
