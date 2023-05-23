package com.shipsupply.controller;

import com.shipsupply.domain.Item;
import com.shipsupply.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/predict/classify")
    public ResponseEntity<String> getCategory(@RequestBody Map<String, String> data) {
        System.out.println("클라이언트에서 준 data : " + data);
        return ResponseEntity.ok().body(itemService.getCategory(data));
    }

}
