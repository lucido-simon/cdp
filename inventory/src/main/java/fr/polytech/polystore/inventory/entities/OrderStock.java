package fr.polytech.polystore.inventory.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_stock", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"order_id", "product_guid"})
})
public class OrderStock {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tech_id;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "product_guid", nullable = false)
    private String productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private double buyPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_guid", referencedColumnName = "product_guid", insertable = false, updatable = false)
    private Stock stock;

}
