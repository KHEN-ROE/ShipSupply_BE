package com.shipsupply.persistence;

import com.shipsupply.domain.Item;
import com.shipsupply.dto.CategoryDTO;
import com.shipsupply.dto.CompanyDTO;
import com.shipsupply.dto.ItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategoryContaining(String category);

    List<Item> findByItemContaining(String item);

    List<Item> findByCompanyContaining(String company);

    @Query("SELECT DISTINCT new com.shipsupply.dto.CategoryDTO(i.category) FROM Item i WHERE i.category LIKE %:category%")
    List<CategoryDTO> findByCategoryLike(@Param("category") String category);

    @Query("SELECT DISTINCT new com.shipsupply.dto.CompanyDTO(i.company) FROM Item i WHERE i.company LIKE %:company%")
    List<CompanyDTO> findByCompanyLike(@Param("company") String company);

    @Query("SELECT DISTINCT new com.shipsupply.dto.ItemDTO(i.item) FROM Item i WHERE i.item LIKE %:item%")
    List<ItemDTO> findByItemLike(@Param("item") String item);

}
