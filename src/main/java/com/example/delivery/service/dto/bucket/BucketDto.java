package com.example.delivery.service.dto.bucket;

import com.example.delivery.dao.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BucketDto {
    private Integer amountProducts;
    private Double sum;
    private List<BucketDetailsDto> bucketDetailsList = new ArrayList<>();

    public static BucketDto mapProductsToBucketDto(List<Product> productList){
        Map<Long, BucketDetailsDto> bucketDetailsMap = new HashMap<>();
        productList.forEach(product -> {
            BucketDetailsDto bucketDetails = bucketDetailsMap.get(product.getId());
            if (bucketDetails == null){
                bucketDetailsMap.put(product.getId(), new BucketDetailsDto(product));
            } else {
                bucketDetails.setAmount(bucketDetails.getAmount() + 1);
                bucketDetails.setSum(bucketDetails.getSum() + product.getPrice().doubleValue());
            }
        });
        return BucketDto.builder()
                .bucketDetailsList(new ArrayList<>(bucketDetailsMap.values()))
                .build()
                .aggregate();
    }

    private BucketDto aggregate(){
        this.amountProducts = bucketDetailsList.size();
        this.sum = bucketDetailsList.stream()
                .map(BucketDetailsDto::getSum)
                .mapToDouble(Double::doubleValue)
                .sum();
        return this;
    }
}
