package fr.polytech.polystore.common.dtos;

import fr.polytech.polystore.common.models.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentDTO {
    private String id;
    private String orderId;
    private ShipmentStatus shipmentStatus;
}
