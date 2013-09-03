package org.yujie.oditest.cssr;

import java.awt.Window;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class WriteXmlFile {			
	
		
	private	DocumentBuilderFactory factory;
	private	DocumentBuilder  builder ;
	private	Document doc;
	private Element rootElement;
	private Element Title;
	public static Logger logger = LoggerFactory.getLogger(Main.class);

	
	public WriteXmlFile()
	{
		try
		{
		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		doc  = builder.newDocument();
		
		rootElement = doc.createElement("REPORT");
		doc.appendChild(rootElement);
		
		Title = doc.createElement("TITLE");
		Title.appendChild(doc.createTextNode("CSTR Test Results"));
		rootElement.appendChild(Title);
		}
		catch(ParserConfigurationException ex)
		{
			ex.printStackTrace();
		}
	}
		
	public void addTestCase(String Testcase, boolean test)
	{
		logger.info("put string "+Testcase +"\n");
		Element TestCase = doc.createElement("SECT");
		TestCase.appendChild(doc.createTextNode(Testcase));
		rootElement.appendChild(TestCase);
		String result = "";
		
		if (test == true)
		{
			result = Testcase + "result: passed";
		}
		else
		{
			result = Testcase + "result: Failed";
		}
		
		Element Param = doc.createElement("PARA");
		Param.appendChild(doc.createTextNode(result));
		TestCase.appendChild(Param);
	}
	
	public void WriteToFile()
	{
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
		
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(System.getProperty("user.dir") + "\\reports\\Report.xml"));
		transformer.transform(source, result);
		
	} catch (TransformerConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (TransformerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}	
		
	public static void main(String[] args)
	{
		WriteXmlFile w = new WriteXmlFile();
		w.addTestCase("this is test 001", true);
		w.WriteToFile();
	}
	
}
