package com.yk.web.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yk.web.WebScraper;
import com.yk.web.WebScraperOne;
import com.yk.web.WebScraperTwo;
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
		List<ItemRequestDto> allItems = new ArrayList<>();
		
		WebScraperOne one = new WebScraperOne();
		List<String> links = one.setLinks();
		Elements categoryLinks = one.getCategoryNameAndLink(links);
		List<ItemRequestDto> items = one.getArticleTitleAndLink(categoryLinks);
		allItems.addAll(items);
		
		WebScraperTwo two = new WebScraperTwo();
		List<String> links2 = two.setLinks();
		Elements categoryLinks2 = two.getCategoryNameAndLink(links2);
		List<ItemRequestDto> items2 = two.getArticleTitleAndLink(categoryLinks2);
		allItems.addAll(items2);
		
		for(int j=0; j<allItems.size(); j++) {
			Items item = itemRepository.save(allItems.get(j).toItemEntity());
			long item_id = item.getItemId();
			
			itemIndexRepository.save(ItemIndexes.builder()
					.item(new Items(item_id))
					.tokens(allItems.get(j).getTokens())
					.build());
		}
		
		HashMap<String, Long> resultCounts = new HashMap<>();
		resultCounts.put("articleCounts", (long) allItems.size());
		
		return resultCounts;
	}

}
