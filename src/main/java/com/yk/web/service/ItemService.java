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
		List<ItemIndexes> itemIndexes = itemIndexRepository.findByTokensContaining(dto.getKeyword());
		List<Items> searchResult = new ArrayList<>();
		for(int i=0; i<itemIndexes.size(); i++) {
			Items item = itemRepository.findByItemId(itemIndexes.get(i).getItem().getItemId());
			searchResult.add(item);
		}
		
		return searchResult;
	}
}
