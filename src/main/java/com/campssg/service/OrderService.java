package com.campssg.service;

import com.campssg.DB.entity.Cart;
import com.campssg.DB.entity.CartItem;
import com.campssg.DB.entity.Mart;
import com.campssg.DB.entity.Order;
import com.campssg.DB.entity.OrderItem;
import com.campssg.DB.entity.OrderState;
import com.campssg.DB.entity.Product;
import com.campssg.DB.entity.RequestedProduct;
import com.campssg.DB.entity.User;
import com.campssg.DB.repository.CartItemRepository;
import com.campssg.DB.repository.CartRepository;
import com.campssg.DB.repository.OrderItemRepository;
import com.campssg.DB.repository.OrderRepository;
import com.campssg.DB.repository.RequestedProductRepository;
import com.campssg.DB.repository.UserRepository;
import com.campssg.common.S3Uploder;
import com.campssg.dto.order.MartOrderListResponseDto;
import com.campssg.dto.order.OrderDetailResponseDto;
import com.campssg.dto.order.OrderItemList;
import com.campssg.dto.order.OrderRequestDto;
import com.campssg.dto.order.OrderResponseDto;
import com.campssg.dto.order.RequestedProductList;
import com.campssg.dto.order.UserOrderListResponseDto;
import com.campssg.dto.push.PushMessage;
import com.campssg.util.SecurityUtil;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final S3Uploder s3Uploder;
    private final RequestedProductRepository requestedProductRepository;
    private final FcmService fcmService;

    // ?????? ??? ????????? ???????????? ?????? ?????? ??????
    public OrderResponseDto addOrderInfo(OrderRequestDto orderRequestDto) throws IOException, WriterException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime reservedDate = LocalDateTime.parse(orderRequestDto.getReservedDate() + " 00:00:00", formatter);

        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail)
            .orElseThrow(); // ?????? ??????????????? ?????? ????????? ?????? ????????????
        Cart cart = cartRepository.findByUser_userId(user.getUserId()).orElseThrow();
        List<CartItem> cartItemList = cartItemRepository.findByCart_cartId(cart.getCartId()); // ??????????????? ?????? ?????? ?????? ????????????
        Mart mart = cartItemList.get(0).getProduct().getMart(); // cartItem?????? ?????? ?????? ????????????
        Order order = addOrder(user, mart, cart, reservedDate, orderRequestDto.getReservedTime());
        List<OrderItemList> orderItemLists = addOrderItem(cartItemList,
            order); // cartItemList??? ?????? ?????? ?????? orderItemList??? ?????????
        cart.setTotalCount(0);
        cart.setTotalPrice(0);
        cartRepository.save(cart);
        return new OrderResponseDto(order, orderItemLists);
    }

    // ??????????????? ?????? ?????? ?????? ??????
    public OrderDetailResponseDto getOrderInfo(Long orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        List<OrderItem> orderItemList = orderItemRepository.findByOrder_orderId(orderId);
        List<OrderItemList> orderItemLists = orderItemList.stream().map(orderItem -> new OrderItemList(orderItem))
            .collect(Collectors.toList());
        List<RequestedProduct> requestedProducts = requestedProductRepository.findByOrder_orderId(orderId).orElse(null);
        if (requestedProducts.isEmpty()) {
            return new OrderDetailResponseDto(order, orderItemLists, null);
        } else {
            List<RequestedProductList> requestedProductLists = requestedProducts.stream()
                .map(requestedProduct -> new RequestedProductList(requestedProduct)).collect(Collectors.toList());
            return new OrderDetailResponseDto(order, orderItemLists, requestedProductLists);
        }
    }

    // ????????? ?????? ?????? ??????(?????? ??????)
    public List<UserOrderListResponseDto> getUserOrderList() {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail)
            .orElseThrow(); // ?????? ??????????????? ?????? ????????? ?????? ????????????
        List<Order> orderList = orderRepository.findByUser_userIdOrderByCreatedAtDesc(user.getUserId());
        return orderList.stream().map(order -> new UserOrderListResponseDto(order)).collect(Collectors.toList());
    }

    // ?????? id??? ?????? ?????? ??????
    public List<MartOrderListResponseDto> getMartOrderList(Long martId) {
        List<Order> orderList = orderRepository.findByMart_martId(martId);
        return orderList.stream().map(order -> new MartOrderListResponseDto(order)).collect(Collectors.toList());
    }

    // ????????????????????? ?????? ?????? ??????
    public List<UserOrderListResponseDto> getPreparedOrderList() {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail)
            .orElseThrow(); // ?????? ??????????????? ?????? ????????? ?????? ????????????
        List<Order> orderList = orderRepository.findByUser_userIdAndOrderState(user.getUserId(), OrderState.??????????????????);
        return orderList.stream().map(order -> new UserOrderListResponseDto(order)).collect(Collectors.toList());
    }

    // ????????? ????????? ?????? ????????? ?????? ?????? ?????? ??????
    public List<UserOrderListResponseDto> getOrderListByState(String orderState) {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow();
        List<Order> orderList = orderRepository
            .findByUser_userIdAndOrderState(user.getUserId(), OrderState.valueOf(orderState));
        return orderList.stream().map(order -> new UserOrderListResponseDto(order)).collect(Collectors.toList());
    }

    // ?????? ????????? ?????? ????????? ?????? ?????? ?????? ??????
    public List<MartOrderListResponseDto> getOrderListByState(Long martId, String orderState) {
        List<Order> orderList = orderRepository.findByMart_martIdAndOrderState(martId, OrderState.valueOf(orderState));
        return orderList.stream().map(order -> new MartOrderListResponseDto(order)).collect(Collectors.toList());
    }

    // ????????? ??????
    public Order addOrder(User user, Mart mart, Cart cart, LocalDateTime dateTime, String reservedTime)
        throws IOException, WriterException {
        int charge = setCostCharge(cart.getTotalPrice()) + setPeriodCharge(dateTime, LocalDateTime.now());

        Order order = orderRepository.save(Order.builder()
            .orderId(setOrderNumber())
            .mart(mart)
            .user(user)
            .reservedDate(dateTime)
            .reservedTime(reservedTime)
            .orderState(OrderState.????????????)
            .charge(charge)
            .totalPrice(cart.getTotalPrice() + charge)
            .build());

        createQrImg(order);
        return order;
    }

    // ???????????? ?????? ??????
    public List<OrderItemList> addOrderItem(List<CartItem> cartItemList, Order order) {
        List<OrderItemList> orderItemLists = new ArrayList<>();
        for (int i = 0; i < cartItemList.size(); i++) {
            CartItem cartItem = cartItemList.get(i);
            Product product = cartItem.getProduct();
            product.subProductStock(cartItem);
            OrderItem orderItem = orderItemRepository.save(OrderItem.builder()
                .order(order)
                .product(product)
                .orderItemCount(cartItem.getCartItemCount())
                .build());
            cartItemRepository.delete(cartItem);
            OrderItemList orderItemList = new OrderItemList(orderItem);
            orderItemLists.add(orderItemList);
        }
        return orderItemLists;
    }

    // ???????????? ??????
    public Long setOrderNumber() {
        // ?????? ????????? ??????
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        // ????????? ?????? ??????
        Random random = new Random();
        int number = 0; // 1?????? ??????
        String stringNumber = ""; //1?????? ????????? String ?????? ?????????
        String resultNumber = ""; // ??????????????? ?????? 3?????? ?????? string
        for (int i = 0; i < 3; i++) {
            number = random.nextInt(9);
            stringNumber = Integer.toString(number);
            resultNumber += stringNumber;
        }
        String orderNumber = dateTime + resultNumber; // ??????????????? ??????, 3?????? ????????? ?????? ?????? ??????

        return Long.parseLong(orderNumber);
    }

    // ????????? ?????? ?????????
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

    // ?????? ????????? ?????? ?????????(?????? ?????? ?????? - ?????? ?????? ??????)
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

    public void updateOrderStatus(Long orderId, String status) throws FirebaseMessagingException {
        Order order = orderRepository.findByOrderId(orderId);
        order.updateStatus(OrderState.valueOf(status));

        pushMessageByOrderState(order, status);
    }

    private void pushMessageByOrderState(Order order, String status) throws FirebaseMessagingException {
        if (status.equals(OrderState.????????????.toString())) {
            // ?????? ??????????????? ???????????? ??????
            fcmService.sendMulticast(
                new PushMessage(order.getMart().getMartName(), "????????? ??????????????????! ??????????????????!"),
                Arrays.asList(order.getMart().getUser().getAccessToken()));
        }

        // ?????? ???????????? ???????????? ??????
        fcmService.sendMulticast(
            new PushMessage(order.getMart().getMartName() + "??? " + status + "??? ???????????????!",
                "?????????????????? ????????? ????????? ??????????????????!"),
            Arrays.asList(order.getUser().getAccessToken()));
    }

    private void createQrImg(Order order) throws IOException, WriterException, WriterException {
        String url = order.getOrderId().toString();
        String codeurl = new String(url.getBytes("UTF-8"), "ISO-8859-1");

        // QRCode ?????????
        int qrcodeColor = 0xff080606;
        // QRCode ???????????????
        int backgroundColor = 0xFFFFFFFF;

        //QRCode ??????
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter
            .encode(codeurl, BarcodeFormat.QR_CODE, 200, 200);    // 200,200??? width, height

        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor, backgroundColor);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(bufferedImage, "png", baos);
        baos.flush();

        MultipartFile multipartFile = new MockMultipartFile("qrcode", "qrcode", "image/jpg", baos.toByteArray());
        String qrcodeUrl = s3Uploder.upload(multipartFile, "qr");

        order.updateQrcodeUrl(qrcodeUrl);
    }
}
