package com.nuance.newFrame.MainFrame;



/**
 * Hello world!
 *
 */
public class App 
{
	public App(){
	System.out.println("pass login fuction");
	}

    public static void main( String[] args )
    {
    	String pathToXml = args[0];
    	System.out.println(pathToXml);
        ProcessXMLFile process = new ProcessXMLFile();
    	process.readXml(pathToXml);
	    
    	GenerateHtml generate=new GenerateHtml();
	    generate.execute();
	       /*give this C:"\"uttam\reports.html*/
    }

}
