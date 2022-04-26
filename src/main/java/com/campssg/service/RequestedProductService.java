package com.campssg.service;

import com.campssg.DB.entity.*;
import com.campssg.DB.repository.MartRepository;
import com.campssg.DB.repository.OrderRepository;
import com.campssg.DB.repository.RequestedProductRepository;
import com.campssg.DB.repository.UserRepository;
import com.campssg.common.S3Uploder;
import com.campssg.dto.requestedProduct.AddRequestedProductDto;
import com.campssg.dto.requestedProduct.GetRequestedProductDto;
import com.campssg.dto.requestedProduct.GuestRequestDto;
import com.campssg.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestedProductService {

    private final RequestedProductRepository requestedProductRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    // 요청 상품 추가
    public RequestedProduct addRequestedProduct(AddRequestedProductDto addRequestedProductDto) throws IOException {
        Order order = orderRepository.findByOrderId(addRequestedProductDto.getOrderId());
        RequestedProduct requestedProduct = requestedProductRepository.save(RequestedProduct.builder()
                .order(order)
                .mart(order.getMart())
                .user(order.getUser())
                .requestedProductName(addRequestedProductDto.getRequestedProductName())
                .requestedProductPrice(addRequestedProductDto.getRequestedProductPrice())
                .requestedProductCount(addRequestedProductDto.getRequestedProductCount())
                .requestedProductState(RequestedProductState.가격요청중)
                .requestedProductReference(addRequestedProductDto.getRequestedProductReference())
                .build());
        return requestedProduct;
    }

    // 서비스 이용자가 최초로 요청상품 등록 및 가격 제시
    public GuestRequestDto guestRequestProduct(AddRequestedProductDto addRequestedProductDto) throws IOException {
        RequestedProduct requestedProduct = addRequestedProduct(addRequestedProductDto);
        return new GuestRequestDto(requestedProduct);
    }

    // 마트 운영자가 요청상품 조회
    public List<GetRequestedProductDto> getRequestedProductFromMart(Long martId) {
        List<RequestedProduct> requestedProducts = requestedProductRepository.findByMart_martIdAndRequestedProductState(martId, RequestedProductState.가격요청중);
        List<GetRequestedProductDto> requestedProductDtos = requestedProducts.stream().map(requestedProduct -> new GetRequestedProductDto(requestedProduct)).collect(Collectors.toList());
        return requestedProductDtos;
    }


    // 서비스 이용자가 요청 상품 조회
    public List<GetRequestedProductDto> getRequestedProductFromGuest() {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow();
        System.out.println(user.getUserId());
        Long userId = user.getUserId();
        List<RequestedProduct> requestedProducts = requestedProductRepository.findByUser_userIdAndRequestedProductState(userId, RequestedProductState.가격제시중);
        List<GetRequestedProductDto> requestedProductDtos = requestedProducts.stream().map(requestedProduct -> new GetRequestedProductDto(requestedProduct)).collect(Collectors.toList());
        return requestedProductDtos;
    }

    // 승인 버튼을 눌러서 가격 흥정 마무리
    public void acceptedProduct(Long requestedProductId) {
        RequestedProduct requestedProduct = requestedProductRepository.findByRequestedProductId(requestedProductId);
        requestedProduct.setRequestedProductState(RequestedProductState.흥정완료);
    }

    // 마트 측에서 가격 제시
    public void requestPriceToGuest(Long requestedProductId, int price) {
        RequestedProduct requestedProduct = requestedProductRepository.findByRequestedProductId(requestedProductId);
        requestedProduct.setRequestedProductPrice(price);
        requestedProduct.setRequestedProductState(RequestedProductState.가격제시중);
        requestedProductRepository.save(requestedProduct);
    }

    // 사용자 측에서 가격 요청
    public void requestPriceToMart(Long requestedProductId, int price) {
        RequestedProduct requestedProduct = requestedProductRepository.findByRequestedProductId(requestedProductId);
        requestedProduct.setRequestedProductPrice(price);
        requestedProduct.setRequestedProductState(RequestedProductState.가격요청중);
        requestedProductRepository.save(requestedProduct);
    }
}
