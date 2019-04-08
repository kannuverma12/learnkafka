package com.kv.learnkafka.service.impl;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Key;
import com.aerospike.client.cdt.ListOperation;
import com.aerospike.client.cdt.ListReturnType;
import com.aerospike.client.policy.WritePolicy;
import com.kv.learnkafka.messages.CatalogCategoryProduct;
import com.kv.learnkafka.service.CategoryProductCacheService;
import com.kv.learnkafka.utils.CacheNameSpace;
import com.kv.learnkafka.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service("CategoryProductCacheService")
public class CategoryProductCacheServiceImpl implements CategoryProductCacheService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryProductCacheServiceImpl.class);

    @Autowired
    private AerospikeClient aerospikeClient;

    @Value("${AEROSPIKE_NAMESPACE}")
    private String aerospikeNameSpace;

    /*  cache schema:
            pid -> [cat1_id, cat2_id, ... catn_id]

        case 1: if product not exist
                then save pid -> [cat1_id]
        case 2: if product exist
                then save pid -> [cat1_id, cat2_id]
     */

    public void updateCategoryProductRecord(CatalogCategoryProduct catalogCategoryProduct, CacheNameSpace cacheNameSpace) {
        WritePolicy policy = new WritePolicy();
        policy.expiration = Constants.NO_EXPIRE;
        com.aerospike.client.Value categoryId = com.aerospike.client.Value.get(
            catalogCategoryProduct.getCategoryId());
        Key key = getAeroKey( catalogCategoryProduct.getProductId(), cacheNameSpace);
        aerospikeClient.operate(policy, key, ListOperation.append(Constants.CATEGORIES, categoryId));
    }

    public void removeCategoryLinkedToPid(CatalogCategoryProduct catalogCategoryProduct, CacheNameSpace cacheNameSpace) {
        com.aerospike.client.Value categoryId = com.aerospike.client.Value.get(
            catalogCategoryProduct.getCategoryId());
        Key key = getAeroKey( catalogCategoryProduct.getProductId(), cacheNameSpace);
        try {
            aerospikeClient.operate(null, key, ListOperation.removeByValue(Constants.CATEGORIES, categoryId, ListReturnType.COUNT));
        } catch (Exception e) {
            logger.info("error in remove operate {}", e.getMessage());
        }
    }

    private Key getAeroKey(Long productId, CacheNameSpace cacheNameSpace) {
        String aero_Key = Constants.PRODUCT_PREFIX + productId;
        return new Key(aerospikeNameSpace, cacheNameSpace.getAerospikeSet(Constants.CCP_SET_KEY), aero_Key);
    }
}
