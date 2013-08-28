package org.ODI.IvrCallRep;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class userInfo {

	private Properties properties = new Properties();
	
	public userInfo (String userPropath) throws IOException
	{
		InputStream is = new FileInputStream(userPropath);
		try {
				String[] usertype = userPropath.split("\\.");
				String fileExt = usertype[1];
				
				if (fileExt.equals("xml"))
				{
					properties.loadFromXML(is);
				}
				else{
					properties.load(is);
				}
		}
		catch(FileNotFoundException ex)
		{
			ex.printStackTrace();
		}
		finally{
			if (is != null)
			{
				is.close();
			}
		}
	}
	
	public String getProperties(String key)
	{
		return this.properties.getProperty(key);
	}

	public static void main(String[] args) throws IOException{
	 userInfo user = new userInfo("userinfo.xml");
      System.out.println(user.getProperties("username"));
		
	}
}
