package com.yk.web.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.yk.web.dto.ItemRequestDto;
import com.yk.web.dto.ItemResponseDto;
import com.yk.web.service.ItemService;

@Controller
public class WebController {
	@Autowired
	ItemService itemService;
	
	
	@GetMapping("/")
	public String main() {
		return "main";
	}
	
	@PostMapping("/search")
	public String search(ItemRequestDto dto, Model model) {
		dto.setItem_link("http://www.mkyong.com");
		List<HashMap<String, String>> results = itemService.getArticle(dto);
		model.addAttribute("searchResult", results);
		return "main";
	}
}
