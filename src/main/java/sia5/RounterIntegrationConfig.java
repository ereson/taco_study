package sia5;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.util.Collection;
import java.util.Collections;

import static java.util.Collections.singleton;

/**
 * 路由的作用是将集成流进行分支
 * 配置一个路由，将偶数路由到一个evenChannel，将奇数路由到oddChannel
 */
@Configuration
public class RounterIntegrationConfig {

    @Bean
    @Router(inputChannel = "numberChannel")
    public AbstractMessageRouter evenOddRouter() {
        return new AbstractMessageRouter() {
            @Override
            protected Collection<MessageChannel> determineTargetChannels(Message<?> message) {
                Integer number = (Integer) message.getPayload();
                if (number % 2 == 0)  return singleton(evenChannel());
                return Collections.singleton(oddChannel());
            }
        };
    }

    /**
     * 接受偶数的通道
     * @return
     */
    @Bean
    public MessageChannel evenChannel() {
        return new DirectChannel();
    }

    /**
     * 接受奇数的通道
     * @return
     */
    @Bean
    public MessageChannel oddChannel() {
        return new DirectChannel();
    }
}
