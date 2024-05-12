package com.rajinda.retail.inventoryservice.repository;

import com.rajinda.retail.inventoryservice.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCodeIn(List<String> codes);
}
