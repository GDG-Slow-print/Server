package com.site.slowprint.board.service;

import com.site.slowprint.board.dto.*;
import com.site.slowprint.board.entity.Matching;
import com.site.slowprint.board.entity.MatchingStatus;
import com.site.slowprint.board.entity.Photo;
import com.site.slowprint.board.entity.Recruitment;
import com.site.slowprint.board.repository.RecruitmentRepository;
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
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;

    public RecruitmentResponseDTO recruit(RecruitmentRequestDTO recruitmentRequestDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        log.info("user: {}", user.getEmail());

        List<Photo> photos = new ArrayList<>();
        for (PhotoDTO photoDTO : recruitmentRequestDTO.getPhotos()) {
            Photo photo = Photo.builder()
                    .url(photoDTO.getUrl())
                    .isMain(photoDTO.isMain())
                    .odr(photoDTO.getOdr())
                    .build();
            photos.add(photo);
        }

        Recruitment recruitment = Recruitment.builder()
                .user(user)
                .title(recruitmentRequestDTO.getTitle())
                .contents(recruitmentRequestDTO.getContents())
                .nation(recruitmentRequestDTO.getNation())
                .status(MatchingStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .photos(photos)
                .build();

        Recruitment newRecruitment = recruitmentRepository.save(recruitment);
        RecruitmentResponseDTO recruitmentResponseDTO = RecruitmentResponseDTO
                .builder()
                .recruitmentId(newRecruitment.getId())
                .message("모집글이 등록되었습니다.")
                .build();
        return recruitmentResponseDTO;
    }

    public Page<RecruitmentListResponseDTO> recruitmentList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Recruitment> pages = recruitmentRepository.findAll(pageable);

        List<RecruitmentListResponseDTO> dtoList = pages.getContent().stream()
                .map(recruitment -> {
                    Optional<Photo> mainPhoto = recruitment.getPhotos().stream()
                            .filter(photo -> photo.isMain())
                            .findFirst();
                    RecruitmentListResponseDTO dto = RecruitmentListResponseDTO.builder()
                            .recruitmentId(recruitment.getId())
                            .title(recruitment.getTitle())
                            .contents(recruitment.getContents())
                            .nation(recruitment.getNation())
                            .province(recruitment.getProvince())
                            .city(recruitment.getCity())
                            .mainPhoto(mainPhoto.isPresent() ? mainPhoto.get().getUrl() : null)
                            .createdAt(recruitment.getCreatedAt())
                            .updatedAt(recruitment.getUpdatedAt())
                            .status(recruitment.getStatus().name())
                            .build();

                    return dto;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, pages.getTotalElements());
    }

    public RecruitmentDetailResponseDTO recruitmentDetail(Long recruitmentId) throws Exception {
        Optional<Recruitment> target = recruitmentRepository.findById(recruitmentId);
        RecruitmentDetailResponseDTO recruitmentDetailResponseDTO;
        if (target.isPresent()) {
            Recruitment recruitment = target.get();
            List<PhotoDTO> photos = new ArrayList<>();
            for (Photo photo : recruitment.getPhotos()) {
                PhotoDTO photoDTO = PhotoDTO.builder()
                        .url(photo.getUrl())
                        .isMain(photo.isMain())
                        .odr(photo.getOdr())
                        .build();
                photos.add(photoDTO);
            }
            recruitmentDetailResponseDTO = RecruitmentDetailResponseDTO.builder()
                    .userEmail(recruitment.getUser().getEmail())
                    .title(recruitment.getTitle())
                    .contents(recruitment.getContents())
                    .nation(recruitment.getNation())
                    .province(recruitment.getProvince())
                    .city(recruitment.getCity())
                    .photos(photos)
                    .createdAt(recruitment.getCreatedAt())
                    .updatedAt(recruitment.getUpdatedAt())
                    .status(recruitment.getStatus().name())
                    .build();
        } else {
            throw new Exception("error: 해당 게시글이 존재하지 않습니다.");
        }
        return recruitmentDetailResponseDTO;
    }
}
