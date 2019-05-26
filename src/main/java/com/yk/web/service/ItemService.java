package com.yk.web.service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
	@Transactional
	public long scrapArticles(ItemRequestDto dto) {
		//if 7일 경과 또는 items 테이블 데이터X
		//if(itemRepository.findAll())
		//String[] RegKeywords = writeRegExp("java");
		
		List<String> links = setLinks();
		List<Element> categoryLinks = getCategoryNameAndLink(links);
		List<ItemIndexes> items = getArticleTitleAndLink(categoryLinks);
		
//		itemIndexRepository.saveAll(items);
		itemIndexRepository.save(items.get(0));
		//items.add(item);
		
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
				//Elements categoryElement = document.select("li a[href]");
				Elements categoryElement = document.select("li a[href~=\\/(.)[/.*(?i)spring.*|.*(?i)java.*]]");
				for(Element e: categoryElement) {
					//if(e.text().matches(RegKeywords[0]))
						categoryTitleAndLink.add(e);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
		}
		return categoryTitleAndLink;
	}
	
	private List<ItemIndexes> getArticleTitleAndLink(List<Element> categoryLinks){
		List<ItemIndexes> articles = new ArrayList<>();
		
		for(Element el: categoryLinks) {
			try {
				Document document = Jsoup.connect(el.attr("abs:href")).get();
				Elements elements = document.select("li a[href~=\\/(.)[/.*(?i)spring.*|.*(?i)java.*]]");
				for(Element element : elements) {
					ItemRequestDto item = new ItemRequestDto();
					String token = tokenizer(element.text());
					
					item.setItem_title(element.text());
					item.setItem_link(element.attr("abs:href"));
					item.setItemIndex(new ItemIndexes(token));
					
					articles.add(item.toIndexEntity());
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
		}
		return articles;
	}
	
	private String tokenizer(String title) {
		String[] tokens = title.trim().split("-");
		String token = String.join(",", tokens);
	/*	StringBuilder builder = new StringBuilder();
		for(String s: tokens) {
			builder.append(s);
		}*/
		return token;
	}
	
/*	private List<HashMap<String, String>> getArticleTitleAndLink(List<Element> categoryLinks, String[] keyword){
		List<HashMap<String, String>> articles = new ArrayList<>();
		
		for(Element el: categoryLinks) {
			try {
				Document document = Jsoup.connect(el.attr("abs:href")).get();
				//Elements elements = document.select("li a[href]");
				Elements elements = document.select("li a[href~=\\/(.)[/.*(?i)spring.*|.*(?i)java.*]]");
				for(Element element : elements) {
					//if(element.text().matches((keyword.length ==2) ? keyword[1]:keyword[0])) {
						HashMap<String, String> article = new HashMap<>();
						article.put("title", element.text());
						article.put("link", element.attr("abs:href"));
						articles.add(article);
					//}
						
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
		}
		//writeToFile(articles);
		return articles;
	}*/
	
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
