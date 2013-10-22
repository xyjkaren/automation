package org.yujie.oditest.cssr;

import java.io.IOException;
import java.util.NoSuchElementException;

import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Login extends Mainpage{

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
		JavascriptExecutor js = (JavascriptExecutor) driver;
		geturl(webUrl);
		getuserinfo();
		//enter username
		WebElement userinput = driver.findElement(By.xpath(userNameXPath));
		WebElement pswdinput = driver.findElement(By.xpath(passwordXPath));
		WebElement submit = driver.findElement(By.xpath(submitXPath));
		

		try{
			
//			Object response = ((JavascriptExecutor) driver).executeScript(" var xhr = null;" +
//			      "if (window.XMLHttpRequest) {xhr = new XMLHttpRequest();}" +
//			       "else if (window.ActiveXObject) {xhr = new ActiveXObject(\"Microsoft.XMLHTTP\"); }" +
//			       "xhr.open('POST', '/https://sun-qa-ncp03clone.engca.bevocal.com:8443/np/odiAdvancedReporting/TestSSOSession.jsp', true);" +			   
//			 //      "xhr.open('GET','/',true);" +
//					"xhr.onreadystatechange = function() {" +				
//					"if (xhr.readyState == 4) {if (xhr.status == 200) {" +
//					"var response = xhr.responseText;" +
//					"if(response == 'TestSSOSection') {" +
//					"var currentTime = new Date;" +
//					"var yyyy = currentTime.getFullYear().toString(10);" +
//					"var mm = formatNumber(currentTime.getMonth()+1);" +
//					"var dd = formatNumber(currentTime.getDate());" +
//					"var HH = formatNumber(currentTime.getHours());" +
//					"var MM = formatNumber(currentTime.getMinutes());" +
//					"var SS = formatNumber(currentTime.getSeconds());" +
//					"var exportTime = yyyy + mm + dd + HH + MM + SS;" +
//					"if (exportFormat == 'pdf') {" +
//					"	window.location = 'ExportPDF.jsp?rand=SNCI-2ICG&exportTime='+exportTime;} " +
//					"else if (exportFormat == 'excel') {" +
//					"	window.location = 'ExportExcel.jsp?rand=SNCI-2ICG&exportTime='+exportTime;}" +
//					"else if (exportFormat == 'csv') {" +
//					"	window.location = 'ExportCSV.jsp?rand=SNCI-2ICG&exportTime='+exportTime; }" +
//					"else if(exportFormat == 'cr') {" +
//		            "    window.location = 'ExportCrystalReport.jsp?rand=SNCI-2ICG&exportTime='+exportTime; } }" +
//					"else {window.location = welcomPage;} }" +
//					"else {window.location = welcomPage;}}};" +
//				//	"window.location='/np/welcome/index.jsp'};" +    // typo here
//			       "xhr.send(null);");
			
//			Object response = ((JavascriptExecutor) driver).executeScript("function executeExporter() {" +
//					"var currentTime = new Date;" +
//					"var yyyy = currentTime.getFullYear().toString(10);" +
//					"var mm = formatNumber(currentTime.getMonth()+1);" +
//					"var dd = formatNumber(currentTime.getDate());" +
//					"var HH = formatNumber(currentTime.getHours());" +
//					"var MM = formatNumber(currentTime.getMinutes());" +
//					"var SS = formatNumber(currentTime.getSeconds());" +
//					"var exportTime = yyyy + mm + dd + HH + MM + SS;" +
//					"if (exportFormat == 'pdf') {" +
//					"	window.location = 'ExportPDF.jsp?rand=SNCI-2ICG&exportTime='+exportTime;} " +
//					"else if (exportFormat == 'excel') {" +
//					"	window.location = 'ExportExcel.jsp?rand=SNCI-2ICG&exportTime='+exportTime;}" +
//					"else if (exportFormat == 'csv') {" +
//					"	window.location = 'ExportCSV.jsp?rand=SNCI-2ICG&exportTime='+exportTime; }" +
//					"else if(exportFormat == 'cr') {" +
//		            "    window.location = 'ExportCrystalReport.jsp?rand=SNCI-2ICG&exportTime='+exportTime; } }" +
//		            "executeExporter;" +
//		            "");
			
			// ((JavascriptExecutor) driver).executeScript("function executeExport1(){return 'haha'} executeExport1;");

					
		//js.executeScript("req.send(null);");
		//					"req.onreadystatechange = resumeExport;" +
	//						"req.send(null); }" );
	}catch(Exception e)
	{System.err.print("error");
		driver.quit();
	}
		userinput.sendKeys(username);
		pswdinput.sendKeys(password);
		submit.submit();
		validlogin();
		choosedomain();
		
	//	driver.quit();
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
