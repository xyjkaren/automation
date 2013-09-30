package org.yujie.oditest.cssr;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

import javax.swing.tree.ExpandVetoException;

import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileDownloader extends Mainpage{
	
	public static Logger logger = LoggerFactory.getLogger(Main.class);
	private String localDownloadPath = System.getProperty("java.io.tmpfir");
	private boolean followDirects = true;
	private boolean mimicWebDriverCookieState = true;
	private int httpStatusOfLastDownloadAttmpt = 0;
	
	public void followRedirectsWhenDownloading(boolean value) {
		this.followDirects = value;
	}
	
	public String loadDownloadPath() {
		return this.localDownloadPath;
	}
	
	public void localDownloadPath(String filepath) {
		this.localDownloadPath = filepath;
	}
	
	public String downloadFile (WebElement element) throws Exception {
		return downloader(element,"href");
	}
	
	public String downloadImg (WebElement element) throws Exception {
		return downloader(element,"src");
	}
	
	public int getHTTPStatusOfLastDownlaodAttmpt() {
		return this.httpStatusOfLastDownloadAttmpt;
	}

	public void mimicWebDriverCookieState(boolean value) {
		this.mimicWebDriverCookieState = value;
	}
	
	private BasicCookieStore mimicCookieState (Set<Cookie> seleniumCookieSet) {
		BasicCookieStore mimicWebDriverCookieStore = new BasicCookieStore();
		for (Cookie seleniumCookie : seleniumCookieSet) {
			BasicClientCookie duplicateCookie = new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
            duplicateCookie.setDomain(seleniumCookie.getDomain());
            duplicateCookie.setSecure(seleniumCookie.isSecure());
            duplicateCookie.setExpiryDate(seleniumCookie.getExpiry());
            duplicateCookie.setPath(seleniumCookie.getPath());
            mimicWebDriverCookieStore.addCookie(duplicateCookie);
		}
		
		return mimicWebDriverCookieStore;
	}
	
	private String downloader(WebElement element, String attribute) throws IOException, NullPointerException, URISyntaxException {
		String fileToDownloadLocation = element.getAttribute(attribute);
		if (fileToDownloadLocation.trim().equals("")) throw new NullPointerException("The element you specified does not link to anything");
		
		URL fileToDownload = new URL(fileToDownloadLocation);
		File downloadedFile = new File(this.localDownloadPath + fileToDownload.getFile().replaceFirst("/|\\\\", ""));
		if (downloadedFile.canWrite() == false) downloadedFile.setWritable(true);
		
		HttpClient httpclient = new DefaultHttpClient();
		BasicHttpContext localcontext = new BasicHttpContext();
		
		logger.info("Mimic WebDriver Cookie state: " + this.mimicWebDriverCookieState);
		
		if (this.mimicWebDriverCookieState) {
			localcontext.setAttribute(ClientContext.COOKIE_STORE,mimicCookieState(driver.manage().getCookies()));
		}
		
		HttpGet httpget = new HttpGet(fileToDownload.toURI());
		HttpParams httpRequestParameters  = httpget.getParams();
		httpRequestParameters.setParameter(ClientPNames.HANDLE_REDIRECTS	, this.followDirects);
		httpget.setParams(httpRequestParameters);
		
		logger.info("send http request for " + httpget.getURI());
		HttpResponse response = httpclient.execute(httpget,localcontext);
		
		this.httpStatusOfLastDownloadAttmpt = response.getStatusLine().getStatusCode();
		logger.info("downloading file " + downloadedFile.getName());
		FileUtils.copyInputStreamToFile(response.getEntity().getContent(), downloadedFile);
		response.getEntity().getContent().close();
		
		String downloadabsolutepath = downloadedFile.getAbsolutePath();
		
		logger.info("the file is saved in " + downloadabsolutepath);
		
		return downloadabsolutepath;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
