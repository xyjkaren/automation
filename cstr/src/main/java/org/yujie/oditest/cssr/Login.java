package org.yujie.oditest.cssr;

import java.io.IOException;
import java.util.NoSuchElementException;

import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Login extends mainpage{

	private static String username = null;
	private static String password = null;
	private static final String userNameXPath = "id('username')";
	private static final String passwordXPath = "//*[@id='password']";
	private static final String submitXPath = "//*[@id='login']/table/tbody/tr[4]/td[2]/div/input[3]";
	public String loginpageUrl;
	
	public Login()
	{
		super.setDriver();
		logger.info("pass login fuction");
	}
	
	public void execute ()
	{ 
		geturl(webUrl);
		getuserinfo();
		//enter username
		WebElement userinput = driver.findElement(By.xpath(userNameXPath));
		WebElement pswdinput = driver.findElement(By.xpath(passwordXPath));
		WebElement submit = driver.findElement(By.xpath(submitXPath));
		
		userinput.sendKeys(username);
		pswdinput.sendKeys(password);
		submit.submit();
		validlogin();
		choosedomain();
	}
	
	public void choosedomain()
	{
		WebElement domainlist = driver.findElement(By.id("dropdown"));
		Select select = new Select(domainlist);
		select.selectByValue("US_AIRWAYS");
	}
	public void geturl (String url){
		driver.get(url);
	}
	
	public void getuserinfo() 
	{		
		username = user.getProperties("username");		
		password = user.getProperties("password");
		
		if (username ==null || username.isEmpty())
		{
			throw new NullPointerException("null username found");
		}
		else if (password == null|| password.isEmpty())
		{
			throw new NullPointerException("null password found");
		}
		
		
	}
	
	public void validlogin()
	{
		try {
			
			new WebDriverWait(driver, 10).until(ExpectedConditions.titleIs("Nuance Client Portal"));
			
		}
		catch(Exception ex)
		{
			gotomainpage();
		//	logger.error("login failed");
			throw new NoSuchElementException("invalid username or password");
		}
	}

	public static void main(String[] args)
	{
		Login logintest = new Login();
		logintest.execute();
	}
	
}
