package com.ducbn.shopapp.controllers;

import com.ducbn.shopapp.dtos.CategoryDTO;
import com.ducbn.shopapp.models.Category;
import com.ducbn.shopapp.responses.UpdateCategoriResponse;
import com.ducbn.shopapp.services.CatagoryService;
import com.ducbn.shopapp.components.LocalizationUtils;
import com.ducbn.shopapp.utils.MessageKey;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${api.prefix}/categories")
//@Validated

//Dependency Injection
@RequiredArgsConstructor
public class CategoryController {
    private final CatagoryService catagoryService;
    private final LocalizationUtils localizationUtils;

    @PostMapping("")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        catagoryService.createCategory(categoryDTO);
        return ResponseEntity.ok("Insert category successfully");
    }


    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam("page")  int page,
            @RequestParam("limit")  int limit
    ) {
        List<Category> categories = catagoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateCategoriResponse> updateCategory(
             @PathVariable Long id,
             @Valid @RequestBody CategoryDTO categoryDTO)
    {
        catagoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(UpdateCategoriResponse.builder()
                        .message(localizationUtils.getLocalizedMessage(MessageKey.UPDATE_CATEGORY_SUCCESSFULLY))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        catagoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete category with id " + id+ " successfully");
    }
}
