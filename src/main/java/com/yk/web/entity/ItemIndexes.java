package com.yk.web.entity;

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
import lombok.ToString;


@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
public class ItemIndexes {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long indexId;

	@Column(length = 400)
	private String tokens;
	
	@OneToOne
	@JoinColumn(name="itemId", referencedColumnName="itemId")
	private Items item;
	
	public void setItem(Items item) {
		this.item = item;
	}
	
	public ItemIndexes(String tokens) {
		this.tokens = tokens;
	}
	
	@Builder
	public ItemIndexes(String tokens, Items item) {
		this.tokens = tokens;
		this.item = item;
	}
	
	
}
