package com.shipsupply.persistence;

import com.shipsupply.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategoryContaining(String category);
    List<Item> findByItemContaining(String item);
    List<Item> findByCategoryAndItem(String category, String item);
}
