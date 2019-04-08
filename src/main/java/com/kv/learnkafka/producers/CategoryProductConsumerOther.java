package com.kv.learnkafka.producers;

import com.kv.learnkafka.messages.CategoryProductMessage;
import com.kv.learnkafka.service.CategoryProductService;
import com.kv.learnkafka.utils.CacheNameSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service("CategoryProductConsumerOther")
public class CategoryProductConsumerOther {

  @Autowired
  private CategoryProductService categoryProductService;
  
  private static final Logger logger = LoggerFactory.getLogger(CategoryProductConsumerOther.class);

  @KafkaListener(
      topics = "${kafka.catalog.category_product.topic.other}",
      groupId = "${kafka.catalog.consumers.groupid}",
      containerFactory = "categoryProductListenerContainerFactory")
  public void listen(CategoryProductMessage kafkaMessage) {

    logger.debug("Processing Product ID: " + kafkaMessage.getProductId());

    categoryProductService.insertCategoryProduct(kafkaMessage.getProductId(), CacheNameSpace.CATALOG_OTHER);

    logger.debug("Processing Product ID: " + kafkaMessage.getProductId());
  }
}
