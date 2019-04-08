package com.kv.learnkafka.producers;

import com.kv.learnkafka.messages.CategoryProductMessage;
import com.kv.learnkafka.service.CategoryProductService;
import com.kv.learnkafka.utils.CacheNameSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service("CategoryProductConsumer")
public class CategoryProductConsumer {

    @Autowired
    private CategoryProductService categoryProductService;

    private static final Logger logger = LoggerFactory.getLogger(CategoryProductConsumer.class);


    @KafkaListener(
            topics = "${kafka.catalog.categoy_product.topic}",
            groupId = "${kafka.catalog.consumers.groupid}",
            containerFactory = "categoryProductListenerContainerFactory")
    public void listen(CategoryProductMessage kafkaMessage) {

        logger.debug("Processing Product ID: " + kafkaMessage.getProductId());

        categoryProductService.insertCategoryProduct(kafkaMessage.getProductId(), CacheNameSpace.CATALOG);

        logger.debug("Processing Product ID: " + kafkaMessage.getProductId());


    }
}
