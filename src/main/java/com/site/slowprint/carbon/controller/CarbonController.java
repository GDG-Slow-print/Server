package com.site.slowprint.carbon.controller;

import com.site.slowprint.carbon.dto.CarbonRankResponse;
import com.site.slowprint.carbon.dto.CarbonRequest;
import com.site.slowprint.carbon.dto.CarbonResponse;
import com.site.slowprint.carbon.service.CarbonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/carbon")
public class CarbonController {

    private final CarbonService carbonService;

    // 탄소 절감량 계산 API
    @PostMapping("/footprint")
    public ResponseEntity<CarbonResponse> saveCarbon(@RequestHeader("Email") String email,
                                                     @RequestBody CarbonRequest request) {
        CarbonResponse carbonResponse = carbonService.saveCarbon(email, request);
        return new ResponseEntity<>(carbonResponse, HttpStatus.OK);
    }

    // 탄소 절감량 랭킹 API
    @GetMapping("/rank")
    public ResponseEntity<List<CarbonRankResponse>> getRanking() {
        return ResponseEntity.ok(carbonService.getRanking());
    }

}
