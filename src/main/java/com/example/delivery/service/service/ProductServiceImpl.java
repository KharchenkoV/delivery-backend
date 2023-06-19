package com.example.delivery.service.service;

import com.example.delivery.dao.entity.Bucket;
import com.example.delivery.dao.entity.User;
import com.example.delivery.dao.repository.ProductRepository;
import com.example.delivery.dao.repository.UserRepository;
import com.example.delivery.service.BucketService;
import com.example.delivery.service.ProductService;
import com.example.delivery.service.dto.product.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final UserRepository userRepository;
    private final BucketService bucketService;
    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> getAll() {
        return ProductDto.fromProductList(productRepository.findAll());
    }

    @Override
    public ProductDto getProductById(Long productId) {
        return ProductDto.fromProduct(productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product hasn't found with id: " + productId)));
    }

    @Override
    @Transactional
    public void addProductToBucket(Long productId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));
        Bucket bucket = user.getBucket();
        if (bucket == null){
            Bucket newBucket = bucketService.createBucket(user, List.of(productId));
            user.setBucket(newBucket);
            userRepository.save(user);
        } else {
            bucketService.addProductsToBucket(bucket, List.of(productId));
        }
    }

    @Override
    public void deleteProductFromBucket(Long productId, String email) {
        Bucket bucket = loadBucketByEmail(email);
        if (bucket != null){
            bucketService.deleteProductById(bucket, productId);
        }
    }

    @Override
    public void deleteAllProductFromBucket(Long productId, String email) {
        Bucket bucket = loadBucketByEmail(email);
        if (bucket != null){
            bucketService.deleteAllProductsById(bucket, productId);
        }
    }

    private Bucket loadBucketByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));
        return user.getBucket();
    }
}
