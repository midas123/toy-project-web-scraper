package com.yk.web.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class ItemIndexes {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long index_id;

	@Column
	private String tokens;
	
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="itemIndex")
	private Items item;
	
	public void setItem(Items item) {
		this.item = item;
	}
	
	public ItemIndexes(String tokens) {
		this.tokens = tokens;
	}
	
	@Builder
	public ItemIndexes(long index_id, String tokens, Items item) {
		this.index_id = index_id;
		this.tokens = tokens;
		this.item = item;
	}
	
	
}
