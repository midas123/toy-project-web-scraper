package com.yk.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yk.web.entity.ItemIndexes;
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
	
	private String item_title;
	
	private String item_link;
	
	private String tag_name;
	
	private String keyword;
	
	private String tokens;
	
	@JsonIgnore
	private Items item;
	
	@JsonIgnore
	private ItemTags item_tags;
	
	@JsonIgnore
	private ItemIndexes itemIndex;
	
	public ItemIndexes toIndexEntity() {
		return ItemIndexes.builder()
				.tokens(tokens)
				.item(item)
				.build();
	}
}
