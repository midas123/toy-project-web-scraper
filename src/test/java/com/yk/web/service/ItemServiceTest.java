package com.yk.web.service;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

public class ItemServiceTest {
	@Rule
	public OutputCapture outputCapture = new OutputCapture();
	
	
	@Test
	public void testSearchArticle() {
		String keyword = "   java- hello% exception^";
		keyword = keyword.trim().replaceAll("[^a-zA-Z0-9]", " ");
	    String[] keys = keyword.split(" ");
	    System.out.println(keyword); //javahello
	    System.out.println(Arrays.toString(keys)); //[java, , hello]
	    
	    for(int i=0; i<keys.length; i++) {
	    	if(keys[i].equals("")) {
	    		System.out.println("whitespace");
	    	}else {
	    		System.out.println("keyword");
	    	}
	    }
	    
	    String token = "java,exception";
	    System.out.println(token.contains("java"));
		
	}

}
