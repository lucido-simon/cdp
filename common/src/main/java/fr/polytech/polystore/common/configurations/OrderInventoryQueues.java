package fr.polytech.polystore.common.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderInventoryQueues {

    public static String ORDER_INVENTORY_QUEUE;
    public static String ORDER_INVENTORY_COMPENSATION_QUEUE;
    public static String INVENTORY_ORDER_QUEUE;
    public static String INVENTORY_ORDER_COMPENSATION_QUEUE;


    @Bean
    public Queue orderInventoryQueue() {
        return new Queue(ORDER_INVENTORY_QUEUE, true);
    }

    @Bean
    public Queue orderInventoryCompensationQueue() {
        return new Queue(ORDER_INVENTORY_COMPENSATION_QUEUE, true);
    }

    @Bean
    public Queue inventoryOrderQueue() {
        return new Queue(INVENTORY_ORDER_QUEUE, true);
    }

    @Bean
    public Queue inventoryOrderCompensationQueue() {
        return new Queue(INVENTORY_ORDER_COMPENSATION_QUEUE, true);
    }

    @Value("${order.inventory.queue}")
    public void setOrderInventoryQueue(String ORDER_INVENTORY_QUEUE) {
        OrderInventoryQueues.ORDER_INVENTORY_QUEUE = ORDER_INVENTORY_QUEUE;
    }
    @Value("${order.inventory.compensation.queue}")
    public void setOrderInventoryCompensationQueue(String ORDER_INVENTORY_COMPENSATION_QUEUE) {
        OrderInventoryQueues.ORDER_INVENTORY_COMPENSATION_QUEUE = ORDER_INVENTORY_COMPENSATION_QUEUE;
    }
    @Value("${inventory.order.queue}")
    public void setInventoryOrderQueue(String INVENTORY_ORDER_QUEUE) {
        OrderInventoryQueues.INVENTORY_ORDER_QUEUE = INVENTORY_ORDER_QUEUE;
    }
    @Value("${inventory.order.compensation.queue}")
    public void setInventoryOrderCompensationQueue(String INVENTORY_ORDER_COMPENSATION_QUEUE) {
        OrderInventoryQueues.INVENTORY_ORDER_COMPENSATION_QUEUE = INVENTORY_ORDER_COMPENSATION_QUEUE;
    }
}
