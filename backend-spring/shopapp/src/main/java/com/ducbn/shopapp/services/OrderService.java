package com.ducbn.shopapp.services;

import com.ducbn.shopapp.dtos.OrderDTO;
import com.ducbn.shopapp.models.Order;
import com.ducbn.shopapp.models.User;
import com.ducbn.shopapp.repositories.OrderRepository;
import com.ducbn.shopapp.repositories.UserRepository;
import com.ducbn.shopapp.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) throws Exception{
        //tim xem user id cos ton tai khong
        User user = userRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(()->new Exception("User not found with id: "+orderDTO.getUserId()));
        //convert orderDTO -> order
        //use librari ModelMapper
        //tao 1 luong bang anh sa rieng de kiem soat viec anh sa
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        // cap nhap cac truong cua don hang tu bang orderDTO
        modelMapper.map(orderDTO, order);
        return null;
    }

    @Override
    public OrderResponse getOrder(Long id) {
        return null;
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }

    @Override
    public List<OrderResponse> getAllOrders(Long userId) {
        return List.of();
    }
}
