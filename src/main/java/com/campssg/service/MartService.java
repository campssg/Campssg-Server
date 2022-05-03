package com.campssg.service;

import com.campssg.DB.entity.Category;
import com.campssg.DB.entity.Mart;
import com.campssg.DB.entity.Product;
import com.campssg.DB.entity.User;
import com.campssg.DB.repository.CategoryRepository;
import com.campssg.DB.repository.MartRepository;
import com.campssg.DB.repository.ProductRepository;
import com.campssg.DB.repository.UserRepository;
import com.campssg.common.OpenApi;
import com.campssg.common.S3Uploder;
import com.campssg.dto.mart.MartAuthRequestDto;
import com.campssg.dto.mart.MartCertificationRequestDto;
import com.campssg.dto.mart.MartListResponseDto;
import com.campssg.dto.mart.MartSaveRequestDto;
import com.campssg.dto.mart.ProductListResponse;
import com.campssg.dto.mart.ProductListSaveRequest;
import com.campssg.dto.mart.ProductSaveRequest;
import com.campssg.util.SecurityUtil;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
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

        martRepository.save(requestDto.toEntity(user));
    }

    public boolean authMart(MartAuthRequestDto requestDto) {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow();

        return openApi.martValidationOpenApi(
            MartCertificationRequestDto.builder()
                .bNo(requestDto.getBNo())
                .pNm(user.getUserName())
                .startDt(requestDto.getStartDt())
                .build());
    }

    public List<MartListResponseDto> findByUserId(String martName) {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow();
        List<Mart> martList;
        if (martName != null) {
            martList = martRepository.findByUser_userIdAndmAndMartNameContaining(user.getUserId(), martName);
        } else {
            martList = martRepository.findByUser_userId(user.getUserId());
        }
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
        for (int i = 0; i < checklistProducts.size(); i++) {
            ProductListSaveRequest productListSaveRequest = checklistProducts.get(i);
            Category category = categoryRepository.findByCategoryId(productListSaveRequest.getCategoryId());
            if (productListSaveRequest.getProductStock() == 0) {
                productRepository.save(new Product(category, mart, productListSaveRequest.getProductName(),
                    productListSaveRequest.getProductPrice(), 0, productListSaveRequest.getProductImgUrl()));
            } else {
                productRepository.save(new Product(category, mart, productListSaveRequest.getProductName(),
                    productListSaveRequest.getProductPrice(), productListSaveRequest.getProductStock(),
                    productListSaveRequest.getProductImgUrl()));
            }
        }
    }

    public ProductListResponse findProductByMartId(Long martId) {
        List<Product> findProductByMartId = productRepository
            .findByMart_martId(martId);

        List<ProductListResponse.ProductList> productLists = findProductByMartId.stream()
            .map(product -> new ProductListResponse().new ProductList(product)).collect(Collectors.toList());
        return new ProductListResponse(productLists, null);
    }

    public ProductListResponse findProductByMartIdAndKeyword(Long martId, String productName) {
        List<Product> productListByMart;

        if (productName == null) {
            productListByMart = productRepository
                .findByMart_martId(martId);
        } else {
            productListByMart = productRepository
                .findByMart_martIdAndProductNameContains(martId, productName);
        }

        List<ProductListResponse.ProductList> productLists = productListByMart.stream()
            .map(product -> new ProductListResponse().new ProductList(product)).collect(Collectors.toList());
        return new ProductListResponse(productLists, null);
    }

    // 카테고리별로 마트 상품 조회
    public ProductListResponse findProductByCategory(Long martId, Long categoryId) {
        List<Product> products = productRepository.findByMart_martIdAndCategory_categoryId(martId, categoryId);
        List<ProductListResponse.ProductList> productLists = products.stream()
            .map(product -> new ProductListResponse().new ProductList(product)).collect(Collectors.toList());
        Category category = categoryRepository.findByCategoryId(categoryId);
        return new ProductListResponse(productLists, category);
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
