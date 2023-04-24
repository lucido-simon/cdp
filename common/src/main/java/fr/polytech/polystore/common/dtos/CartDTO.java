package fr.polytech.polystore.common.dtos;

import fr.polytech.polystore.common.dtos.CartProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private List<CartProductDTO> products;
}
