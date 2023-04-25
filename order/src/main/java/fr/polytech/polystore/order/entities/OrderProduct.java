package fr.polytech.polystore.order.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_products", uniqueConstraints = {@UniqueConstraint(columnNames = {"order_id", "product_id"})})
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price")
    private float price;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}