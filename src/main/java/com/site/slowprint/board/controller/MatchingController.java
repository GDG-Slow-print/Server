package com.site.slowprint.board.controller;

import com.site.slowprint.board.dto.MatchingDetailResponseDTO;
import com.site.slowprint.board.dto.MatchingListResponseDTO;
import com.site.slowprint.board.dto.MatchingRequestDTO;
import com.site.slowprint.board.dto.MatchingResponseDTO;
import com.site.slowprint.board.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/matching")
public class MatchingController {

    private final MatchingService matchingService;

    @PostMapping("/create")
    public ResponseEntity<MatchingResponseDTO> createMatch(@RequestBody MatchingRequestDTO matchingRequestDTO) {
        MatchingResponseDTO matchingResponseDTO = matchingService.createMatch(matchingRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(matchingResponseDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<MatchingListResponseDTO>> matchList(@RequestParam(value="page", defaultValue="0") int page) {
        Page<MatchingListResponseDTO> pages = matchingService.matchList(page);
        return ResponseEntity.status(HttpStatus.OK).body(pages);
    }

    @GetMapping("/{matchingId}")
    public ResponseEntity<?> matchDetail(@PathVariable("matchingId") Long matchingId) {
        MatchingDetailResponseDTO matchingDetailResponseDTO;
        try {
            matchingDetailResponseDTO = matchingService.matchDetail(matchingId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(matchingDetailResponseDTO);
    }

}
