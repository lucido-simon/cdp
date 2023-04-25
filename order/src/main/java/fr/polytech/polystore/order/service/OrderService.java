package fr.polytech.polystore.order.service;

import fr.polytech.polystore.common.dtos.CartProductDTO;
import fr.polytech.polystore.common.dtos.OrderDTO;
import fr.polytech.polystore.common.models.OrderStatus;
import fr.polytech.polystore.order.entities.Order;
import fr.polytech.polystore.order.entities.OrderProduct;
import fr.polytech.polystore.order.entities.Product;
import fr.polytech.polystore.order.repositories.OrderProductRepository;
import fr.polytech.polystore.order.repositories.OrderRepository;
import fr.polytech.polystore.order.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    public String createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(java.util.UUID.randomUUID().toString());
        order.setUserId(orderDTO.getUserId());
        order.setOrderStatus(OrderStatus.OrderCheckout);

        orderRepository.save(order);

        List<OrderProduct> orderProducts = orderDTO.getOrderProducts().stream().map(op -> {
            Optional<Product> productOption = productRepository.findProductByProductGuid(op.getId());
            Product product = productOption.orElse(null);
            if (product == null) {
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
        order.setOrderStatus(OrderStatus.OrderCreated);
        orderRepository.save(order);

        return order.getId();
    }

    private Product newProductFromOrderProductDTO(CartProductDTO op) {
        Product product = new Product();
        product.setProductGuid(op.getId());
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