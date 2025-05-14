package com.site.slowprint.board.controller;

import com.site.slowprint.board.dto.MatchDetailResponseDTO;
import com.site.slowprint.board.dto.MatchListResponseDTO;
import com.site.slowprint.board.dto.MatchRequestDTO;
import com.site.slowprint.board.dto.MatchResponseDTO;
import com.site.slowprint.board.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/match")
public class MatchController {

    private final MatchService matchService;

    @PostMapping("/create")
    public ResponseEntity<MatchResponseDTO> createMatch(@RequestBody MatchRequestDTO matchRequestDTO) {
        MatchResponseDTO matchResponseDTO = matchService.createMatch(matchRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(matchResponseDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<MatchListResponseDTO>> matchList(@RequestParam(value="page", defaultValue="0") int page) {
        Page<MatchListResponseDTO> pages = matchService.matchList(page);
        return ResponseEntity.status(HttpStatus.OK).body(pages);
    }

    @GetMapping("/{matchId")
    public ResponseEntity<?> matchDetail(@PathVariable("matchId") Long matchId) {
        MatchDetailResponseDTO matchDetailResponseDTO;
        try {
            matchDetailResponseDTO = matchService.matchDetail(matchId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(matchDetailResponseDTO);
    }

}
