package com.site.slowprint.board.service;

import com.site.slowprint.board.dto.*;
import com.site.slowprint.board.entity.Matching;
import com.site.slowprint.board.entity.MatchingStatus;
import com.site.slowprint.board.entity.Photo;
import com.site.slowprint.board.repository.MatchingRepository;
import com.site.slowprint.user.domain.User;
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
public class MatchingService {

    private final MatchingRepository matchingRepository;

    public MatchingResponseDTO createMatch(MatchingRequestDTO matchingRequestDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        log.info("user: {}", user.getEmail());

        List<Photo> photos = new ArrayList<>();
        for (PhotoDTO photoDTO : matchingRequestDTO.getPhotos()) {
            Photo photo = Photo.builder()
                    .url(photoDTO.getUrl())
                    .isMain(photoDTO.isMain())
                    .odr(photoDTO.getOdr())
                    .build();
            photos.add(photo);
        }

        Matching matching = Matching.builder()
                        .user(user)
                        .title(matchingRequestDTO.getTitle())
                        .contents(matchingRequestDTO.getContents())
                        .nation(matchingRequestDTO.getNation())
                        .status(MatchingStatus.OPEN)
                        .createdAt(LocalDateTime.now())
                        .photos(photos)
                        .build();

        Matching newMatching = matchingRepository.save(matching);
        MatchingResponseDTO matchingResponseDTO = MatchingResponseDTO
                .builder()
                .matchingId(newMatching.getId())
                .message("게시물이 등록되었습니다.")
                .build();
        return matchingResponseDTO;
    }

    public Page<MatchingListResponseDTO> matchList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Matching> pages = matchingRepository.findAll(pageable);

        List<MatchingListResponseDTO> dtoList = pages.getContent().stream()
                .map(matching -> {
                    Optional<Photo> mainPhoto = matching.getPhotos().stream()
                            .filter(photo -> photo.isMain())
                            .findFirst();
                    MatchingListResponseDTO dto = MatchingListResponseDTO.builder()
                            .matchingId(matching.getId())
                            .title(matching.getTitle())
                            .contents(matching.getContents())
                            .mainPhoto(mainPhoto.isPresent() ? mainPhoto.get().getUrl() : null)
                            .createdAt(matching.getCreatedAt())
                            .updatedAt(matching.getUpdatedAt())
                            .status(matching.getStatus().name())
                            .build();

                    return dto;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, pages.getTotalElements());
    }

    public MatchingDetailResponseDTO matchDetail(Long matchingId) throws Exception {
        Optional<Matching> target = matchingRepository.findById(matchingId);
        MatchingDetailResponseDTO matchingDetailResponseDTO;
        if (target.isPresent()) {
            Matching matching = target.get();
            List<PhotoDTO> photos = new ArrayList<>();
            for (Photo photo : matching.getPhotos()) {
                PhotoDTO photoDTO = PhotoDTO.builder()
                        .url(photo.getUrl())
                        .isMain(photo.isMain())
                        .odr(photo.getOdr())
                        .build();
                photos.add(photoDTO);
            }
            matchingDetailResponseDTO = MatchingDetailResponseDTO.builder()
                    .userEmail(matching.getUser().getEmail())
                    .title(matching.getTitle())
                    .contents(matching.getContents())
                    .photos(photos)
                    .createdAt(matching.getCreatedAt())
                    .updatedAt(matching.getUpdatedAt())
                    .status(matching.getStatus().name())
            .build();
        } else {
            throw new Exception("error: 해당 게시글이 존재하지 않습니다.");
        }
        return matchingDetailResponseDTO;
    }
}
