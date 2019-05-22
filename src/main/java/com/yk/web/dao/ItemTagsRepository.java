package com.yk.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yk.web.entity.ItemTags;

@Repository
public interface ItemTagsRepository extends JpaRepository<ItemTags, Long>{

}
