package com.shipsupply.persistence;

import com.shipsupply.domain.Item;
import com.shipsupply.domain.WishList;
import com.shipsupply.dto.CategoryDTO;
import com.shipsupply.dto.CompanyDTO;
import com.shipsupply.dto.ItemDTO;
import com.shipsupply.dto.LeadtimeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategoryContaining(String category);

    List<Item> findByItemContaining(String item);

    List<Item> findByCompanyContaining(String company);

    // 과거 리드타임 추이
    // JPQL 사용하여 쿼리 작성.JPQL(Java Persistence Query Language)은 데이터베이스 테이블이 아닌 자바 엔티티 객체를 대상으로 하는 쿼리 언어
    // @Query는 해당 메서드에 JQPL 쿼리 지정하는데 사용
    @Query("SELECT new com.shipsupply.dto.LeadtimeDTO(i.leadtime, i.date)" + // 쿼리가 리턴할 내용 지정.
            // 각각의 결과를 LeatimeDTO 클래스의 새 인스턴스로 만들고, 그 생성자에 Leadtime 필드값 전달.
            "FROM Item i " + // 쿼리가 검색할 엔티티 지정. i는 별칭
            "WHERE i.item = :item " + // 검색 조건 지정. :item은 매개변수를 가리키는 바인딩 변수
            "AND i.category = :category " +
            "AND i.company = :company")
    // @Param은 JPQL 쿼리 내의 바인딩 변수에 실제 값 매핑하는데 사용(@Param("item" -> 바인딩 변수) String item은 실제값)
    // 따라서 이 쿼리는 Item 엔티티에서 각 매개변수에 해당하는 행을 찾고, 그 행의 리드타임 값을 가진 LeadtimeDTO 인스턴스의 리스트 반환
    List<LeadtimeDTO> findDuplicates(@Param("item") String item, @Param("category") String category, @Param("company") String company);
    default List<LeadtimeDTO> findByItemAndCategoryAndCompanyOrderByDate(String item, String category, String company) {
        List<LeadtimeDTO> duplicates = findDuplicates(item, category, company);

        return duplicates.stream().distinct().collect(Collectors.toList());
    }

    @Query("SELECT DISTINCT new com.shipsupply.dto.CategoryDTO(i.category) FROM Item i WHERE i.category LIKE %:category%")
    List<CategoryDTO> findByCategoryLike(@Param("category") String category);

    @Query("SELECT DISTINCT new com.shipsupply.dto.CompanyDTO(i.company) FROM Item i WHERE i.company LIKE %:company%")
    List<CompanyDTO> findByCompanyLike(@Param("company") String company);

    @Query("SELECT DISTINCT new com.shipsupply.dto.ItemDTO(i.item) FROM Item i WHERE i.item LIKE %:item%")
    List<ItemDTO> findByItemLike(@Param("item") String item);

}
