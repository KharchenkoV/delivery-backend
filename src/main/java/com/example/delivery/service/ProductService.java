package com.example.delivery.service;

import com.example.delivery.service.dto.product.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAll();
    ProductDto getProductById(Long productId);
    void addProductToBucket(Long productId, String email);
    void deleteProductFromBucket(Long productId, String email);
    void deleteAllProductFromBucket(Long productId, String email);
}
