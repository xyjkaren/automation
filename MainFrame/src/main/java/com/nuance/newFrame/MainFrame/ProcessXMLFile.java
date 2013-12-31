package com.nuance.newFrame.MainFrame;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import static java.lang.System.err;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
 
public class ProcessXMLFile {
	public static Logger logger = LoggerFactory.getLogger(Main.class);
 

	public void readXml(String xmlFile){
    try 
    {
    	String mname = null;
    	NodeList tLists = null;
    	NodeList quitBrowser= null;
    	NodeList homePage = null;
    	Class<?> noparams[] = {};
    	
		File fXmlFile = new File(xmlFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
	 	logger.info("Root element: " + doc.getDocumentElement().getNodeName());//company
	 	NodeList nList = doc.getElementsByTagName("classname");
	 	NodeList home =doc.getElementsByTagName("homepage");
	 	 	
	 		Element homeElement= (Element) home.item(0);
	 		homePage = homeElement.getElementsByTagName("mainpage");
	 		quitBrowser = homeElement.getElementsByTagName("quitbrowser");
	 	try{
		 	//This for loop executes depending on the number of the classnames in the xml file
		 	for (int temp = 0; temp < nList.getLength(); temp++) 
		 	{
		 		Node nNode = nList.item(temp);
				String className=nNode.getAttributes().getNamedItem("class").getNodeValue();
				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					Element eElement = (Element) nNode;
					tLists = eElement.getElementsByTagName("testname");
					
				}
				
					Class<?> cls = Class.forName(className); //name is the id attribute which i have to extract from xml file
					Object obj = cls.newInstance();
						for (int j = 0; j < tLists.getLength(); j++)
					    {
							try
							{
					        Element node = (Element) tLists.item(j);
					        logger.info("Current Method: "+node.getFirstChild().getNodeValue()+ "\nCurrent Class:"+className);
					        Method method = cls.getDeclaredMethod(node.getFirstChild().getNodeValue(), noparams);
					        mname = method.getName();
					        method.invoke(obj);
							}catch(InvocationTargetException e)
							{
								Throwable cause = e.getCause();
								err.format("Invocation of %s  failed: %s%n",mname, cause.getMessage());
							}
							finally
							{
								String returnToMainpage= homeElement.getAttributes().getNamedItem("class").getNodeValue();
								Class<?> mainpage = Class.forName(returnToMainpage);
								Object gotomain = mainpage.newInstance();
								for(int k=0; k<homePage.getLength();k++)
								{
									Element homeNode = (Element) homePage.item(k);
								String mainMethod = homeNode.getFirstChild().getNodeValue();
								logger.info("Current Method: "+ mainMethod+ "\n in Current Class: "+returnToMainpage);
								Method defaultRun = mainpage.getDeclaredMethod(mainMethod, noparams);
								defaultRun.invoke(gotomain);
								}
							}
				    }
				
		 		}	
		 	}catch(Exception e)
			{
				logger.info("Exception trace:" + e);
			}finally
			{
		 		String quitSession = homeElement.getAttributes().getNamedItem("class").getNodeValue();
		 		logger.info("Current class: "+ quitSession);
		 		Class<?> session = Class.forName(quitSession);
		 		Object exit = session.newInstance();
		 		for(int k=0; k<quitBrowser.getLength();k++)
				{
					Element node2 = (Element) quitBrowser.item(k);
					String quitMethod = node2.getFirstChild().getNodeValue();
					logger.info(quitMethod);
					Method defaultRun = session.getDeclaredMethod(quitMethod, noparams);
					defaultRun.invoke(exit);
				}
		
			}	 	
	}catch (Exception e) 
    {
    	logger.info("Exception trace" + e);
    }

  }
}