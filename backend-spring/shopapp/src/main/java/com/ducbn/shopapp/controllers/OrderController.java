package com.ducbn.shopapp.controllers;

import com.ducbn.shopapp.dtos.OrderDTO;
import com.ducbn.shopapp.models.Order;
import com.ducbn.shopapp.services.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

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
            Order orderResponse = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(orderResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrdersByUser (@Valid @PathVariable("user_id") Long user_id) {
        try{
            List<Order> orders = orderService.findByUserId(user_id);
            return ResponseEntity.ok(orders);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder (@Valid @PathVariable("id") Long orderId) {
        try{
            Order existingOrder = orderService.getOrder(orderId);
            return ResponseEntity.ok(existingOrder);
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
