package com.yk.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yk.web.entity.ItemIndexes;

@Repository
public interface ItemIndexRepository extends JpaRepository<ItemIndexes, Long>{
	List<ItemIndexes> findByTokensContaining(String keyword);
}
