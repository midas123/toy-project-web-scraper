package com.yk.web;


import java.io.IOException;

import org.junit.Test;


public class WebScraperTwoTest {
	
	@Test
	public void testTokenizer() {
		WebScraperTwo web = new WebScraperTwo();
		
		String link = "https://www.programcreek.com/2009/02/diagram-to-show-java-strings-immutability/";
		
		try {
			String result = web.tokenizer(link);
			System.out.println(result);
		} catch(IOException e){
			e.printStackTrace();
		}
		
		
	}

}
