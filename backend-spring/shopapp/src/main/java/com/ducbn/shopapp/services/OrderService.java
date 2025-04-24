package com.ducbn.shopapp.services;

import com.ducbn.shopapp.dtos.CartItemDTO;
import com.ducbn.shopapp.dtos.OrderDTO;
import com.ducbn.shopapp.exceptions.DataNotFoundException;
import com.ducbn.shopapp.models.*;
import com.ducbn.shopapp.repositories.OrderDetailRepository;
import com.ducbn.shopapp.repositories.OrderRepository;
import com.ducbn.shopapp.repositories.ProductRepository;
import com.ducbn.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) throws Exception{
        //tim xem user id co ton tai khong
        User user = userRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(()->new Exception("User not found with id: "+orderDTO.getUserId()));
        //convert orderDTO -> order
        //use librari ModelMapper
        //tao 1 luong bang anh sa rieng de kiem soat viec anh sa
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        // cap nhap cac truong cua don hang tu bang orderDTO
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);

        //kiem tra shipping date >= ngay dat hang
        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
        if(shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at least today!");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        order.setTotalMoney(orderDTO.getTotalMoney());
        orderRepository.save(order);

        //tạo 1 ds các obj OrderDetail từ CartItemDTO
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItemDTO cartItemDTO: orderDTO.getCartItems()) {
            //tạo 1 đối tượng orderDetail từ cartItemDTO
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);

            //lấy thông tin sản phẩm từ cartItemDTO
            Long productId = cartItemDTO.getProductId();
            int quantity = cartItemDTO.getQuantity();

            //tìm thông tin sản phẩm từ csdl(hoặc cache nếu cần)
            Product product = productRepository.findById(productId)
                    .orElseThrow(()->new Exception("Product not found with id: "+productId));

            orderDetail.setProduct(product);
            orderDetail.setNumberOfProduct(quantity);
            orderDetail.setPrice(product.getPrice());

            orderDetails.add(orderDetail);
        }
        orderDetailRepository.saveAll(orderDetails);
        return order;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(()->
                new DataNotFoundException("Cannot find order with id: "+ id));

        User existingUser = userRepository.findById(orderDTO.getUserId()).orElseThrow(()->
                new DataNotFoundException("User not found with id: "+id));

        //tao 1 luong bang anh xa rieng de kiem soat viec anh xa
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        // cap nhpa cac truonwg cua don hang tu orderDTO
        modelMapper.map(orderDTO, order);
        order.setUser(existingUser);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        //khong xoa cung,  => soft-delete
        if(order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
