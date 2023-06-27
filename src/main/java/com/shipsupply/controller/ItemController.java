package com.shipsupply.controller;

import com.shipsupply.domain.Item;
import com.shipsupply.domain.WishList;
import com.shipsupply.dto.*;
import com.shipsupply.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Autowired
    ItemService itemService;

    // 모든 행 출력
    @Operation(summary = "선용품 목록 보기")
    @ApiResponse(responseCode = "200", description = "선용품 목록이 정상적으로 조회되었습니다.")
    @GetMapping("/getItems")
    public ResponseEntity<List<Item>> getItems() {
        return ResponseEntity.ok().body(itemService.getItems());
    }

    // 검색 창에서 카테고리 검색
    @Operation(summary = "검색창에서 카테고리 검색")
    @ApiResponse(responseCode = "200", description = "카테고리가 정상적으로 조회되었습니다.")
    @GetMapping("/getCategory")
    public List<CategoryDTO> getCategoriesByKeyword(@RequestParam String category) {
        log.info("받은 카테고리 : " + category);
        return itemService.getCategoriesByKeyword(category);
    }

    // 검색 창에서 발주처 검색
    @Operation(summary = "검색창에서 발주처 검색")
    @ApiResponse(responseCode = "200", description = "발주처가 정상적으로 조회되었습니다.")
    @GetMapping("/getCompany")
    public List<CompanyDTO> getCompaniesByKeyword(@RequestParam String company) {
        log.info("받은 발주처 : " + company);
        return itemService.getCompaniesByKeyword(company);
    }

    // 검색 창에서 용품명 검색
    @Operation(summary = "검색창에서 선용품명 검색")
    @ApiResponse(responseCode = "200", description = "선용품명이 정상적으로 조회되었습니다.")
    @GetMapping("/getItemName")
    public List<ItemDTO> getItemByKeyword(@RequestParam String item) {
        log.info("받은 용품명 : " + item);
        return itemService.getItemByKeyword(item);
    }

    // 특정 카테고리 포함하는 모든 행 출력
    @Operation(summary = "특정 카테고리가 포함된 모든 행 조회")
    @ApiResponse(responseCode = "200", description = "해당 카테고리에 해당하는 선용품 정보가 정상적으로 조회되었습니다.")
    @GetMapping("/findByCategory")
    public List<Item> findByCategory(@RequestParam String category) {
        log.info("받은 카테고리명 : " + category);
        return itemService.findByCategory(category);
    }

    // 특정 부품명을 포함하는 모든 행 출력
    @Operation(summary = "특정 부품명이 포함된 모든 행 조회")
    @ApiResponse(responseCode = "200", description = "해당 부품명에 해당하는 선용품 정보가 정상적으로 조회되었습니다.")
    @GetMapping("/findByItem")
    public List<Item> findByItem(@RequestParam String item) {
        log.info("받은 아이템 : " + item);
        return itemService.findByItem(item);
    }

    // 특정 발주처를 포함하는 모든 행 출력
    @Operation(summary = "특정 발주처가 포함된 모든 행 조회")
    @ApiResponse(responseCode = "200", description = "해당 발주처에 해당하는 선용품 정보가 정상적으로 조회되었습니다.")
    @GetMapping("/findByCompany")
    public List<Item> findByCompany(@RequestParam String company) {
        log.info("받은 발주처 : " + company);
        return itemService.findByCompany(company);
    }

    // 과거 리드타임 추이
    @Operation(summary = "과거리드타임 조회")
    @ApiResponse(responseCode = "200", description = "과거리드타임 정보가 정상적으로 조회되었습니다.")
    @GetMapping("/pastLeadtime")
    public List<LeadtimeDTO> getPastLeadtime(
            @RequestParam("item") String item,
            @RequestParam("category") String category,
            @RequestParam("company") String company
    ) {
        return itemService.getPastLeadtime(item, category, company);
    }

    // 리드타임 분포
    @Operation(summary = "리드타임 분포 조회")
    @ApiResponse(responseCode = "200", description = "리드타임 분포 정보가 정상적으로 조회되었습니다.")
    @GetMapping("/leadtimeDistribution")
    public List<LeadtimeDistributionDTO> getLeadtimeDistribution() {
        return itemService.getLeadtimeDistribution();
    }

    // 카테고리 분포
    @Operation(summary = "카테고리 분포 조회")
    @ApiResponse(responseCode = "200", description = "카테고리 분포 정보가 정상적으로 조회되었습니다.")
    @GetMapping("/categoryDistribution")
    public List<CategoryDistributionDTO> getCategoryDistribution() {
        return itemService.getCategoryDistribution();
    }

    // 분류모델 예측
    @Operation(summary = "분류모델 예측")
    @ApiResponse(responseCode = "200", description = "분류모델이 정상적으로 예측되었습니다.")
    @PostMapping("/predict/classify")
    public ResponseEntity<String> predCategory(@RequestBody Map<String, String> data) {
        log.info("리액트에서 준 data : {}", data);
        return ResponseEntity.ok().body(itemService.predCategory(data));
    }

    // 회귀 모델 예측
    @Operation(summary = "회귀모델 예측")
    @ApiResponse(responseCode = "200", description = "회귀모델이 정상적으로 예측되었습니다.")
    @PostMapping("/predict/regression")
    public ResponseEntity<String> predLeadtime(@RequestBody Map<String, String> data) {
        log.info("리액트에서 준 data : {}", data);
        return ResponseEntity.ok().body(itemService.predLeadtime(data));
    }

}
