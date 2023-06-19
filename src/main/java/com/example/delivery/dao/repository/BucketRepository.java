package com.example.delivery.dao.repository;

import com.example.delivery.dao.entity.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
}
