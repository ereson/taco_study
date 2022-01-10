package sia5;

import com.ereson.toca.Order;
import com.ereson.toca.data.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHandler;


public class ServiceActivatorConfig {

    @Bean
    @ServiceActivator(inputChannel = "someChannel")
    public MessageHandler sysoutHandler() {
        return message -> {
            System.out.println("Message payload: " + message.getPayload());
        };
    }


    /**
     * 在返回新载荷前，处理输入消息中的数据
     * bean应该是一个GenericHandler
     * @param orderRepository
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "orderChannel", outputChannel = "completeOrder")
    public GenericHandler<Order> orderHandler(OrderRepository orderRepository) {
        return ((payload, headers) -> {
            return orderRepository.save(payload);
        });
    }
}
