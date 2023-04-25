package fr.polytech.polystore.common.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderCartQueues {
    public static String CART_ORDER_QUEUE;

    @Bean
    public Queue cartOrderQueue() {
        return new Queue(CART_ORDER_QUEUE, true);
    }

    @Value("${cart.order.queue}")
    public void setInventoryOrderQueue(String CART_ORDER_QUEUE) {
        OrderCartQueues.CART_ORDER_QUEUE = CART_ORDER_QUEUE;
    }
}
