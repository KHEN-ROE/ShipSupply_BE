package com.shipsupply.controller;

import com.shipsupply.domain.WishList;
import com.shipsupply.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wish")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @GetMapping("/get")
    public List<WishList> getList() {
        return wishListService.getList();
    }

    @GetMapping("/get/{id}")
    public Optional<WishList> getDetail(@PathVariable Long id) {
        return wishListService.getDetail(id);
    }

    @PostMapping("/add")
    public WishList addList(@RequestBody WishList wishList) {
        System.out.println("받은 위시리스트 : " + wishList);
        return wishListService.addList(wishList);
    }

    @PutMapping("/update")
    public WishList updateList(@RequestBody WishList wishList) {
        System.out.println("받은 수정 목록 : " + wishList);
        return wishListService.updateList(wishList);
    }

    @DeleteMapping("/delete")
    public void deleteList(@RequestBody WishList wishList) {
        System.out.println("받은 삭제 목록 : " + wishList);
        wishListService.deleteList(wishList);
    }

}
