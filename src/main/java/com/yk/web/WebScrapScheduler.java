package com.yk.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yk.web.service.WebScrapService;

@Component
public class WebScrapScheduler {
	@Autowired
	WebScrapService webScrapService;
	
	@Scheduled(cron="0 0 0 * * *", zone ="")
	public void ScrapingWeb() {
		webScrapService.scrapArticles();
	}
	
}
