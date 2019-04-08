package com.kv.learnkafka.service;

import com.kv.learnkafka.messages.BinlogMessage;
import com.kv.learnkafka.utils.CacheNameSpace;
import org.springframework.stereotype.Service;

@Service
public interface CategoryProductService {
    void updateCategoryProduct(BinlogMessage binlogMessage, CacheNameSpace cacheNameSpace);
    void insertCategoryProduct(long productId, CacheNameSpace cacheNameSpace);
}
