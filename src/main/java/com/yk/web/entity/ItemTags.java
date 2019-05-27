package com.yk.web.entity;

import java.util.List;

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
public class ItemTags {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long tag_id;
	
	@Column
	private String tag_name;
	
/*	@JsonManagedReference
	@OneToMany(mappedBy="item_tags")
	private List<Items> items;*/

	@Builder
	public ItemTags(long tag_id, String tag_name, List<Items> items) {
		this.tag_id = tag_id;
		this.tag_name = tag_name;
		//this.items = items;
	}
	
}
