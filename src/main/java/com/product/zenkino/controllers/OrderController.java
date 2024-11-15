package com.product.zenkino.controllers;

import com.product.zenkino.entities.Order;
import com.product.zenkino.entities.User;
import com.product.zenkino.exception.EntityNotFoundException;
import com.product.zenkino.repositories.OrderRepository;
import com.product.zenkino.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/orders/{id}")
    public Optional<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty())
            throw new EntityNotFoundException("Order with Id " + id + " not found");
        return order;
    }

    @PostMapping("/order")
    public Order createOrder(@RequestBody Order order) {
        Long userId = order.getUser().getId();
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new EntityNotFoundException("User with Id " + userId + " not found");
        order.setUser(user.get());
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderDate(new Date());
        return orderRepository.save(order);
    }

    @PutMapping("/orders/{id}")
    public Order updateOrder(@RequestBody Order order, @PathVariable Long id) {
        Optional<Order> optionalExistingOrder = orderRepository.findById(id);
        if (optionalExistingOrder.isEmpty())
            throw new EntityNotFoundException("Order with Id " + id + " not found");
        Order existingOrder = optionalExistingOrder.get();
        modelMapper.map(order, existingOrder);
        existingOrder.setUpdatedOrderDate(new Date());
        return orderRepository.save(existingOrder);
    }

    @DeleteMapping("/orders/{id}")
    public void deleteOrderById(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty())
            throw new EntityNotFoundException("Order with Id " + id + " not found");
        orderRepository.deleteById(id);
    }
}
