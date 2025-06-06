package com.ducbn.shopapp.services;
import com.ducbn.shopapp.dtos.ProductDTO;
import com.ducbn.shopapp.dtos.ProductImageDTO;
import com.ducbn.shopapp.exceptions.DataNotFoundException;
import com.ducbn.shopapp.models.Product;
import com.ducbn.shopapp.models.ProductImage;
import com.ducbn.shopapp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;


public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws DataNotFoundException;

    Product getProductById(long id) throws Exception;

    Page<ProductResponse> getAllProducts(String keyword ,Long categoryId ,PageRequest pageRequest);

    List<Product> findProductsByIds(List<Long> productIds);

    Product updateProduct(long id, ProductDTO productDTO) throws Exception;

    void deleteProduct(long id);

    boolean existsByName(String name);

    ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception;

    //ProductImage getProductImage(long productImageId) throws Exception;
}
