package com.yk.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Entity
public class Items {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long item_id;
	
	@Column
	private String source_name;
	
	@Column
	private String item_subject;
	
	@Column
	private String item_link;
	
	@Column
	private String item_content;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="tag_id", referencedColumnName="tag_id")
	private ItemTags item_tags;

	@Builder
	public Items(long item_id, String source_name, String item_subject, String item_link, String item_content, ItemTags item_tags) {
		this.item_id = item_id;
		this.source_name = source_name;
		this.item_subject = item_subject;
		this.item_link = item_link;
		this.item_content = item_content;
		this.item_tags = item_tags;
	}
}
