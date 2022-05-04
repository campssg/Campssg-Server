package com.campssg.service;

import com.campssg.DB.entity.CampWishlist;
import com.campssg.DB.entity.Mart;
import com.campssg.DB.entity.MartWishlist;
import com.campssg.DB.entity.User;
import com.campssg.DB.repository.CampWishRepository;
import com.campssg.DB.repository.MartRepository;
import com.campssg.DB.repository.MartWishRepository;
import com.campssg.DB.repository.UserRepository;
import com.campssg.dto.camping.CampWishRequestDto;
import com.campssg.dto.camping.CampWishResponseDto;
import com.campssg.dto.mart.MartListResponseDto;
import com.campssg.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WishService {

    private final MartWishRepository martWishRepository;
    private final CampWishRepository campWishRepository;
    private final UserRepository userRepository;
    private final MartRepository martRepository;

    // 캠핑장 위시리스트에 저장
    public void addCampWish(CampWishRequestDto campWishRequestDto) {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow();
        CampWishlist campWishlist = CampWishlist.builder()
                .user(user)
                .campName(campWishRequestDto.getCampName())
                .campTel(campWishRequestDto.getCampTel())
                .campAddress(campWishRequestDto.getCampAddress())
                .campLatitude(campWishRequestDto.getLatitude())
                .campLongitude(campWishRequestDto.getLongitude())
                .build();
        campWishRepository.save(campWishlist);
    }

    // 캠핑장 위시리스트 반환
    public List<CampWishResponseDto> getCampWish() {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow();
        List<CampWishlist> campWishlists = campWishRepository.findByUser_userId(user.getUserId());
        return campWishlists.stream().map(campWishlist -> new CampWishResponseDto(campWishlist)).collect(Collectors.toList());
    }

    // 마트 위시리스트에 저장
    public void addMartWish(Long martId) {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow();
        Mart mart = martRepository.findByMartId(martId);
        MartWishlist martWishlist = MartWishlist.builder().mart(mart).user(user).build();
        martWishRepository.save(martWishlist);
    }

    // 마트 위시리스트 반환
    public List<MartListResponseDto> getMartWish() {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow();
        List<MartWishlist> martWishlists = martWishRepository.findByUser_userId(user.getUserId());
        return martWishlists.stream().map(martWishlist -> new MartListResponseDto(martWishlist.getMart())).collect(Collectors.toList());
    }
}
