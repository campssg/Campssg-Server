package com.campssg.service;

import com.campssg.DB.entity.*;
import com.campssg.DB.repository.OrderRepository;
import com.campssg.DB.repository.RequestedProductRepository;
import com.campssg.DB.repository.UserRepository;
import com.campssg.dto.push.PushMessage;
import com.campssg.dto.requestedProduct.AddRequestedProductDto;
import com.campssg.dto.requestedProduct.GetRequestedProductDto;
import com.campssg.dto.requestedProduct.GuestRequestDto;
import com.campssg.util.SecurityUtil;
import com.google.firebase.messaging.FirebaseMessagingException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final FcmService fcmService;

    // 요청 상품 추가
    public RequestedProduct addRequestedProduct(AddRequestedProductDto addRequestedProductDto)
        throws FirebaseMessagingException {
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

        order.updateStatus(OrderState.가격흥정중);

        // 물품 요청 시, 마트 운영자 -> 요청 상품에 대한 메시지
        fcmService.sendMulticast(
            new PushMessage("[" + order.getMart().getMartName() + "]", "새로운 물품요청이 들어왔어요! 확인해주세요!"),
            Arrays.asList(order.getMart().getUser().getAccessToken()));

        return requestedProduct;
    }

    // 서비스 이용자가 최초로 요청상품 등록 및 가격 제시
    public GuestRequestDto guestRequestProduct(AddRequestedProductDto addRequestedProductDto)
        throws FirebaseMessagingException {
        RequestedProduct requestedProduct = addRequestedProduct(addRequestedProductDto);
        return new GuestRequestDto(requestedProduct);
    }

    // 마트 운영자가 가격 요청 중인 요청상품 조회
    public List<GetRequestedProductDto> getRequestedProductFromMart(Long martId) {
        List<RequestedProduct> requestedProducts = requestedProductRepository.findByMart_martIdAndRequestedProductState(martId, RequestedProductState.가격요청중);
        return requestedProducts.stream().map(requestedProduct -> new GetRequestedProductDto(requestedProduct)).collect(Collectors.toList());
    }

    // 서비스 이용자가 가격 제시 중인 요청 상품 조회
    public List<GetRequestedProductDto> getRequestedProductFromGuest() {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow();
        List<RequestedProduct> requestedProducts = requestedProductRepository.findByUser_userIdAndRequestedProductState(user.getUserId(), RequestedProductState.가격제시중);
        return requestedProducts.stream().map(requestedProduct -> new GetRequestedProductDto(requestedProduct)).collect(Collectors.toList());
    }

    // 서비스 이용자 요청 상품 상태에 따른 조회
    public List<GetRequestedProductDto> getRequestedProductByState(String orderState) {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow();
        List<RequestedProduct> requestedProducts = requestedProductRepository.findByUser_userIdAndRequestedProductState(user.getUserId(), RequestedProductState.valueOf(orderState));
        return requestedProducts.stream().map(requestedProduct -> new GetRequestedProductDto(requestedProduct)).collect(Collectors.toList());
    }

    // 마트 운영자 요청 상품 상태에 따른 조회
    public List<GetRequestedProductDto> getRequestedProductByState(Long martId, String orderState) {
        List<RequestedProduct> requestedProducts = requestedProductRepository.findByMart_martIdAndRequestedProductState(martId, RequestedProductState.valueOf(orderState));
        return requestedProducts.stream().map(requestedProduct -> new GetRequestedProductDto(requestedProduct)).collect(Collectors.toList());
    }

    // 마트 운영자가 모든 요청상품 조회
    public List<GetRequestedProductDto> getTotalRequestedProductFromMart(Long martId) {
        List<RequestedProduct> requestedProducts = requestedProductRepository.findByMart_martId(martId);
        return requestedProducts.stream().map(requestedProduct -> new GetRequestedProductDto(requestedProduct)).collect(Collectors.toList());
    }

    //서비스 이용자가 모든 요청상품 조회
    public List<GetRequestedProductDto> getTotalRequestedProductFromGuest() {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow();
        List<RequestedProduct> requestedProducts = requestedProductRepository.findByUser_userId(user.getUserId());
        return requestedProducts.stream().map(requestedProduct -> new GetRequestedProductDto(requestedProduct)).collect(Collectors.toList());
    }

    // 승인 버튼을 눌러서 가격 흥정 마무리
    public void acceptedProduct(Long requestedProductId) throws FirebaseMessagingException {
        RequestedProduct requestedProduct = requestedProductRepository.findByRequestedProductId(requestedProductId);
        requestedProduct.setRequestedProductState(RequestedProductState.흥정완료);
        Order order = orderRepository.findByOrderId(requestedProduct.getOrder().getOrderId());
        order.setOrderState(OrderState.결제대기중);
        order.setTotalPrice(requestedProduct.getRequestedProductPrice()+order.getTotalPrice());
        orderRepository.save(order);

        // 서비스이용자 -> 요청상품 승인 완료
        fcmService.sendMulticast(
            new PushMessage("요청상품(" + requestedProduct.getRequestedProductName() +")이 승인되었어요!", "결제를 완료해주세요!"),
            Arrays.asList(order.getUser().getAccessToken()));
    }

    // 마트 측에서 가격 제시
    public void requestPriceToGuest(Long requestedProductId, int price) throws FirebaseMessagingException {
        RequestedProduct requestedProduct = requestedProductRepository.findByRequestedProductId(requestedProductId);
        requestedProduct.setRequestedProductPrice(price);
        requestedProduct.setRequestedProductState(RequestedProductState.가격제시중);

        // 서비스이용자 -> 마트운영자가 가격제시한 메세지
        fcmService.sendMulticast(
            new PushMessage(requestedProduct.getMart().getMartName() + "이 가격을 제안했어요!", "확인해주세요!"),
            Arrays.asList(requestedProduct.getUser().getAccessToken()));

        requestedProductRepository.save(requestedProduct);
    }

    // 사용자 측에서 가격 요청
    public void requestPriceToMart(Long requestedProductId, int price) throws FirebaseMessagingException {
        RequestedProduct requestedProduct = requestedProductRepository.findByRequestedProductId(requestedProductId);
        requestedProduct.setRequestedProductPrice(price);
        requestedProduct.setRequestedProductState(RequestedProductState.가격요청중);

        // (가격흥정시), 마트운영자 -> 서비스이용자가 가격요청한 메시지
        fcmService.sendMulticast(
            new PushMessage(requestedProduct.getUser().getUserName() + "님이 가격을 다시 요청했어요!", "확인해주세요!"),
            Arrays.asList(requestedProduct.getMart().getUser().getAccessToken()));

        requestedProductRepository.save(requestedProduct);
    }
}
