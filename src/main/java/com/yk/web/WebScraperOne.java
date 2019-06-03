package com.yk.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.yk.web.dto.ItemRequestDto;

@Component
public class WebScraperOne extends WebScraper{
	public List<ItemRequestDto> scrapByLink(List<String> links){
		Elements categoryLinks = getCategoryNameAndLink(links);
		return getArticleTitleAndLink(categoryLinks);
	}
	
	@Override
	public List<String> setLinks(String url){
		List<String> links = new ArrayList<>();
		links.add(url);
		return links;
	}
	
	@Override
	public Elements getCategoryNameAndLink(List<String> links) {
		Elements categoryElements = new Elements();
		for(String s: links) {
			try {
				Document document = Jsoup.connect(s).get();
				categoryElements = document.select("li a[href~=\\/(.)[/.*(?i)spring.*|.*(?i)java.*]]");
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
		}
		return categoryElements;
	}
	
	@Override
	public List<ItemRequestDto> getArticleTitleAndLink(Elements categoryLinks){
		Set<ItemRequestDto> articles = new HashSet<>();
		Elements elements = new Elements();
		for(int i=0; i<categoryLinks.size()/4; i++) {
			try {
				Document document = Jsoup.connect(categoryLinks.get(i).attr("abs:href")).get();
				elements = document.select("li a[href~=\\/(.)[/.*(?i)spring.*|.*(?i)java.*]]");
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
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
		String token ="";
		if(path.length()>0) {
			String title = link.substring(link.indexOf("/", 9), link.length()-1);
			title = title.replaceFirst("/", "").replaceAll("/", "-");
			String[] tokens = title.trim().split("-");
			
			Set<String> set = new LinkedHashSet <>(); 
			for(String str: tokens) {
				set.add(str);
			}
			
			tokens = set.toArray(new String[set.size()]);
			token = String.join(",", tokens);
		}
		return token;
	}
}
