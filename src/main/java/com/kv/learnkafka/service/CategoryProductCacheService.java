package com.kv.learnkafka.service;

import com.kv.learnkafka.messages.CatalogCategoryProduct;
import com.kv.learnkafka.utils.CacheNameSpace;

public interface CategoryProductCacheService {
    void updateCategoryProductRecord(CatalogCategoryProduct catalogCategoryProduct,
        CacheNameSpace cacheNameSpace);
    void removeCategoryLinkedToPid(CatalogCategoryProduct catalogCategoryProduct,
        CacheNameSpace cacheNameSpace);
}
