package com.ducbn.shopapp.responses;

import com.ducbn.shopapp.models.ProductImage;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageResponse {
    private String imageUrl;

    public static ProductImageResponse fromProductImage(ProductImage productImage) {
        return ProductImageResponse.builder()
                .imageUrl(productImage.getImageUrl())
                .build();
    }
}
