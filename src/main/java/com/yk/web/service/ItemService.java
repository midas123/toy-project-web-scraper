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
import java.util.List;

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
public class ItemService {
	
	@Autowired
	private ItemIndexRepository itemIndexRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	public List<HashMap<String, String>> searchArticles(){
		
		return null;
	}
	
	public long scrapArticles(ItemRequestDto dto) {
		//if 7일 경과 또는 items 테이블 데이터X
		//if(itemRepository.findAll())
		//String[] RegKeywords = writeRegExp("java");
		
		List<String> links = setLinks();
		List<Element> categoryLinks = getCategoryNameAndLink(links);
		List<ItemRequestDto> items = getArticleTitleAndLink(categoryLinks);
		
		System.out.println("items.get(0):"+items.get(0));
		Items i = itemRepository.save(items.get(0).toItemEntity());
		long item_id = i.getItem_id();
		
		itemIndexRepository.save(ItemIndexes.builder().item(new Items(item_id)).tokens(items.get(0).getTokens()).build());
		
		return items.size();
	}
	
	
	private List<String> setLinks(){
		List<String> links = new ArrayList<>();
		links.add("https://howtodoinjava.com");
		//links.add("https://www.programcreek.com");
		return links;
	}
	
	private List<Element> getCategoryNameAndLink(List<String> links) {
		List<Element> categoryTitleAndLink = new ArrayList<>();
		for(String s: links) {
			try {
				Document document = Jsoup.connect(s).get();
				Elements categoryElements = document.select("li a[href~=\\/(.)[/.*(?i)spring.*|.*(?i)java.*]]");
				//for(Element e: categoryElement) {
				for(int i=0; i<2; i++) {
						categoryTitleAndLink.add(categoryElements.get(i));
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
		}
		return categoryTitleAndLink;
	}
	
	private List<ItemRequestDto> getArticleTitleAndLink(List<Element> categoryLinks){
		List<ItemRequestDto> articles = new ArrayList<>();
		//List<Items> articles = new ArrayList<>();
		
		for(Element el: categoryLinks) {
			try {
				Document document = Jsoup.connect(el.attr("abs:href")).get();
				Elements elements = document.select("li a[href~=\\/(.)[/.*(?i)spring.*|.*(?i)java.*]]");
				for(Element element : elements) {
					ItemRequestDto item = new ItemRequestDto();
					String token = tokenizer(element.attr("abs:href"));
					item.setItem_title(element.text());
					item.setItem_link(element.attr("abs:href"));
					item.setTokens(token);
					
					if(token != "") {
						articles.add(item);
					}	
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
		}
		return articles;
	}
	
	private String tokenizer(String link) throws MalformedURLException {
		String path = new URL(link).getPath();
		String token ="";
		if(path.length()>0) {
			path = path.substring(0, path.length()-1);
			String title = path.substring(path.lastIndexOf("/")+1, path.length());
			String[] tokens = title.trim().split("-");
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
