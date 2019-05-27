package com.yk.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yk.web.entity.ItemIndexes;
import com.yk.web.entity.ItemTags;
import com.yk.web.entity.Items;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class ItemRequestDto {
	private String item_title;
	
	private String item_link;
	
	private String tag_name;
	
	private String keyword;
	
	private String tokens;
	
	@JsonIgnore
	private Items item;
	
/*	@JsonIgnore
	private ItemTags item_tags;*/
	
	@JsonIgnore
	private ItemIndexes itemIndexes;
	
	public ItemIndexes toIndexEntity() {
		return ItemIndexes.builder()
				.tokens(tokens)
				.item(item)
				.build();
	}
	
	public Items toItemEntity() {
		return Items.builder()
				.item_link(item_link)
				.item_title(item_title)
				.itemIndexes(itemIndexes)
				.build();
	}
}
