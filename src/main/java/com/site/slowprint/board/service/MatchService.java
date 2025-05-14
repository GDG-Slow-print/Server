package com.site.slowprint.board.service;

import com.site.slowprint.board.dto.*;
import com.site.slowprint.board.entity.Match;
import com.site.slowprint.board.entity.MatchStatus;
import com.site.slowprint.board.entity.Photo;
import com.site.slowprint.board.repository.MatchRepository;
import com.site.slowprint.user.domain.User;
import com.site.slowprint.user.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchResponseDTO createMatch(MatchRequestDTO matchRequestDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        log.info("user: {}", user.getEmail());

        List<Photo> photos = new ArrayList<>();
        for (PhotoDTO photoDTO : matchRequestDTO.getPhotos()) {
            Photo photo = Photo.builder()
                    .url(photoDTO.getUrl())
                    .isMain(photoDTO.isMain())
                    .order(photoDTO.getOrder())
                    .build();
            photos.add(photo);
        }

        Match match = Match.builder()
                        .user(user)
                        .title(matchRequestDTO.getTitle())
                        .contents(matchRequestDTO.getContents())
                        .nation(matchRequestDTO.getNation())
                        .status(MatchStatus.OPEN)
                        .createdAt(LocalDateTime.now())
                        .photos(photos)
                        .build();

        Match newMatch = matchRepository.save(match);
        MatchResponseDTO matchResponseDTO = MatchResponseDTO
                .builder()
                .matchId(newMatch.getId())
                .message("게시물이 등록되었습니다.")
                .build();
        return matchResponseDTO;
    }

    public Page<MatchListResponseDTO> matchList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Match> pages = matchRepository.findAll(pageable);

        List<MatchListResponseDTO> dtoList = pages.getContent().stream()
                .map(match -> {
                    Optional<Photo> mainPhoto = match.getPhotos().stream()
                            .filter(photo -> photo.isMain())
                            .findFirst();
                    MatchListResponseDTO dto = MatchListResponseDTO.builder()
                            .matchId(match.getId())
                            .title(match.getTitle())
                            .contents(match.getContents())
                            .mainPhoto(mainPhoto.isPresent() ? mainPhoto.get().getUrl() : null)
                            .createdAt(match.getCreatedAt())
                            .updatedAt(match.getUpdatedAt())
                            .status(match.getStatus().name())
                            .build();

                    return dto;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, pages.getTotalElements());
    }

    public MatchDetailResponseDTO matchDetail(Long matchId) throws Exception {
        Optional<Match> target = matchRepository.findById(matchId);
        MatchDetailResponseDTO matchDetailResponseDTO;
        if (target.isPresent()) {
            Match match = target.get();
            List<PhotoDTO> photos = new ArrayList<>();
            for (Photo photo : match.getPhotos()) {
                PhotoDTO photoDTO = PhotoDTO.builder()
                        .url(photo.getUrl())
                        .isMain(photo.isMain())
                        .order(photo.getOrder())
                        .build();
                photos.add(photoDTO);
            }
            matchDetailResponseDTO = MatchDetailResponseDTO.builder()
                    .userEmail(match.getUser().getEmail())
                    .title(match.getTitle())
                    .contents(match.getContents())
                    .photos(photos)
                    .createdAt(match.getCreatedAt())
                    .updatedAt(match.getUpdatedAt())
                    .status(match.getStatus().name())
            .build();
        } else {
            throw new Exception("error: 해당 게시글이 존재하지 않습니다.");
        }
        return matchDetailResponseDTO;
    }
}
