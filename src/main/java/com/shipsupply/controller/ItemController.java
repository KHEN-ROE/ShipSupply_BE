package com.shipsupply.controller;

import com.shipsupply.domain.Item;
import com.shipsupply.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/api/getItems")
    public ResponseEntity<List<Item>> getItems() {
        return ResponseEntity.ok().body(itemService.getItems());
    }

    @PostMapping("/api/addItem")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        return ResponseEntity.ok().body(itemService.addItem(item));
    }

}
