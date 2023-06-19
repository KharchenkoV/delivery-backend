package com.example.delivery.core.controller;

import com.example.delivery.service.BucketService;
import com.example.delivery.service.dto.bucket.BucketDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/bucket")
@RequiredArgsConstructor
public class BucketController {
    private final BucketService bucketService;

    @GetMapping
    public ResponseEntity<BucketDto> getBucket(Principal principal){
        return ResponseEntity.ok().body(bucketService.getBucketByEmail(principal.getName()));
    }

    @GetMapping("/clean")
    public ResponseEntity<HttpStatus> cleanBucket(Principal principal){
        bucketService.cleanBucketByEmail(principal.getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
