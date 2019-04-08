package com.kv.learnkafka.consumers;

import com.kv.learnkafka.exception.ErrorHandler;
import com.kv.learnkafka.messages.BinlogMessage;
import com.kv.learnkafka.service.BinlogProcessorService;
import com.kv.learnkafka.utils.CacheNameSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service("CatalogConsumerOther")
public class CatalogConsumerOther {
  private static final Logger logger = LoggerFactory.getLogger(CatalogConsumerOther.class);

  @Value("${AEROSPIKE_NAMESPACE_OTHER}")
  private String aeropsikeNameSpaceOther;

  @Value("${kafka.catalog.binlog.topic.other}")
  private String kafkaTopic;

  @Autowired
  private BinlogProcessorService binlogProcessorService;

  @Autowired
  private ErrorHandler errorHandler;

  @KafkaListener(
      topics = "${kafka.catalog.binlog.topic.other}",
      groupId = "${kafka.catalog.consumers.groupid}",
      containerFactory = "catalogBinlogListenerContainerFactory")
  public void catalogBinlogListener(@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
      @Header(KafkaHeaders.OFFSET) int offset,
      BinlogMessage binlogMessage) {
    try {
      binlogProcessorService.processBinlog(binlogMessage, CacheNameSpace.CATALOG_OTHER);
    } catch (RuntimeException e) {
      errorHandler.catalogOtherBinlogListenerErrorHandler(kafkaTopic, partition, offset, binlogMessage, e);
    }
  }
}
