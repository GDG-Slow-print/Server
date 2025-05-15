package com.site.slowprint.board.controller;

import com.site.slowprint.board.dto.*;
import com.site.slowprint.board.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recruitment")
public class RecruitmentController {

    RecruitmentService recruitmentService;

    @PostMapping("/create")
    public ResponseEntity<RecruitmentResponseDTO> createMatch(@RequestBody RecruitmentRequestDTO recruitmentRequestDTO) {
        RecruitmentResponseDTO recruitmentResponseDTO = recruitmentService.recruit(recruitmentRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(recruitmentResponseDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<RecruitmentListResponseDTO>> recruitmentList(@RequestParam(value="page", defaultValue="0") int page) {
        Page<RecruitmentListResponseDTO> pages = recruitmentService.recruitmentList(page);
        return ResponseEntity.status(HttpStatus.OK).body(pages);
    }

    @GetMapping("/{recruitmenteId}")
    public ResponseEntity<?> recruitmentDetail(@PathVariable("recruitmentId") Long recruitmentId) {
        RecruitmentDetailResponseDTO recruitmentDetailResponseDTO;
        try {
            recruitmentDetailResponseDTO = recruitmentService.recruitmentDetail(recruitmentId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(recruitmentDetailResponseDTO);
    }
}
