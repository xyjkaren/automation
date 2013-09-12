package org.yujie.oditest.cssr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UserInfo {

	private Properties properties = new Properties();
	
	public UserInfo (String userPropath) throws IOException
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
	 UserInfo user = new UserInfo("userinfo.xml");
      System.out.println(user.getProperties("username"));
		
	}
}
