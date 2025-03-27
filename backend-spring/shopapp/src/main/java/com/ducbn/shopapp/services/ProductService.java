package com.ducbn.shopapp.services;

import com.ducbn.shopapp.dtos.ProductDTO;
import com.ducbn.shopapp.dtos.ProductImageDTO;
import com.ducbn.shopapp.exceptions.DataNotFoundException;
import com.ducbn.shopapp.exceptions.InvalidParamException;
import com.ducbn.shopapp.models.Category;
import com.ducbn.shopapp.models.Product;
import com.ducbn.shopapp.models.ProductImage;
import com.ducbn.shopapp.repositories.CategoryRepository;
import com.ducbn.shopapp.repositories.ProductImageRepository;
import com.ducbn.shopapp.repositories.ProductRepository;
import com.ducbn.shopapp.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category existingCategory = categoryRepository
                .findById(productDTO.getCategory_id())
                .orElseThrow(()-> new DataNotFoundException("Cannot find category with id" + productDTO.getCategory_id()));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .description(productDTO.getDescription())
                .category(existingCategory)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(long productId) throws Exception{
        return productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with id = "+ productId));
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        // lấy danh sách sản phẩm theo trang(page) và giới hạn (limit)
        return productRepository
                .findAll(pageRequest)
                .map(ProductResponse::fromProduct);
    }


    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws Exception{
        Product existingProduct = getProductById(id);
        if(existingProduct != null) {
            //copy cac thuoc tinh tu DTO -> Product
            Category existingCategory = categoryRepository
                    .findById(productDTO.getCategory_id())
                    .orElseThrow(()-> new DataNotFoundException("Cannot find category with id" + productDTO.getCategory_id()));
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception {
        Product existtingProduct = productRepository
                .findById(productId)
                .orElseThrow(()-> new DataNotFoundException("Cannot find product with id: "+productImageDTO.getProductId()));
        ProductImage newProductImage = ProductImage.builder()
                .product(existtingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();

        //khong cho insert qua 5 anh cho 1 san pham
        int size = productImageRepository.findByProductId(productId).size();
        if(size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException("Number of image must be <= "+ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return productImageRepository.save(newProductImage);
    }
}
