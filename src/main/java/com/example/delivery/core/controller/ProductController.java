package com.example.delivery.core.controller;

import com.example.delivery.service.ProductService;
import com.example.delivery.service.dto.product.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/{id}/bucket")
    public ResponseEntity<HttpStatus> addToBucket(@PathVariable Long id, Principal principal){
        productService.addProductToBucket(id, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/{id}/delete/product/from/bucket")
    public ResponseEntity<HttpStatus> deleteProductFromBucket(@PathVariable Long id, Principal principal){
        productService.deleteProductFromBucket(id, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}/delete/all/product/from/bucket")
    public ResponseEntity<HttpStatus> deleteAllProductFromBucket(@PathVariable Long id, Principal principal){
        productService.deleteAllProductFromBucket(id, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
