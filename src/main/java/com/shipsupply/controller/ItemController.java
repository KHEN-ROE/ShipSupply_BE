package com.shipsupply.controller;

import com.shipsupply.domain.Item;
import com.shipsupply.dto.CategoryDTO;
import com.shipsupply.dto.CompanyDTO;
import com.shipsupply.dto.ItemDTO;
import com.shipsupply.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/api/item")
@RestController
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    ItemService itemService;

    // 모든 행 출력
    @GetMapping("/getItems")
    public ResponseEntity<List<Item>> getItems() {
        return ResponseEntity.ok().body(itemService.getItems());
    }

    // 검색 창에서 카테고리 검색
    @GetMapping("/getCategory")
    public List<CategoryDTO> getCategoriesByKeyword(@RequestParam String category) {
        log.info("받은 카테고리 : " + category);
        return itemService.getCategoriesByKeyword(category);
    }

    // 검색 창에서 발주처 검색
    @GetMapping("/getCompany")
    public List<CompanyDTO> getCompaniesByKeyword(@RequestParam String company) {
        log.info("받은 발주처 : " + company);
        return itemService.getCompaniesByKeyword(company);
    }

    // 검색 창에서 부품명 검색
    @GetMapping("/getItemName")
    public List<ItemDTO> getItemByKeyword(@RequestParam String item) {
        log.info("받은 부품명 : " + item);
        return itemService.getItemByKeyword(item);
    }

    // 특정 카테고리 포함하는 모든 행 출력
    @GetMapping("/findByCategory")
    public List<Item> findByCategory(@RequestParam String category) {
        return itemService.findByCategory(category);
    }

    // 특정 부품명을 포함하는 모든 행 출력
    @GetMapping("/findByItem")
    public List<Item> findByItem(@RequestParam String item) {
        return itemService.findByItem(item);
    }

    // 특정 발주처를 포함하는 모든 행 출력
    @GetMapping("/findByCompany")
    public List<Item> findByCompany(@RequestParam String company) {
        return itemService.findByCompany(company);
    }

    // 분류모델 예측
    @PostMapping("/predict/classify")
    public ResponseEntity<String> predCategory(@RequestBody Map<String, String> data) {
        logger.info("리액트에서 준 data : {}", data);
        return ResponseEntity.ok().body(itemService.predCategory(data));
    }

    // 회귀 모델 예측
    @PostMapping("/predict/regression")
    public ResponseEntity<String> predLeadtime(@RequestBody Map<String, String> data) {
        logger.info("리액트에서 준 data : {}", data);
        return ResponseEntity.ok().body(itemService.predLeadtime(data));
    }

}
