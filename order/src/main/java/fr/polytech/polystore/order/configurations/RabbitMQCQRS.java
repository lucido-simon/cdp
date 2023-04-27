package fr.polytech.polystore.order.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class RabbitMQCQRS {

    public static String ORDER_CQRS_QUEUE;

    @Bean
    public Queue orderCQRSQueue() {
        return new Queue(ORDER_CQRS_QUEUE, true, false, false, Collections.singletonMap("x-queue-type", "stream"));
    }

    @Value("${order.cqrs.queue}")
    public void setOrderCQRSQueue(String ORDER_CQRS_QUEUE) {
        RabbitMQCQRS.ORDER_CQRS_QUEUE = ORDER_CQRS_QUEUE;
    }
}
