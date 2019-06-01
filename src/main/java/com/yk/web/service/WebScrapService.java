package com.yk.web.service;


import java.util.HashMap;
import java.util.List;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yk.web.DataScraping;
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
	
	@Autowired
	DataScraping dataScraping;
	
	public HashMap<String, Long> scrapArticles(ItemRequestDto dto) {
		List<String> links = dataScraping.setLinks();
		Elements categoryLinks = dataScraping.getCategoryNameAndLink(links);
		List<ItemRequestDto> items = dataScraping.getArticleTitleAndLink(categoryLinks);
		
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

}
