package com.shipsupply.controller;

import com.shipsupply.domain.Item;
import com.shipsupply.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/getItems")
    public ResponseEntity<List> getItems() {
        return ResponseEntity.ok().body(itemService.getItems());
    }

    @PostMapping("/addItem")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        return ResponseEntity.ok().body(itemService.addItem(item));
    }

}
