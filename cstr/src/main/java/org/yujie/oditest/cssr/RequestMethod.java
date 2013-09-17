package org.yujie.oditest.cssr;
import org.apache.http.client.methods.*;

public enum RequestMethod {
	OPTIONS(new HttpOptions()),
	GET(new HttpGet()),
	HEAD(new HttpHead()),
	POST(new HttpPost()),
	PUT(new HttpPut()),
	DELETE(new HttpDelete()),
	Trace(new HttpTrace());
	
	private final HttpRequestBase requestMethod;
	
	RequestMethod(HttpRequestBase requestbase) {
		this.requestMethod = requestbase;
		// TODO Auto-generated constructor stub
	}
	
	public HttpRequestBase getRequestMethod()
	{
		return this.requestMethod;
	}
	
	
}
