package fr.polytech.polystore.order.service;

import fr.polytech.polystore.common.dtos.CartProductDTO;
import fr.polytech.polystore.common.dtos.OrderDTO;
import fr.polytech.polystore.common.models.OrderStatus;
import fr.polytech.polystore.order.entities.Order;
import fr.polytech.polystore.order.entities.OrderProduct;
import fr.polytech.polystore.order.repositories.OrderProductRepository;
import fr.polytech.polystore.order.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    public String createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(java.util.UUID.randomUUID().toString());
        order.setUserId(orderDTO.getUserId());
        order.setOrderStatus(OrderStatus.OrderCreated);

        List<OrderProduct> orderProducts = orderDTO.getOrderProducts().stream().map(product -> {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProductId(product.getId());
            orderProduct.setQuantity(product.getQuantity());
            orderProduct.setOrder(order);
            return orderProduct;
        }).collect(Collectors.toList());

        order.setOrderProducts(orderProducts);
        orderRepository.save(order);

        try {
            orderProducer.convertAndSendInventory();
        } catch (Exception e) {
            this.compensate(order.getId());
        }

        return order.getId();
    }

    public void compensate(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            order.get().setOrderStatus(OrderStatus.OrderCreationFailed);
            orderRepository.save(order.get());
        }
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}