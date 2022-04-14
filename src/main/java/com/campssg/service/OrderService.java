package com.campssg.service;

import com.campssg.DB.entity.*;
import com.campssg.DB.repository.*;
import com.campssg.dto.order.OrderItemList;
import com.campssg.dto.order.OrderRequestDto;
import com.campssg.dto.order.OrderResponseDto;
import com.campssg.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    // 주문 시 주문서 생성하고 주문 정보 반환
    public OrderResponseDto getOrderInfo(OrderRequestDto orderRequestDto) {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow(); // 현재 로그인하고 있는 사용자 정보 가져오기
        Cart cart = cartRepository.findByUser_userId(user.getUserId()).orElseThrow();
        List<CartItem> cartItemList = cartItemRepository.findByCart_cartId(cart.getCartId()); // 장바구니에 있는 상품 목록 가져오기
        Mart mart = cartItemList.get(0).getProduct().getMart(); // cartItem에서 마트 정보 가져오기
        Order order = addOrder(user, mart, cart, orderRequestDto);
        List<OrderItemList> orderItemLists = addOrderItem(cartItemList, order); // cartItemList에 있는 상품 목록 orderItemList로 옮기기

        return new OrderResponseDto(order, orderItemLists);
    }

    // 주문서 생성
    public Order addOrder(User user, Mart mart, Cart cart, OrderRequestDto orderRequestDto) {
        int charge = setCostCharge(cart.getTotalPrice())+setPeriodCharge(orderRequestDto.getReservedAt(), LocalDateTime.now());
        Order order = orderRepository.save(Order.builder()
                .orderId(setOrderNumber())
                .mart(mart)
                .user(user)
                .reservedAt(orderRequestDto.getReservedAt())
                .orderState(OrderState.주문완료)
                .charge(charge)
                .totalPrice(cart.getTotalPrice()+charge)
                .build());
        return order;
    }

    // 주문서에 상품 추가
    public List<OrderItemList> addOrderItem(List<CartItem> cartItemList, Order order) {
        List<OrderItemList> orderItemLists = new ArrayList<>();
        for (int i=0; i<cartItemList.size(); i++) {
            CartItem cartItem = cartItemList.get(i);
            OrderItem orderItem = orderItemRepository.save(OrderItem.builder()
                    .order(order)
                    .product(cartItem.getProduct())
                    .orderItemCount(cartItem.getCartItemCount())
                    .build());
            OrderItemList orderItemList = new OrderItemList(orderItem);
            orderItemLists.add(orderItemList);
        }
        return orderItemLists;
    }

    // 주문번호 생성
    public Long setOrderNumber() {
        // 주문 날짜와 시간
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        // 세자리 난수 생성
        Random random = new Random();
        int number = 0; // 1자리 난수
        String stringNumber = ""; //1자리 난수를 String 으로 형변환
        String resultNumber = ""; // 최종적으로 만들 3자리 난수 string
        for (int i = 0; i < 3; i++) {
            number = random.nextInt(9);
            stringNumber = Integer.toString(number);
            resultNumber += stringNumber;
        }
        String orderNumber = dateTime + resultNumber; // 주문날짜와 시간, 3자리 난수를 합친 주문 번호

        return Long.parseLong(orderNumber);
    }

    // 가격에 따른 수수료
    public int setCostCharge(int cartItemPrice) {
        int charge;
        if (cartItemPrice < 50000) {
            charge = 0;
        } else if (cartItemPrice < 100000) {
            charge = 1000;
        } else if (cartItemPrice < 150000) {
            charge = 1500;
        } else {
            charge = 2000;
        }
        return charge;
    }

    // 예약 기간에 따른 수수료(픽업 예약 날짜 - 주문 날짜 간격)
    public int setPeriodCharge(LocalDateTime reservedAt, LocalDateTime createdAt) {
        long day = ChronoUnit.DAYS.between(createdAt, reservedAt);
        int charge;
        if (day < 1) {
            charge = 0;
        } else if (day < 7) {
            charge = 1000;
        } else if (day < 14) {
            charge = 1500;
        } else {
            charge = 2000;
        }
        return charge;
    }
}
