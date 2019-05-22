package com.yk.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yk.web.entity.ItemTags;
import com.yk.web.entity.Items;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ItemRequestDto {
	private long item_id;
	
	private String source_name;
	
	private String item_subject;
	
	private String item_link;
	
	private String item_content;
	
	private String tag_name;
	
	@JsonIgnore
	private ItemTags item_tags;
	
	public Items toEntity() {
		return Items.builder()
				.source_name(source_name)
				.item_subject(item_subject)
				.item_link(item_link)
				.item_content(item_content)
				.build();
	}
}
