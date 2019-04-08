package com.kv.learnkafka.consumers;

import com.kv.learnkafka.exception.ErrorHandler;
import com.kv.learnkafka.messages.BinlogMessage;
import com.kv.learnkafka.service.BinlogProcessorService;
import com.kv.learnkafka.utils.CacheNameSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;


@Service("CatalogConsumer")
public class CatalogConsumer {

    @Value("${AEROSPIKE_NAMESPACE}")
    private String aerospikeNameSpace;

    @Autowired
    private BinlogProcessorService binlogProcessorService;

    @Value("${kafka.catalog.binlog.topic}")
    private String kafkaTopic;

    @Autowired
    private ErrorHandler errorHandler;

    @KafkaListener(
            topics = "${kafka.catalog.binlog.topic}",
            groupId = "${kafka.catalog.consumers.groupid}",
            containerFactory = "catalogBinlogListenerContainerFactory")
    public void catalogBinlogListener(@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                      @Header(KafkaHeaders.OFFSET) int offset,
                                      BinlogMessage binlogMessage) {
        try {
            binlogProcessorService.processBinlog(binlogMessage, CacheNameSpace.CATALOG);
        } catch (RuntimeException e) {
            errorHandler.catalogBinlogListenerErrorHandler( kafkaTopic, partition, offset, binlogMessage,e);
        }
    }


}
