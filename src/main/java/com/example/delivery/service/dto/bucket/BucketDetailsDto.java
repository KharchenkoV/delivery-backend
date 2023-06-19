package com.example.delivery.service.dto.bucket;

import com.example.delivery.dao.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BucketDetailsDto {
    private String title;
    private Long productId;
    private String picture;
    private BigDecimal price;
    private Integer amount;
    private Double sum;

    public BucketDetailsDto(Product product){
        this.title = product.getTitle();
        this.productId = product.getId();
        this.price = product.getPrice();
        this.amount = 1;
        this.sum = product.getPrice().doubleValue();
        this.picture = product.getPicture();
    }
}
