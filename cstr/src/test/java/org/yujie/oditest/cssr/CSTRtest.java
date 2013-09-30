package org.yujie.oditest.cssr;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class CSTRtest {

	@Test
	public void test() {
		
		Login logintest = new Login();
	       logintest.execute();
	       Timestep selecttest = new Timestep();
	       selecttest.ReportLayout();
	       GenerateHtml FinalRep = new GenerateHtml();
	       FinalRep.execute();
//	      DateFormat format = new SimpleDateFormat("MMM-yy");
//	      String dd = "Sep-12";
//	      try {
//			Date date = format.parse(dd);
//			System.out.print(date.toString());
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	      
 
	}

}
