package com.yk.web.service;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yk.web.dao.ItemIndexRepository;
import com.yk.web.dao.ItemRepository;
import com.yk.web.dto.ItemRequestDto;
import com.yk.web.entity.ItemIndexes;
import com.yk.web.entity.Items;

@Service
public class WebScrapService {
	
	@Autowired
	private ItemIndexRepository itemIndexRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	public HashMap<String, Long> scrapArticles(ItemRequestDto dto) {
		List<String> links = setLinks();
		Elements categoryLinks = getCategoryNameAndLink(links);
		List<ItemRequestDto> items = getArticleTitleAndLink(categoryLinks);
		
		for(int j=0; j<items.size(); j++) {
			Items item = itemRepository.save(items.get(j).toItemEntity());
			long item_id = item.getItemId();
			
			itemIndexRepository.save(ItemIndexes.builder()
					.item(new Items(item_id))
					.tokens(items.get(j).getTokens())
					.build());
		}
		HashMap<String, Long> resultCounts = new HashMap<>();
		resultCounts.put("categoryCounts", (long) categoryLinks.size());
		resultCounts.put("articleCounts", (long) items.size());
		
		return resultCounts;
	}
	
	
	private List<String> setLinks(){
		List<String> links = new ArrayList<>();
		links.add("https://howtodoinjava.com");
		//links.add("https://www.programcreek.com");
		return links;
	}
	
	private Elements getCategoryNameAndLink(List<String> links) {
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
	
	private List<ItemRequestDto> getArticleTitleAndLink(Elements categoryLinks){
		Set<ItemRequestDto> articles = new HashSet<>();
		Elements elements = new Elements();
		for(int i=0; i<categoryLinks.size()/4; i++) {
		//for(Element el: categoryLinks) {
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
	
	public String tokenizer(String link) throws MalformedURLException {
		String path = new URL(link).getPath();
		String token ="";
		if(path.length()>0) {
			String title = link.substring(link.indexOf("/", 9), link.length()-1);
			title = title.replaceFirst("/", "").replaceAll("/", "-");
			String[] tokens = title.trim().split("-");
			
			Set<String> set = new LinkedHashSet <>(); //remove duplicate in order
			for(String str: tokens) {
				set.add(str);
			}
			
			tokens = set.toArray(new String[set.size()]);
			token = String.join(",", tokens);
		}
		return token;
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
	
	private void writeToFile(List<HashMap<String, String>> articles) {
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
