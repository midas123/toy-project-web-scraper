package com.yk.web;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;

import org.jsoup.select.Elements;

import com.yk.web.dto.ItemRequestDto;

public abstract class WebScraper {
	abstract List<String> setLinks();
	
	abstract Elements getCategoryNameAndLink(List<String> links);
	
	abstract List<ItemRequestDto> getArticleTitleAndLink(Elements categoryLinks);
	
	abstract String tokenizer(String link) throws MalformedURLException;
	
	//abstract String scrapArticleContents();
	
	public String[] writeRegExp(String keyword) {
		if(keyword.contains(" ")) {
			String[] splitStr = keyword.trim().split("\\s+");
			String[] regKeyword = new String[splitStr.length];
			
			for(int i=0; i<splitStr.length; i++) {
				StringBuilder strb = new StringBuilder();
				strb.append(".*(?i)");
				strb.append(splitStr[i]);
				strb.append(".*");
				regKeyword[i] = strb.toString();
			}
			return regKeyword;
			
		} else {
			return new String[] {".*(?i)"+keyword+".*"};
		}
	}
	
	public void writeToFile(List<HashMap<String, String>> articles) {
		FileWriter writer;
			try {
				//String time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
				writer = new FileWriter("Titles");
				articles.forEach(a -> {
				
				String temp = "title:" + a.get("title");
				try {
					writer.write(temp);
				} catch (IOException e) {
					e.printStackTrace();
				}
				});
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
