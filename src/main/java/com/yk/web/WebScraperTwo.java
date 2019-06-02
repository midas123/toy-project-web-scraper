package com.yk.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.yk.web.dto.ItemRequestDto;

public class WebScraperTwo extends WebScraper{

	@Override
	public List<String> setLinks() {
		List<String> links = new ArrayList<String>();
		links.add("https://www.programcreek.com/");
		return links;
	}

	@Override
	public Elements getCategoryNameAndLink(List<String> links) {
		Elements categoryElements = new Elements();
		try {
			Document document = Jsoup.connect(links.get(0)).get();
			categoryElements = document.select("li a[href~=/[(simple-java)]]");
		} catch(IOException e) {
			e.printStackTrace();
		}
		return categoryElements;
	}

	@Override
	public List<ItemRequestDto> getArticleTitleAndLink(Elements categoryLinks) {
		Set<ItemRequestDto> articles = new HashSet<>();
		Elements elements = new Elements();
		
		try {
			Document document = Jsoup.connect(categoryLinks.get(0).attr("abs:href")).get();
			elements = document.select("li a[href~=(programcreek)]");
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		for(Element element : elements) {
			ItemRequestDto item = new ItemRequestDto();
			String token = null;
			try {
				token = tokenizer(element.attr("abs:href"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
			item.setItemTitle(element.text());
			item.setItemLink(element.attr("abs:href"));
			item.setTokens(token);
			
			if(token != null) {
				articles.add(item);
			}	
		}
		
		return new ArrayList<ItemRequestDto>(articles);
	}

	@Override
	public String tokenizer(String link) throws MalformedURLException {
		String path = new URL(link).getPath();
		String token = "";
		if(path.length()>0) {
			String title = path.replaceAll("[0-9]", "").replaceAll("/", "");
			token = title.replaceAll("-", ",");
			
		/*	String[] tokens = title.split("-");
			
			Set<String> set = new HashSet<>();
			for(String str: tokens) {
				set.add(str);
			}
			
			tokens = set.toArray(new String[set.size()]);
			token = String.join(",", tokens);*/
		}
		return token;
	}
	
}
