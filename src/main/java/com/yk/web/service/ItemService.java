package com.yk.web.service;

import java.util.ArrayList;
import java.util.List;

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
	ItemRepository itemRepository;
	
	@Autowired
	ItemIndexRepository itemIndexRepository;
	
	public List<Items> searchArticle(ItemRequestDto dto){
		List<String> keywords = ListingKeyword(dto);
		
		List<ItemIndexes> itemIndexes = itemIndexRepository.findByTokensContaining(keywords.get(0));
		List<ItemIndexes> itemIndexesByKeyword = searchItemIndexToken(keywords, itemIndexes);
		
		List<Items> searchResult = new ArrayList<>();
		for(int i=0; i<itemIndexesByKeyword.size(); i++) {
			Items item = itemRepository.findByItemId(itemIndexesByKeyword.get(i).getItem().getItemId());
			searchResult.add(item);
		}
		
		return searchResult;
	}
	
	private List<String> ListingKeyword(ItemRequestDto dto){
		String keyword = dto.getKeyword();
		keyword = keyword.trim().replaceAll("[^a-zA-Z0-9]", " ");
		String[] keywords = keyword.split(" ");
		
		List<String> list = new ArrayList<>();
		
		for(int i=0; i<keywords.length; i++) {
	    	if(!keywords[i].equals("")) {
	    		list.add(keywords[i]);
	    	}
		}
		return list;
	}
	
	private List<ItemIndexes> searchItemIndexToken(List<String> keywords, List<ItemIndexes> itemIndexes){
		List<ItemIndexes> itemIndexesByKeyword = new ArrayList<>();
		if(keywords.size()>1) {
			for(int i=1; i<keywords.size(); i++) {
				for(int j=0; j<itemIndexes.size(); j++) {
					String token = itemIndexes.get(j).getTokens();
					if(token.contains(keywords.get(i))) {
						itemIndexesByKeyword.add(itemIndexes.get(j));
					} 
				}
			}
		}
		return itemIndexesByKeyword;
	}
}
