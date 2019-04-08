package com.kv.learnkafka.service;

import com.kv.learnkafka.messages.BinlogMessage;
import com.kv.learnkafka.utils.CacheNameSpace;

public interface CategoryService {
    void insertCategory(long id, CacheNameSpace cacheNameSpace);
    void updateCategory(BinlogMessage binlogMessage, CacheNameSpace cacheNameSpace);
}
