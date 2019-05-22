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
public class ItemTagsRequestDto {
	private long tag_id;
	
	private String tag_name;
	
	@JsonIgnore
	private Items items;
	
	public ItemTags toEntity() {
		return ItemTags.builder()
				.tag_name(tag_name)
				.items(items)
				.build();
	}
}
