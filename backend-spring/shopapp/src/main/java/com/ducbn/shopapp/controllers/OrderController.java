package com.ducbn.shopapp.controllers;

import com.ducbn.shopapp.dtos.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    @PostMapping("")
    public ResponseEntity<?> createOrder (@Valid @RequestBody OrderDTO orderDTO, BindingResult result) {
        try{
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok("create order successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrdersByUser (@Valid @PathVariable("user_id") Long user_id) {
        try{
            return ResponseEntity.ok("get orders by user_id");

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    //công việc của admin
    public ResponseEntity<?> updateOrder (@Valid @RequestBody OrderDTO orderDTO, @PathVariable Long id) {
        return ResponseEntity.ok("update order successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder (@PathVariable Long id) {
        //xoá mềm => cập nhập active = f
        return ResponseEntity.ok("delete order successfully");
    }
}
