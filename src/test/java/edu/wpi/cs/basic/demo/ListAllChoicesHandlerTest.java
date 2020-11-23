package edu.wpi.cs.basic.demo;


import java.io.IOException;


import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.basic.demo.http.AllChoicesResponse;
import edu.wpi.cs.basic.demo.model.Choice;


public class ListAllChoicesHandlerTest extends LambdaTest {
	
    @Test
    public void testGetList() throws IOException {
    	ListAllChoicesHandler handler = new ListAllChoicesHandler();

        AllChoicesResponse resp = handler.handleRequest(null, createContext("list"));
        
        boolean hasE = false;
        for (Choice c : resp.list) {
        	System.out.println("found choice " + c);
        	System.out.println(c.getUniqueID());
        	if (c.getUniqueID().equals("54633563")) { hasE = true; }
        }
        Assert.assertTrue("e Needs to exist in the constants table (from tutorial) for this test case to work.", hasE);
        Assert.assertEquals(200, resp.statusCode);
    }

}