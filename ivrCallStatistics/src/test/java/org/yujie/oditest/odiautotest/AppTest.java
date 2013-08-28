package org.yujie.oditest.odiautotest;

import org.ODI.IvrCallRep.ExportPdf;
import org.ODI.IvrCallRep.Login;
import org.openqa.selenium.internal.seleniumemulation.IsAlertPresent;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */

public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
        Login logintest = new Login();
        logintest.execute();
        ExportPdf pdfopen = new ExportPdf();
// export PDF
       
        
        
    }
    

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
