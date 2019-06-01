package com.yk.web.service;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import com.yk.web.DataScraping;
import com.yk.web.dao.ItemIndexRepository;
import com.yk.web.dao.ItemRepository;
import com.yk.web.entity.Items;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest2 {
	@Autowired
	private ItemIndexRepository itemIndexRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	DataScraping dataScraping;
	
	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	@Test
	public void testScrapArticles() throws MalformedURLException {
		List<Items> item = itemRepository.findAll();
		for(int i=0; i<item.size(); i++) {
			String link = item.get(i).getItemLink();
			String token = dataScraping.tokenizer(link);
			System.out.println("link:"+link+"  token:"+token);
		}
	}

}
