package org.yujie.oditest.cssr;

import org.yujie.oditest.cssr.*;
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
        timestep selecttest = new timestep();
        selecttest.updatedBasedOnTimePeriod();
        generateHtml FinalRep = new generateHtml();
        FinalRep.execute();
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
