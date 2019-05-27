package com.yk.web.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yk.web.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Items {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long item_id;
	
	@Column(length = 300)
	private String item_title;
	
	@Column(length = 300)
	private String item_link;
	
/*	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="tag_id", referencedColumnName="tag_id")
	private ItemTags item_tags;*/
	
	//@JsonBackReference
	@OneToOne(mappedBy = "item")
	private ItemIndexes itemIndexes;
	
	
	public void setItemIndexes(ItemIndexes itemIndexes) {
		this.itemIndexes = itemIndexes;
	}
	
	public void setItemIndexToken(String token) {
		itemIndexes.setTokens(token);
	}
	
	public Items(Long item_id) {
		this.item_id = item_id;
	}
	
	public Items(String item_title, String item_link) {
		this.item_title = item_title;
		this.item_link = item_link;
	}

	@Builder
	public Items(String item_title, String item_link, ItemIndexes itemIndexes) {
		this.item_title = item_title;
		this.item_link = item_link;
		this.itemIndexes = itemIndexes;
	}
	
	public void setItem_title(String item_title) {
		this.item_title = item_title;
	}
	
	public void setItem_link(String item_link) {
		this.item_link = item_link;
	}
}
