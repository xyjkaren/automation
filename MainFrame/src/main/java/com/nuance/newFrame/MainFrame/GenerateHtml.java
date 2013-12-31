package com.nuance.newFrame.MainFrame;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;




public class GenerateHtml {

	
	public void execute()
	{
		 String fileloc = System.getProperty("user.dir");
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		 
		//File stylesheet = new File(fileloc+"\\src\\main\\resources\\ReportFormat.xsl");
		 InputStream stylesheet = getClass().getResourceAsStream("/ReportFormat.xsl");
		 File datafile = new File(fileloc+"\\Report.xml");
		
		try{
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(datafile);
		TransformerFactory tFactory = TransformerFactory.newInstance();
		StreamSource stylesource = new StreamSource(stylesheet);
		Transformer transformer = tFactory.newTransformer(stylesource);
		DOMSource source = new DOMSource(document);
		
		StreamResult result = new StreamResult(new FileOutputStream("report.html"));
		
		transformer.transform(source, result);
		
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String []args){
		GenerateHtml x = new GenerateHtml();
		x.execute();
	}

}
