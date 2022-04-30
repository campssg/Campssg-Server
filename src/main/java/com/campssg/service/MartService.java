package com.campssg.service;

import com.campssg.DB.entity.*;
import com.campssg.DB.repository.CategoryRepository;
import com.campssg.DB.repository.MartRepository;
import com.campssg.DB.repository.ProductRepository;
import com.campssg.DB.repository.UserRepository;
import com.campssg.common.OpenApi;
import com.campssg.common.S3Uploder;
import com.campssg.dto.mart.*;
import com.campssg.util.SecurityUtil;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class MartService {

    private final MartRepository martRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final OpenApi openApi;

    private final S3Uploder s3Uploder;

    public void saveMart(MartSaveRequestDto requestDto) {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow();
        boolean isValidate = openApi.martValidationOpenApi(
            MartCertificationRequestDto.builder()
                .bNo(requestDto.getBNo())
                //TODO: 로그인 한 사용자 이름 받아오기
                .pNm(((User) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal()).getUserName())
                .startDt(requestDto.getStartDt())
                .build());
        if (isValidate) {
            martRepository.save(requestDto.toEntity(user));
        } else {
            throw new IllegalArgumentException("마트 인증에 실패하였습니다.");
        }
    }

    public List<MartListResponseDto> findByUserId() {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow();
        List<Mart> martList = martRepository.findByUser_userId(user.getUserId());
        return martList.stream().map(mart -> new MartListResponseDto(mart)).collect(Collectors.toList());
    }

    // 위치 기반 마트 검색
    public List<MartListResponseDto> searchAroundMart(Double latitude, Double longitude) {
        List<Mart> aroundMart = martRepository.findAroundMart(latitude, longitude);
        return aroundMart.stream().map(mart -> new MartListResponseDto(mart)).collect(Collectors.toList());
    }

    public void saveProductToMart(ProductSaveRequest requestDto, MultipartFile file) throws IOException {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow();
        String imgUrl = file == null ? null : s3Uploder.upload(file, "product");
        requestDto.setProductImgUrl(imgUrl);
        productRepository.save(requestDto.toEntity());
    }

    // 물품 리스트 마트에 등록
    public void saveProductListToMart(List<ProductListSaveRequest> checklistProducts, Long martId) {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow();
        Mart mart = martRepository.findByMartId(martId);
        for (int i=0; i<checklistProducts.size(); i++) {
            ProductListSaveRequest productListSaveRequest  = checklistProducts.get(i);
            Category category = categoryRepository.findByCategoryId(productListSaveRequest.getCategoryId());
            if (productListSaveRequest.getProductStock() == 0) {
                productRepository.save(new Product(category, mart, productListSaveRequest.getProductName(),
                        productListSaveRequest.getProductPrice(), 0, productListSaveRequest.getProductImgUrl()));
            } else {
                productRepository.save(new Product(category, mart, productListSaveRequest.getProductName(),
                        productListSaveRequest.getProductPrice(), productListSaveRequest.getProductStock(), productListSaveRequest.getProductImgUrl()));
            }
        }
    }

    public ProductListResponse findProductByMartId(Long martId) {
        List<Product> productListByMart = productRepository.findByMart_martId(martId);

        List<ProductListResponse.ProductList> productLists = productListByMart.stream()
            .map(product -> new ProductListResponse().new ProductList(product)).collect(Collectors.toList());
        return new ProductListResponse(productLists);
    }

    public void addProductStock(Long productId, int count) { // 기존에 있는 마트 상품 재고만 추가
        Product product = productRepository.findByProductId(productId);
        if (product != null) { // 마트에 상품이 존재할 경우 재고 추가
            product.setProductStock(product.getProductStock() + count);
            productRepository.save(product);
        }
    }

    public void deleteProduct(Long productId) { // 마트에 있는 상품 삭제
        Product product = productRepository.findByProductId(productId);
        if (product != null) { // 마트에 상품이 존재할 경우 삭제
            productRepository.delete(product);
        }
    }
}
