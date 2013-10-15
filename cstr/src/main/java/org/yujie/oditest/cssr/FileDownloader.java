package org.yujie.oditest.cssr;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.tree.ExpandVetoException;

import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.xalan.trace.TraceManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.DeadEvent;

public class FileDownloader extends Mainpage{
	
	public static Logger logger = LoggerFactory.getLogger(Main.class);
	private String localDownloadPath = System.getProperty("java.io.tmpfir");
	private boolean followDirects = true;
	private boolean mimicWebDriverCookieState = true;
	private int httpStatusOfLastDownloadAttmpt = 0;
	
	public FileDownloader() {
	
	}
	
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
		return downloader("href");
	}
	
	public String downloadImg (WebElement element) throws Exception {
		return downloader("src");
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
	
	
	public static HttpClient wrapClient(HttpClient base) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				
				public X509Certificate[] getAcceptedIssuers() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public void checkServerTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
					// TODO Auto-generated method stub
					
				}
				
				public void checkClientTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
					// TODO Auto-generated method stub
					
				}
			};
			
			ctx.init(null,new TrustManager[]{tm},null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = base.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https",ssf, 443));
			return new DefaultHttpClient(ccm,base.getParams());
		}catch(Exception ex) {
			ex.printStackTrace();return null;
		}
	}
	
	public String downloader(String attribute) throws IOException, NullPointerException, URISyntaxException {
		String fileToDownloadLocation = attribute;
		
		
		
		if (fileToDownloadLocation.trim().equals("")) throw new NullPointerException("The element you specified does not link to anything");
		
		URL fileToDownload = new URL(fileToDownloadLocation);
		File downloadedFile = new File(this.localDownloadPath + fileToDownload.getFile().replaceFirst("/|\\\\", ""));
		if (downloadedFile.canWrite() == false) downloadedFile.setWritable(true);
		
		HttpClient httpclient = new DefaultHttpClient();
		httpclient  = wrapClient(httpclient);
		BasicHttpContext localcontext = new BasicHttpContext();
		
		logger.info("Mimic WebDriver Cookie state: " + this.mimicWebDriverCookieState);
		
		if (this.mimicWebDriverCookieState) {
			localcontext.setAttribute(ClientContext.COOKIE_STORE,mimicCookieState(driver.manage().getCookies()));
		}
		
		HttpPost httppost = new HttpPost(fileToDownload.toURI());
		HttpResponse response = httpclient.execute(httppost);
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String downloadabsolutepath = "";
		while((downloadabsolutepath = rd.readLine()) !=null) {
			System.out.print(downloadabsolutepath)	;
		}
		
		
//		HttpGet httpget = new HttpGet(fileToDownload.toURI());
//		HttpParams httpRequestParameters  = httpget.getParams();
//		httpRequestParameters.setParameter(ClientPNames.HANDLE_REDIRECTS	, this.followDirects);
//		httpget.setParams(httpRequestParameters);
//		
//		logger.info("send http request for " + httpget.getURI());
//		HttpResponse response = httpclient.execute(httpget,localcontext);
//		
//		this.httpStatusOfLastDownloadAttmpt = response.getStatusLine().getStatusCode();
//		logger.info("downloading file " + downloadedFile.getName());
//		FileUtils.copyInputStreamToFile(response.getEntity().getContent(), downloadedFile);
//		response.getEntity().getContent().close();
//		
//		String downloadabsolutepath = downloadedFile.getAbsolutePath();
//		
//		logger.info("the file is saved in " + downloadabsolutepath);
//		
		return downloadabsolutepath;
		
	}
	
	
	public static void main(String[] args)
	{

//		FileDownloader test = new FileDownloader();
//		WebElement downlink = driver.findElement(By.tagName("a"));
//	
//		try {
//			String downloadedFileAbsoluteLocation = test.downloadFile(downlink);
//			logger.info(downloadedFileAbsoluteLocation);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	 
	}

}





