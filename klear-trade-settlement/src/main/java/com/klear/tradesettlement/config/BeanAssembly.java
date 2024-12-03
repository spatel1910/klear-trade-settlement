
package com.klear.tradesettlement.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class BeanAssembly {
    @Bean
    public NewTopic getBean(){
        return TopicBuilder.name("trade-events").build();
    }
}

