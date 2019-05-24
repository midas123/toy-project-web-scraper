package com.yk.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.yk.web.dto.ItemRequestDto;

@Service
public class ItemService {
	public List<HashMap<String, String>> getArticle(ItemRequestDto dto) {
		String categoryLinks = getCategoryLink(dto.getItem_link());
		
		return getArticles(categoryLinks, dto.getKeyword());
	}
	
	private String getCategoryLink(String url) {
		try {
			Document document = Jsoup.connect(url).get();
			Elements categoryElement = document.select("a[href^=\"/tutorials/java-8-tutorials/\"]");
			return categoryElement.attr("abs:href");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	private List<HashMap<String, String>> getArticles(String categoryLinks, String keyword){
		try {
			Document document = Jsoup.connect(categoryLinks).get();
			Elements elements = document.select("li a[href^=\"http://www.mkyong.com/\"]");
			return searchArticle(elements, keyword);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	private List<HashMap<String, String>> searchArticle(Elements elements, String keyword){
		List<HashMap<String, String>> searchResult = new ArrayList<>();
		String regex = writeRegExp(keyword);
		
		for(Element element : elements) {
			if(element.text().matches(regex)) { 
				HashMap<String, String> article = new HashMap<>();
				article.put("subject", element.text());
				article.put("link", element.attr("abs:href"));
				searchResult.add(article);
			}
		}
		return searchResult;
	}
	
	private String writeRegExp(String keyword) {
		if(keyword.contains(" ")) {
			String[] splitStr = keyword.trim().split("\\s+");

			StringBuilder strb = new StringBuilder();
			for(int i=0; i<splitStr.length; i++) {
				strb.append(".*(?i)");
				strb.append(splitStr[i]);
				strb.append(".*");
			}
			return strb.toString();
			
		} else {
			return ".*(?i)"+keyword+".*";
		}
		
	}
	
}
