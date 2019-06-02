package com.yk.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.yk.web.dto.ItemRequestDto;
import com.yk.web.entity.ItemIndexes;

@Component
public class KeywordFinder {
	public List<String> listingKeyword(ItemRequestDto dto){
		String keyword = dto.getKeyword();
		keyword = keyword.trim().replaceAll("[^a-zA-Z0-9]", " ");
		String[] keywords = keyword.split(" ");
		
		List<String> list = new ArrayList<>();
		if(keywords.length>1) {
			for(int i=0; i<keywords.length; i++) {
		    	if(!keywords[i].equals("")) {
		    		list.add(keywords[i]);
		    	}
			}
		} else {
			list.add(keyword);
		}
		return list;
	}
	
	public List<ItemIndexes> searchItembyToken(List<String> keywords, List<ItemIndexes> itemIndexes){
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
