package com.ducbn.shopapp.responses;

import com.ducbn.shopapp.models.Order;
import com.ducbn.shopapp.models.OrderDetail;
import com.ducbn.shopapp.models.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private Long id;

    @JoinColumn(name = "order_id")
    private Long orderID;

    @JoinColumn(name = "product_id")
    private Long productId;

    @Column(name = "price")
    private Float price;

    @Column(name = "number_of_product")
    private int numberOfProduct;

    @Column(name = "total_money")
    private Float totalMoney;

    private String color;

    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderID(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .price(orderDetail.getPrice())
                .numberOfProduct(orderDetail.getNumberOfProduct())
                .totalMoney(orderDetail.getTotalMoney())
                .color(orderDetail.getColor())
                .build();

    }
}
