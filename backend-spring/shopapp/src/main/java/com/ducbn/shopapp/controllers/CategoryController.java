package com.ducbn.shopapp.controllers;

import com.ducbn.shopapp.dtos.CategoryDTO;
import com.ducbn.shopapp.models.Category;
import com.ducbn.shopapp.responses.UpdateCategoriResponse;
import com.ducbn.shopapp.services.CatagoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("${api.prefix}/categories")
//@Validated

//Dependency Injection
@RequiredArgsConstructor
public class CategoryController {
    private final CatagoryService catagoryService;
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

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
             @Valid @RequestBody CategoryDTO categoryDTO,
             HttpServletRequest request)
    {
        catagoryService.updateCategory(id, categoryDTO);
        Locale locale = localeResolver.resolveLocale(request);
        return ResponseEntity.ok(UpdateCategoriResponse.builder()
                        .message(messageSource.getMessage("category.updateCategory.update_successfully", null, locale))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        catagoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete category with id " + id+ " successfully");
    }
}
