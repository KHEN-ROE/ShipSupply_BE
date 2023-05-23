package com.shipsupply.controller;

import com.shipsupply.domain.Hit;
import com.shipsupply.service.HitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hit")
public class HitController {

    @Autowired
    HitService hitService;

    @GetMapping("/get/{num}")
    public Long getHit(@PathVariable Long num) {
        return hitService.getHit(num);
    }

    @PostMapping("/add")
    public Hit addHit(@RequestBody Hit hit) {
        return hitService.addHit(hit);
    }

    @DeleteMapping("/delete")
    public void deleteHit(@RequestBody Hit hit) {
        hitService.deleteHit(hit);
    }
}
