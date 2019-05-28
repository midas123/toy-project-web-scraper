package com.yk.web.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yk.web.dao.ItemIndexRepository;
import com.yk.web.dao.ItemRepository;
import com.yk.web.entity.ItemIndexes;
import com.yk.web.entity.Items;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebScrapServiceTest {
	@Autowired
	private ItemIndexRepository itemIndexRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@After
	public void cleanUp() {
		//itemIndexRepository.deleteAll();
	}
	
	
	@Test
	public void testScrapArticles() {
		//given
		Items i = itemRepository.save(Items.builder()
				//.itemIndexes(new ItemIndexes(""))
				.itemLink("link")
				.itemTitle("title")
				.build());
		
		long item_id = i.getItemId();
		
		itemIndexRepository.save(ItemIndexes.builder()
				.tokens("token")
				.item(new Items(item_id))
				.build());
		
		//when
		List<ItemIndexes> list = itemIndexRepository.findAll();
		List<Items> list2 = itemRepository.findAll();
		
		//then
		ItemIndexes itemIndexes = new ItemIndexes();
		itemIndexes = list.get(0);
		
		Items item = list2.get(0);
		
		assertThat(itemIndexes.getTokens(), is("token"));
		assertThat(item.getItemTitle(), is("title"));
		assertThat(item.getItemLink(), is("link"));
	}
}
