package com.campssg.service;

import com.campssg.DB.entity.Mart;
import com.campssg.DB.repository.MartRepository;
import com.campssg.common.OpenApi;
import com.campssg.dto.mart.MartCertificationRequestDto;
import com.campssg.dto.mart.MartListResponseDto;
import com.campssg.dto.mart.MartSaveRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MartService {

    private final MartRepository martRepository;

    private final OpenApi openApi;

    public void saveMart(MartSaveRequestDto requestDto) {
        martRepository.save(requestDto.toEntity(requestDto.getUserId()));
    }

    public List<MartListResponseDto> findByUserId(Long userId) {
        List<Mart> martList = martRepository.findByUser_userId(userId);
        return martList.stream().map(mart -> new MartListResponseDto()).collect(Collectors.toList());
    }

    public void certifyMart(MartCertificationRequestDto requestDto) {
        openApi.connection(requestDto);
    }
}
