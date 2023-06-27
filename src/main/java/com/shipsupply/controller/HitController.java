package com.shipsupply.controller;

import com.shipsupply.domain.Hit;
import com.shipsupply.service.HitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hit")
public class HitController {

    private static final Logger logger = LoggerFactory.getLogger(HitController.class);

    @Autowired
    HitService hitService;

    @Operation(summary = "좋아요 개수 보기")
    @ApiResponse(responseCode = "200", description = "좋아요 개수가 정상적으로 조회되었습니다.")
    @GetMapping("/get/{commentId}")
    public Long getHit(@PathVariable Long commentId) {
        return hitService.getHit(commentId);
    }

    @Operation(summary = "좋아요 추가")
    @ApiResponse(responseCode = "200", description = "좋아요가 정상적으로 추가되었습니다.")
    @PostMapping("/add")
    public Hit addHit(@RequestBody Hit hit) {
        logger.info("받은 hit 정보 : " + hit);
        return hitService.addHit(hit);
    }

    @Operation(summary = "좋아요 취소")
    @ApiResponse(responseCode = "200", description = "좋아요가 정상적으로 취소되었습니다.")
    @DeleteMapping("/delete")
    public void deleteHit(@RequestBody Hit hit) {
        hitService.deleteHit(hit);
    }
}
