package edu.wpi.cs.basic.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetChoiceHandlerTest {
    @Test
    public void testGetChoiceHandler() throws Exception {
    	GetChoiceHandler handler = new GetChoiceHandler();
    	String testID = "choiceID";
    	handler.getChoice(testID);
    	
    	
    	
        // TODO: validate output here if needed.
        
    }
}