package com.ducbn.shopapp.responses;

import com.ducbn.shopapp.models.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListResponse {
    private List<ProductResponse> products;
    private int totalPages;
}
