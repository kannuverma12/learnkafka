package com.kv.learnkafka.consumers;

import com.kv.learnkafka.messages.BinlogMessage;
import com.kv.learnkafka.messages.CategoryProductMessage;
import com.kv.learnkafka.utils.Constants;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer.AckMode;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class CatalogConsumerConfig implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory
            .getLogger(CatalogConsumerConfig.class);

    @Value("${kafka.servers}")
    private String kafkaServerAddress;

    private ApplicationContext context;

    public DefaultKafkaConsumerFactory<String, Object> consumerFactory() {
        logger.info("Initializing catalog binlog consumer factory bean!!!!");
        Map<String, Object> props = new HashMap<>();
        props.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaServerAddress);
        props.put( ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "500");
        props.put( ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put( ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put( ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "300000");


        logger.info("properties = {}", props);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> catalogBinlogListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        DefaultKafkaConsumerFactory<String, Object> defaultFactory = consumerFactory();
        defaultFactory.setKeyDeserializer(new StringDeserializer ());
        defaultFactory.setValueDeserializer(new JsonDeserializer( BinlogMessage.class));
        factory.setConsumerFactory(defaultFactory);
        factory.setConcurrency( Constants.CONSUMER_CONCURRENCY * Runtime.getRuntime().availableProcessors());
        factory.getContainerProperties().setAckOnError(false);
        factory.getContainerProperties().setErrorHandler(new SeekToCurrentErrorHandler());
        factory.getContainerProperties().setAckMode(AckMode.RECORD);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> categoryProductListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        DefaultKafkaConsumerFactory<String, Object> defaultFactory = consumerFactory();
        defaultFactory.setKeyDeserializer(new StringDeserializer ());
        defaultFactory.setValueDeserializer(new JsonDeserializer( CategoryProductMessage.class));
        factory.setConsumerFactory(defaultFactory);
        return factory;
    }

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> catalogCategoryListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        DefaultKafkaConsumerFactory<String, Object> defaultFactory = consumerFactory();
//        defaultFactory.setKeyDeserializer(new StringDeserializer ());
//        defaultFactory.setValueDeserializer(new JsonDeserializer(CatalogCategoryMessage.class));
//        factory.setConsumerFactory(defaultFactory);
//        return factory;
//    }

    @Override
	  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		    context = applicationContext;
	  }

}
