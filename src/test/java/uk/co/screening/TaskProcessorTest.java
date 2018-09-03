package uk.co.screening;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TaskProcessorTest {
	@Before
    public void setup(){
    }

    @After
    public void tearDown(){

    }

    @Test
    public void shoudVerifyProcess_passed_1(){
    	String resp = TaskProcessor.process(null, null);
    	assertNull(resp);
    }

    @Test
    public void shoudVerifyProcess_passed_2(){
    	String task = "a,b";
    	String resp = TaskProcessor.process(task, null);
    	assertNotNull(resp);
    	assertEquals(task, resp);
    }
    
    @Test
    public void shoudVerifyProcess_passed_3(){
    	String task = "a,b,c,d";
    	String dependencies = "a => b,c => d";
    	String resp = TaskProcessor.process(task, dependencies);
    	assertNotNull(resp);
    	assertEquals("b,a,d,c", resp);
    }

    @Test
    public void shoudVerifyProcess_passed_4(){
    	String task = "a,b,c";
    	String dependencies = "a => b,b => c";
    	String resp = TaskProcessor.process(task, dependencies);
    	assertNotNull(resp);
    	assertEquals("c,b,a", resp);
    }
    
    @Test
    public void shoudVerifyProcess_passed_5(){
    	String task = "a,b,c,d,e,f";
    	String dependencies = "a => b,b => c,d => e,e => f";
    	String resp = TaskProcessor.process(task, dependencies);
    	assertNotNull(resp);
    	assertEquals("c,b,a,f,e,d", resp);
    }
    
    @Test
    public void shoudVerifyProcess_processed_6(){
    	String task = "a,b,e,d,c,f";
    	String dependencies = "a => b,b => c, d => f, c => e";
    	String resp = TaskProcessor.process(task, dependencies);
    	assertNotNull(resp);
    	assertEquals("e,c,b,a,f,d", resp);
    }
	
    @Test
    public void shoudVerifyProcess_error_1(){
    	String task = "a,b,c,d";
    	String dependencies = "a => b,b => c,c => a";
    	String resp = TaskProcessor.process(task, dependencies);
    	assertNotNull(resp);
    	assertEquals("Error - this is a cyclic dependency", resp);
    }
    
    @Test
    public void shoudVerifyProcess_error_2(){
    	String task = "a,b";
    	String dependencies = "a => b,b => a";
    	String resp = TaskProcessor.process(task, dependencies);
    	assertNotNull(resp);
    	assertEquals("Error - this is a cyclic dependency", resp);
    }
    
    @Test
    public void shoudVerifyProcess_error_3(){
    	String task = "a,b,e,d,c,f";
    	String dependencies = "a => b,b => c,c => a";
    	String resp = TaskProcessor.process(task, dependencies);
    	assertNotNull(resp);
    	assertEquals("Error - this is a cyclic dependency", resp);
    }
    
    @Test
    public void shoudVerifyProcess_error_4(){
    	String task = "a,b,e,d,c,f";
    	String dependencies = "a => b,b => c,d => f,c => a";
    	String resp = TaskProcessor.process(task, dependencies);
    	assertNotNull(resp);
    	assertEquals("Error - this is a cyclic dependency", resp);
    }
}
