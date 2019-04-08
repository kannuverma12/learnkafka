package com.kv.learnkafka.utils;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CacheNameSpace {

  CATALOG(Collections.unmodifiableMap(
      Stream.of(
          new SimpleEntry<>(
              Constants.CATEGORY_SET_KEY,
              Constants.CATEGORY_AEROSPIKE_SETS),
          new SimpleEntry<>(
              Constants.CCP_SET_KEY,
              Constants.CCP_AEROSPIKE_SETS)
      )
      .collect(
          Collectors.toMap(
              SimpleEntry :: getKey,
              SimpleEntry :: getValue
          )
      )
  )),
  CATALOG_OTHER(Collections.unmodifiableMap(
      Stream.of(
          new SimpleEntry<>(
              Constants.CATEGORY_SET_KEY,
              Constants.CATEGORY_AEROSPIKE_OTHER_SETS),
          new SimpleEntry<>(
              Constants.CCP_SET_KEY,
              Constants.CCP_AEROSPIKE_OTHER_SETS)
      )
      .collect(
          Collectors.toMap(
              SimpleEntry :: getKey,
              SimpleEntry :: getValue
          )
      )
  ));

  private Map<String,String> value;

  CacheNameSpace(Map<String,String> value) {
    this.value = value;
  }

  public String getAerospikeSet(String key) {
    return value.get(key);
  }
}
