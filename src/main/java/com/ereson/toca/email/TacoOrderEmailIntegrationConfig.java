package com.ereson.toca.email;

import com.ereson.toca.EmailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;

@Configuration
public class TacoOrderEmailIntegrationConfig {

    @Bean
    public IntegrationFlow tacoOrderEmailConfig(EmailProperties emailProperties, EmailToOrderTransformer email
            , OrderSubmitMessageHandler orderSubmitMessageHandler) {
        return IntegrationFlows.from(Mail.imapInboundAdapter(emailProperties.getImapUrl()),
                e -> e.poller(
                        Pollers.fixedDelay(emailProperties.getPollRate())
                ))
                .transform(email)
                .handle(orderSubmitMessageHandler)
                .get();
    }
}
