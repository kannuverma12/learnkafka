package com.kv.learnkafka.exception;

import com.kv.learnkafka.messages.BinlogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ErrorHandler {

  //@RecordCount(metricName = "catalogConsumerKafka_error")
  //@RecordTime(metricName = "catalogConsumerKafka_error")
  public void catalogBinlogListenerErrorHandler(String kafkaTopic, int partition, int offset, BinlogMessage binlogMessage, RuntimeException e) {
    log.error("kafkaTopic = {}, partition = {}, offset = {}, binlogMessage = {}", kafkaTopic, partition, offset, binlogMessage);
    throw e;
  }

  //@RecordCount(metricName = "catalogConsumerOtherKafka_error")
  //@RecordTime(metricName = "catalogConsumerOtherKafka_error")
  public void catalogOtherBinlogListenerErrorHandler(String kafkaTopic, int partition, int offset, BinlogMessage binlogMessage, RuntimeException e) {
    log.error("kafkaTopic = {}, partition = {}, offset = {}, binlogMessage = {}", kafkaTopic, partition, offset, binlogMessage);
    throw e;
  }
}
