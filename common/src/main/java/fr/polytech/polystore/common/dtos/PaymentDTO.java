package fr.polytech.polystore.common.dtos;

import fr.polytech.polystore.common.models.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private String id;
    private String orderId;
    private Double price;
    private PaymentStatus paymentStatus;
}
