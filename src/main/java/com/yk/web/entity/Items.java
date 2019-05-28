package com.yk.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
	private Long itemId;
	
	@Column(length = 300)
	private String itemTitle;
	
	@Column(length = 300)
	private String itemLink;
	
/*	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="tag_id", referencedColumnName="tag_id")
	private ItemTags item_tags;*/
	
	//@JsonBackReference
	//@OneToOne(mappedBy = "item", cascade=CascadeType.ALL)
	@OneToOne(mappedBy = "item")
	private ItemIndexes itemIndexes;
	
	
	public void setItemIndexes(ItemIndexes itemIndexes) {
		this.itemIndexes = itemIndexes;
	}
	
	public void setItemIndexToken(String token) {
		itemIndexes.setTokens(token);
	}
	
	public Items(Long itemId) {
		this.itemId = itemId;
	}
	
	public Items(String itemTitle, String itemLink) {
		this.itemTitle = itemTitle;
		this.itemLink = itemLink;
	}

	@Builder
	public Items(String itemTitle, String itemLink, ItemIndexes itemIndexes) {
		this.itemTitle = itemTitle;
		this.itemLink = itemLink;
		this.itemIndexes = itemIndexes;
	}
	
	public void setitemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
	
	public void setitemLink(String itemLink) {
		this.itemLink = itemLink;
	}
}
