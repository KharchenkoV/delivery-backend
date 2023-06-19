package com.example.delivery.service.service;

import com.example.delivery.dao.entity.Bucket;
import com.example.delivery.dao.entity.Product;
import com.example.delivery.dao.entity.User;
import com.example.delivery.dao.repository.BucketRepository;
import com.example.delivery.dao.repository.ProductRepository;
import com.example.delivery.dao.repository.UserRepository;
import com.example.delivery.service.BucketService;
import com.example.delivery.service.dto.bucket.BucketDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BucketServiceImpl implements BucketService {
    private final ProductRepository productRepository;
    private final BucketRepository bucketRepository;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public Bucket createBucket(User user, List<Long> productIds) {
        List<Product> productList = productRepository.findAllById(productIds);
        Bucket bucket = Bucket.builder()
                .products(productList)
                .user(user)
                .build();
        return bucketRepository.save(bucket);
    }

    @Override
    @Transactional
    public void addProductsToBucket(Bucket bucket, List<Long> productIds) {
        List<Product> productList = bucket.getProducts();
        if (productList == null)
            productList = new ArrayList<>();
        productList.addAll(productRepository.findAllById(productIds));
        bucket.setProducts(productList);
        bucketRepository.save(bucket);
    }

    @Override
    public BucketDto getBucketByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));
        return BucketDto.mapProductsToBucketDto(user.getBucket().getProducts());
    }

    @Override
    @Transactional
    public void deleteProductById(Bucket bucket, Long productId) {
        List<Product> productList = bucket.getProducts();
        for (Product product: productList){
            if(productId.equals(product.getId())){
                productList.remove(product);
                break;
            }
        }
        bucket.setProducts(productList);
        bucketRepository.save(bucket);
    }

    @Override
    @Transactional
    public void deleteAllProductsById(Bucket bucket, Long productId) {
        List<Product> productList = bucket.getProducts();
        productList.removeIf(product -> productId.equals(product.getId()));
        bucket.setProducts(productList);
        bucketRepository.save(bucket);
    }

    @Override
    @Transactional
    public void cleanBucketByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));
        Bucket bucket = user.getBucket();
        if (bucket != null){
            bucket.setProducts(null);
            bucketRepository.save(bucket);
        }
    }
}
