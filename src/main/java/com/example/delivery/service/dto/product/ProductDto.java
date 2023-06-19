package com.example.delivery.service.dto.product;

import com.example.delivery.dao.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    private String title;
    private BigDecimal price;
    private String picture;
    private String description;

    public static List<ProductDto> fromProductList(List<Product> productList) {
        return productList.stream()
                .map(product -> {
                    return ProductDto.builder()
                            .id(product.getId())
                            .picture(product.getPicture())
                            .price(product.getPrice())
                            .title(product.getTitle())
                            .description(product.getDescription())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public static ProductDto fromProduct(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .picture(product.getPicture())
                .price(product.getPrice())
                .title(product.getTitle())
                .description(product.getDescription())
                .build();
    }
}
