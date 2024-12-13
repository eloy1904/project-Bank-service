package com.nttadata.transaction_service.config;

import com.nttadata.transaction_service.dto.TransactionDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ReactiveKafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.key-serializer}")
    private String keySerializer;

    @Value("${spring.kafka.value-serializer}")
    private String valueSerializer;

    @Bean
    public KafkaSender<String, TransactionDto> kafkaSender() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);

        SenderOptions<String, TransactionDto> senderOptions = SenderOptions.create(props);
        return KafkaSender.create(senderOptions);
    }
}
