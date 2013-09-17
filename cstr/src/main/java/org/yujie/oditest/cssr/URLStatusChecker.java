package org.yujie.oditest.cssr;

import java.io.IOException;
import java.net.URI;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.net.UrlChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLStatusChecker {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	private URI linkToCheck;
	private WebDriver driver;
	private boolean mimicWebDriverCookieState = true;
	private boolean followState = false;
	private RequestMethod httpRequestMethod = RequestMethod.GET;
	
	public URLStatusChecker (WebDriver driverObject) throws MalformedURLException, URISyntaxException {
		this.driver  = driverObject;
	}
	
	public void setURIToCheck(String linkToceck) throws MalformedURLException,URISyntaxException
	{
		this.linkToCheck = new URI(linkToceck);
	}
	
	public void setURIToCheck(URL linkToceck) throws MalformedURLException,URISyntaxException
	{
		this.linkToCheck = linkToceck.toURI();
	}
	
	public void setHttpRequestMethod(RequestMethod requestmethod)
	{
		this.httpRequestMethod = requestmethod;
	}
	
	public void followRedirects(Boolean value)
	{
		this.followState = value;
	}
	
	
	public int getHttpStatusCode() throws IOException{
		
		HttpClient client = new DefaultHttpClient();
		BasicHttpContext localcontext = new BasicHttpContext();
		
		logger.info("Mimic driver cookie" + this.mimicWebDriverCookieState);
		if (this.mimicWebDriverCookieState) {
			localcontext.setAttribute(ClientContext.COOKIE_STORE, mimicCookieState(this.driver.manage().getCookies()));
		}
		
		HttpRequestBase requestmethod = this.httpRequestMethod.getRequestMethod();
		requestmethod.setURI(this.linkToCheck);
		
		HttpParams httpRequestParameters = requestmethod.getParams();
		httpRequestParameters.setParameter(ClientPNames.HANDLE_REDIRECTS, this.followState);
		requestmethod.setParams(httpRequestParameters);
		
		logger.info("sending " + requestmethod.getMethod() + " request for: " + requestmethod.getParams());
		HttpResponse reponse = client.execute(requestmethod,localcontext);
		
		return reponse.getStatusLine().getStatusCode();
		
		
	}
	
	
	public BasicCookieStore mimicCookieState(Set<Cookie> set)
	{
		BasicCookieStore mimicWebDriverCookieStore = new BasicCookieStore();
		for (Cookie seleniumCookie : set)
		{
			BasicClientCookie duplicateCookie = new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
			duplicateCookie.setDomain(seleniumCookie.getDomain());
			duplicateCookie.setSecure(seleniumCookie.isSecure());
			duplicateCookie.setExpiryDate(seleniumCookie.getExpiry());
			duplicateCookie.setPath(seleniumCookie.getPath());
			mimicWebDriverCookieStore.addCookie(duplicateCookie);
			
		}
		
		return mimicWebDriverCookieStore;
	}
	
	
	public void mimicWebDriverCookieState(boolean value) {
		this.mimicWebDriverCookieState = value;
	}
	

	public static void main(String[] args)
	{
		WebDriver driver = new FirefoxDriver();
		try {
			URLStatusChecker url = new URLStatusChecker(driver);
			url.setURIToCheck("wwww.google.com");
			url.setHttpRequestMethod(RequestMethod.GET);
			logger.info(""+url.getHttpStatusCode());
		
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
