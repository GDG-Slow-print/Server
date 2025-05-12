package com.site.slowprint.carbon.service;

import com.site.slowprint.carbon.domain.Carbon;
import com.site.slowprint.carbon.dto.CarbonRankResponse;
import com.site.slowprint.carbon.dto.CarbonRequest;
import com.site.slowprint.carbon.dto.CarbonResponse;
import com.site.slowprint.carbon.repository.CarbonRepository;
import com.site.slowprint.user.domain.User;
import com.site.slowprint.carbon.exception.UserNotFoundInCarbonException;
import com.site.slowprint.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarbonService {

    private final UserRepository userRepository;
    private final CarbonRepository carbonRepository;

    // 탄소 절감량 계산 및 마일리지 적립
    @Transactional
    public CarbonResponse saveCarbon(String email, CarbonRequest carbonRequest) {
        // 사용자 정보 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundInCarbonException::new);

        // 1. 절감량 계산
        double savedCarbon = caclulateSavedCarbon(carbonRequest.getTransportation(), carbonRequest.getDistance());

        // 2. 마일리지 적립
        double earnedMileage = calculateEarnedMileage(savedCarbon);

        // 3. 사용자 정보 업데이트
        user.updateTotalMileage(earnedMileage);

        // 4. 탄소 절감량 정보 저장
        Carbon carbon = Carbon.builder()
                .user(user)
                .transportation(carbonRequest.getTransportation())
                .distance(carbonRequest.getDistance())
                .savedCarbon(savedCarbon)
                .earnedMileage(earnedMileage)
                .build();

        carbonRepository.save(carbon);

        return CarbonResponse.builder()
                .savedCarbon(savedCarbon)
                .earnedMileage(earnedMileage)
                .build();
    }

    // 탄소 절감량 계산
    private double caclulateSavedCarbon(String transportation, double distance) {
        double carEmission = 192.0;
        double transportationEmission = 0.0;

        switch(transportation.toLowerCase()) {
            case "walk","bike" -> transportationEmission = 0.0;
            case "subway" -> transportationEmission = 41.0;
            case "bus" -> transportationEmission = 105.0;
            default -> transportationEmission = 192.0;
        }

        // 1km당 탄소 배출량 (gCO2/km)
        return (carEmission - transportationEmission) * distance;
    }

    // 마일리지 계산
    private double calculateEarnedMileage(double savedCarbon) {
        // 100gCO2/km당 1 마일리지 적립
        return (int) (savedCarbon / 100);
    }

    // 사용자 랭킹 조회
    @Transactional(readOnly = true)
    public List<CarbonRankResponse> getRanking() {
        // 1. 사용자 정보 조회
        List<User> users = userRepository.findAllByOrderByTotalMileageDesc();
        List<CarbonRankResponse> result = new ArrayList<>();
        int rank = 1;

        // 2. 사용자 랭킹 정보 생성
        for (User user : users) {
            result.add(CarbonRankResponse.builder()
                    .rank(rank++)
                    .email(user.getEmail())
                    .totalMileage(user.getTotalMileage())
                    .build());
        }
        return result;
    }
}
