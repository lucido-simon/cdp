package fr.polytech.polystore.common.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class OrderPaymentQueues {
    public static String ORDER_PAYMENT_QUEUE;
    public static String ORDER_PAYMENT_COMPENSATION_QUEUE;
    public static String PAYMENT_ORDER_QUEUE;
    public static String PAYMENT_ORDER_COMPENSATION_QUEUE;


    @Bean
    public Queue orderPaymentQueue() {
        return new Queue(ORDER_PAYMENT_QUEUE, true);
    }

    @Bean
    public Queue orderPaymentCompensationQueue() {
        return new Queue(ORDER_PAYMENT_COMPENSATION_QUEUE, true);
    }

    @Bean
    public Queue paymentOrderQueue() {
        return new Queue(PAYMENT_ORDER_QUEUE, true);
    }

    @Bean
    public Queue paymentOrderCompensationQueue() {
        return new Queue(PAYMENT_ORDER_COMPENSATION_QUEUE, true);
    }

    @Value("${order.payment.queue}")
    public void setOrderPaymentQueue(String ORDER_PAYMENT_QUEUE) {
        OrderPaymentQueues.ORDER_PAYMENT_QUEUE = ORDER_PAYMENT_QUEUE;
    }
    @Value("${order.payment.compensation.queue}")
    public void setOrderPaymentCompensationQueue(String ORDER_PAYMENT_COMPENSATION_QUEUE) {
        OrderPaymentQueues.ORDER_PAYMENT_COMPENSATION_QUEUE = ORDER_PAYMENT_COMPENSATION_QUEUE;
    }
    @Value("${payment.order.queue}")
    public void setPaymentOrderQueue(String PAYMENT_ORDER_QUEUE) {
        OrderPaymentQueues.PAYMENT_ORDER_QUEUE = PAYMENT_ORDER_QUEUE;
    }
    @Value("${payment.order.compensation.queue}")
    public void setPaymentOrderCompensationQueue(String PAYMENT_ORDER_COMPENSATION_QUEUE) {
        OrderPaymentQueues.PAYMENT_ORDER_COMPENSATION_QUEUE = PAYMENT_ORDER_COMPENSATION_QUEUE;
    }
}
