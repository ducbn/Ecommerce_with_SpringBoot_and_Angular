package com.ducbn.shopapp.services;

import com.ducbn.shopapp.dtos.OrderDetailDTO;
import com.ducbn.shopapp.exceptions.DataNotFoundException;
import com.ducbn.shopapp.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO newOrderDetail) throws Exception;
    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO newOrderDetailData);
    void deleteOrderDetail(Long id);
    List<OrderDetail> findByOrderId(Long orderId);
}
