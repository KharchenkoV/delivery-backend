package com.example.delivery.service;

import com.example.delivery.dao.entity.Bucket;
import com.example.delivery.dao.entity.User;
import com.example.delivery.service.dto.bucket.BucketDto;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productIds);
    void addProductsToBucket(Bucket bucket, List<Long> productIds);
    BucketDto getBucketByEmail(String email);
    void deleteProductById(Bucket bucket, Long productId);
    void deleteAllProductsById(Bucket bucket, Long productId);
    void cleanBucketByEmail(String email);
}
