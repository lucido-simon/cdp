package fr.polytech.polystore.common.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderCartQueues {
    public static String ORDER_CART_QUEUE;
    public static String CART_ORDER_QUEUE;

    @Bean
    public Queue orderCartQueue() {
        return new Queue(ORDER_CART_QUEUE, true);
    }

    @Bean
    public Queue cartOrderQueue() {
        return new Queue(CART_ORDER_QUEUE, true);
    }

    @Value("${order.cart.queue}")
    public void setOrderInventoryQueue(String ORDER_CART_QUEUE) {
        OrderCartQueues.ORDER_CART_QUEUE = ORDER_CART_QUEUE;
    }

    @Value("${cart.order.queue}")
    public void setInventoryOrderQueue(String CART_ORDER_QUEUE) {
        OrderCartQueues.CART_ORDER_QUEUE = CART_ORDER_QUEUE;
    }
}
