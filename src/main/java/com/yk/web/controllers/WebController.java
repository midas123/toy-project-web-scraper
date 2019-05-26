package com.yk.web.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.yk.web.dto.ItemRequestDto;
import com.yk.web.service.ItemService;

@Controller
public class WebController {
	@Autowired
	ItemService itemService;
	
	
	@GetMapping("/")
	public String main() {
		return "main";
	}
	
	@GetMapping("/scrap")
	public String scrap(ItemRequestDto dto, Model model) {
		long startTime = System.nanoTime();
		long articleCounts = itemService.scrapArticles(dto);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		long convertedDuration = TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS);
		
		model.addAttribute("articleCounts", articleCounts);
		model.addAttribute("duration", convertedDuration);
		return "main";
	}
	
	@PostMapping("/search")
	public String search(ItemRequestDto dto, Model model) {
		
		return "main";
	}
}
