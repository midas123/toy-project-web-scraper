package com.yk.web.service;

import java.io.IOException;
import java.util.ArrayList;
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
	
	
	public List<HashMap<String, String>> getArticles(ItemRequestDto dto) {
		String[] RegKeywords = writeRegExp(dto.getKeyword());
		List<String> links = setLinks();
		List<Element> categoryLinks = getCategoryLink(links, RegKeywords);
		return getArticles(categoryLinks, RegKeywords);
	}
	
	private List<String> setLinks(){
		List<String> links = new ArrayList<>();
		links.add("https://howtodoinjava.com");
		links.add("https://www.programcreek.com");
		return links;
	}
	
	private List<Element> getCategoryLink(List<String> links, String[] RegKeywords) {
		List<Element> categoryLinks = new ArrayList<>();
		for(String s: links) {
			try {
				Document document = Jsoup.connect(s).get();
				//Elements categoryElement = document.select("a[href^=\"/tutorials/java-8-tutorials/\"]");
				Elements categoryElement = document.select("a[href]");
				for(Element e: categoryElement) {
					if(e.text().matches(RegKeywords[0]))
						categoryLinks.add(e);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
		}
		return categoryLinks;
	}
	
	private List<HashMap<String, String>> getArticles(List<Element> categoryLinks, String[] keyword){
		List<HashMap<String, String>> articles = new ArrayList<>();
		
		for(Element el: categoryLinks) {
			try {
				Document document = Jsoup.connect(el.attr("abs:href")).get();
				//Elements elements = document.select("li a[href^=\"http://www.mkyong.com/\"]");
				Elements elements = document.select("a[href]");
				for(Element element : elements) {
					if(element.text().matches((keyword.length ==2) ? keyword[1]:keyword[0])) {
						HashMap<String, String> article = new HashMap<>();
						article.put("subject", element.text());
						article.put("link", element.attr("abs:href"));
						articles.add(article);
					}
						
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
		}
		System.out.println("articles:"+articles);
		return articles;
	}
	
	private String[] writeRegExp(String keyword) {
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
	
	
	
/*	private List<HashMap<String, String>> searchArticle(Elements elements, String keyword){
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
	}*/
	

/*	private String writeRegExp(String keyword) {
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
	}*/
	
}
