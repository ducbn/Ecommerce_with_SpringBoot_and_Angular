package com.ducbn.shopapp.responses;

import com.ducbn.shopapp.models.Product;
import com.ducbn.shopapp.models.ProductImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Builder
public class ProductResponseImage extends BaseResponse{
    private String name;
    private Float price;
    private String thumbnail;
    private String description;

    @JsonProperty("product_images")
    private List<ProductImageResponse> productImages;

    @JsonProperty("category_id")
    private Long categoryId;

    public static ProductResponseImage fromProductImage(Product product) {
        List<ProductImageResponse> productImageResponses = new ArrayList<>();
        if (product.getProductImages() != null) {
            for (ProductImage image : product.getProductImages()) {
                productImageResponses.add(ProductImageResponse.fromProductImage(image));
            }
        }

        ProductResponseImage productResponseImage = ProductResponseImage.builder()
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .productImages(productImageResponses)
                .build();
        productResponseImage.setCreatedAt(product.getCreatedAt());
        productResponseImage.setUpdatedAt(product.getUpdatedAt());
        return productResponseImage;
    }
}
