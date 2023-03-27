package fr.polytech.polystore.inventory.service;

import fr.polytech.polystore.inventory.dtos.OrderDTO;
import fr.polytech.polystore.inventory.dtos.OrderProductDTO;
import fr.polytech.polystore.inventory.entities.Order;
import fr.polytech.polystore.inventory.entities.OrderProduct;
import fr.polytech.polystore.inventory.entities.Product;
import fr.polytech.polystore.inventory.repositories.OrderProductRepository;
import fr.polytech.polystore.inventory.repositories.OrderRepository;
import fr.polytech.polystore.inventory.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@EnableDiscoveryClient
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(java.util.UUID.randomUUID().toString());
        order.setUserId(orderDTO.getUserId());

        this.orderRepository.save(order);

        List<OrderProduct> orderProducts = orderDTO.getOrderProducts().stream().map(op -> {
            Optional<Product> productOption = productRepository.findProductByProductGuid(op.getProductId());
            Product product = productOption.orElse(null);
            if (product == null ) {
                product = newProductFromOrderProductDTO(op);
            }
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setPrice(0f);
            orderProduct.setQuantity(op.getQuantity());
            return orderProductRepository.save(orderProduct);
        }).collect(Collectors.toList());

        order.setOrderProducts(orderProducts);
        orderRepository.save(order);

        OrderDTO dto = new OrderDTO();
        String id = order.getId();
        dto.setId(id);
        dto.setUserId(order.getUserId());
        dto.setOrderProducts(
                order.getOrderProducts().stream().map(op -> {
                    OrderProductDTO opDTO = new OrderProductDTO();
                    opDTO.setProductId(op.getProduct().getProductGuid());
                    opDTO.setQuantity(op.getQuantity());
                    return opDTO;
                }).collect(Collectors.toList())
        );

        return dto;
    }

    private Product newProductFromOrderProductDTO(OrderProductDTO op) {
        Product product = new Product();
        product.setProductGuid(op.getProductId());
        this.productRepository.save(product);
        return product;
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