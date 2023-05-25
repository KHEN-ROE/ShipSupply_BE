package com.shipsupply.controller;

import com.shipsupply.domain.Item;
import com.shipsupply.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/item")
@RestController
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/getItems")
    public ResponseEntity<List<Item>> getItems() {
        return ResponseEntity.ok().body(itemService.getItems());
    }

    @PostMapping("/addItem")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        return ResponseEntity.ok().body(itemService.addItem(item));
    }

    // db에 있는 카테고리 검색 메서드
    @GetMapping("/getCategory")
    public List<Item> getCategory(@RequestBody Item item) {
        System.out.println("클라이언트에서 준 카테고리 : " + item);
        return itemService.getCategory(item);
    }

    // db에 있는 선용품 조회 메서드
    @GetMapping("/getItem")
    public List<Item> getItem(@RequestBody Item item) {
        return itemService.getItem(item);
    }

    // db에 있는 특정 카테고리에 해당하는 선용품 검색 메서드
    @GetMapping("/getCateAndItem")
    public List<Item> getCateAndItem(@RequestBody Item item) {
        System.out.println("클라이언트에서 준 카테고리와 선용품 : " + item);
        return itemService.getCateAndItem(item);
    }

    //분류모델 예측 메서드
    @PostMapping("/predict/classify")
    public ResponseEntity<String> predCategory(@RequestBody Map<String, String> data) {
        System.out.println("클라이언트에서 준 data : " + data);
        return ResponseEntity.ok().body(itemService.predCategory(data));
    }

}
