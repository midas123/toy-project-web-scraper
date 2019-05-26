package com.yk.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yk.web.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
public class Items extends BaseTimeEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long item_id;
	
	@Column
	private String item_title;
	
	@Column
	private String item_link;
	
/*	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="tag_id", referencedColumnName="tag_id")
	private ItemTags item_tags;*/
	
	@JsonBackReference
	@OneToOne(mappedBy="item")
	private ItemIndexes itemIndex;
	
	public void setItemIndex(ItemIndexes itemIndex) {
		this.itemIndex = itemIndex;
	}
	
	public Items() {
		
	}
	
	public Items(String item_title, String item_link) {
		this.item_title = item_title;
		this.item_link = item_link;
	}

	@Builder
	public Items(long item_id, String item_title, String item_link, ItemTags item_tags, ItemIndexes itemIndex) {
		this.item_id = item_id;
		this.item_title = item_title;
		this.item_link = item_link;
		//this.item_tags = item_tags;
		this.itemIndex = itemIndex;
	}
	
	public void setItem_title(String item_title) {
		this.item_title = item_title;
	}
	
	public void setItem_link(String item_link) {
		this.item_link = item_link;
	}
}
