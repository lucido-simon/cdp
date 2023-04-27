package fr.polytech.polystore.common.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class OrderShippingQueues {
    public static String ORDER_SHIPPING_QUEUE;
    public static String ORDER_SHIPPING_COMPENSATION_QUEUE;
    public static String SHIPPING_ORDER_QUEUE;
    public static String SHIPPING_ORDER_COMPENSATION_QUEUE;


    @Bean
    public Queue orderShippingQueue() {
        return new Queue(ORDER_SHIPPING_QUEUE, true);
    }

    @Bean
    public Queue orderShippingCompensationQueue() {
        return new Queue(ORDER_SHIPPING_COMPENSATION_QUEUE, true);
    }

    @Bean
    public Queue shippingOrderQueue() {
        return new Queue(SHIPPING_ORDER_QUEUE, true);
    }

    @Bean
    public Queue shippingOrderCompensationQueue() {
        return new Queue(SHIPPING_ORDER_COMPENSATION_QUEUE, true);
    }

    @Value("${order.shipping.queue}")
    public void setOrderShippingQueue(String ORDER_SHIPPING_QUEUE) {
        OrderShippingQueues.ORDER_SHIPPING_QUEUE = ORDER_SHIPPING_QUEUE;
    }
    @Value("${order.shipping.compensation.queue}")
    public void setOrderShippingCompensationQueue(String ORDER_SHIPPING_COMPENSATION_QUEUE) {
        OrderShippingQueues.ORDER_SHIPPING_COMPENSATION_QUEUE = ORDER_SHIPPING_COMPENSATION_QUEUE;
    }
    @Value("${shipping.order.queue}")
    public void setShippingOrderQueue(String SHIPPING_ORDER_QUEUE) {
        OrderShippingQueues.SHIPPING_ORDER_QUEUE = SHIPPING_ORDER_QUEUE;
    }
    @Value("${shipping.order.compensation.queue}")
    public void setShippingOrderCompensationQueue(String SHIPPING_ORDER_COMPENSATION_QUEUE) {
        OrderShippingQueues.SHIPPING_ORDER_COMPENSATION_QUEUE = SHIPPING_ORDER_COMPENSATION_QUEUE;
    }
}
