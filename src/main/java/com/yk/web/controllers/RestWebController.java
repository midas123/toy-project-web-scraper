package com.yk.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yk.web.entity.Items;

@RestController
public class RestWebController {
	@GetMapping("/items")
	public Items getItems() {
		
		return null;
	}
}
