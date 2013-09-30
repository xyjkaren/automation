package org.yujie.oditest.cssr;

import static org.junit.Assert.*;

import org.junit.Test;

public class CSTRtest {

	@Test
	public void test() {
		Login logintest = new Login();
	       logintest.execute();
	       Timestep selecttest = new Timestep();
	       selecttest.quarterSelectionSorting();
	       GenerateHtml FinalRep = new GenerateHtml();
	       FinalRep.execute();
	       
	}

}
