package com.ducbn.shopapp.controllers;

import com.ducbn.shopapp.dtos.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @Valid @ModelAttribute ProductDTO productDTO,
            BindingResult result
    ) {
        try{
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            List<MultipartFile> files = productDTO.getFiles();
            files = files == null ? new ArrayList<MultipartFile>() : files; // nếu file là null
            for (MultipartFile file : files) {
                if(file.getSize() == 0){
                    continue;
                }
                // kiêm tra kích thước file và định dạng
                if(file.getSize() > 10 * 1024 * 1024) { // kích thước > 10MB
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large. maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if(contentType ==null||!contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                //Lưu file và cập nhập thumnail trong DOT
                String filename = storeFile(file);// thay thế hàm này với code của bạn để lưu file

            }
            return ResponseEntity.ok("Product created successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<String> getProducts(
            @RequestParam("page")  int page,
            @RequestParam("limit")  int limit
    ) {
        return ResponseEntity.ok("getProducts here");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable("id") String productId) {
        return ResponseEntity.ok("Product with id " + productId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        return ResponseEntity.ok(String.format("Product with id = %d deleted successfully ", id));
    }

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // Thêm UUID vào trước tên file để đảm bảo khong trung tên
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        //đương dẫn đên thư mục bạn muốn lưu file
        Path uploadDir = Paths.get("uploads");

        if (!Files.exists(uploadDir)) {
            Files.createDirectory(uploadDir);
        }
        // đường dẫn đầy đủ đến file
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        //sao chép file vào thư mục đích.
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }
}
