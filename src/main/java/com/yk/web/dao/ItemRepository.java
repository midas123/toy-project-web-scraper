package com.yk.web.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yk.web.entity.Items;

@Repository
public interface ItemRepository extends JpaRepository<Items, Long>{
	@Query
	Items findByItemId(Long item_id);
}
