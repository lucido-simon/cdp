package fr.polytech.polystore.cart.repositories;

import fr.polytech.polystore.cart.entities.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart, String> {}
