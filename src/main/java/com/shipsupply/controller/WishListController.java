package com.shipsupply.controller;

import com.shipsupply.domain.User;
import com.shipsupply.domain.WishList;
import com.shipsupply.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/wish")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @Operation(summary = "관심상품 조회")
    @ApiResponse(responseCode = "200", description = "관심상품 정보가 정상적으로 조회되었습니다.")
    @GetMapping("/get")
    public List<WishList> getList(@RequestParam String userId) {
        return wishListService.getList(userId);
    }

    @Operation(summary = "관심상품 조회")
    @ApiResponse(responseCode = "200", description = "관심상품 상세정보가 정상적으로 조회되었습니다.")
    @GetMapping("/get/{id}")
    public Optional<WishList> getDetail(@PathVariable Long id) {
        return wishListService.getDetail(id);
    }

    @Operation(summary = "관심상품 추가")
    @ApiResponse(responseCode = "200", description = "관심상품이 정상적으로 추가되었습니다.")
    @PostMapping("/add")
    public WishList addList(@RequestBody WishList wishList) {
        return wishListService.addList(wishList);
    }

    @Operation(summary = "관심상품 수정")
    @ApiResponse(responseCode = "200", description = "관심상품이 정상적으로 수정되었습니다.")
    @PutMapping("/update")
    public WishList updateList(@RequestBody WishList wishList) {
        return wishListService.updateList(wishList);
    }

    @Operation(summary = "관심상품 삭제")
    @ApiResponse(responseCode = "200", description = "관심상품이 정상적으로 삭제되었습니다.")
    @DeleteMapping("/delete")
    public void deleteList(@RequestBody WishList wishList) {
        wishListService.deleteList(wishList);
    }

}
