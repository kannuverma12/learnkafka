package com.kv.learnkafka.service.impl;

import com.kv.learnkafka.messages.BinlogMessage;
import com.kv.learnkafka.messages.CatalogCategoryProduct;
import com.kv.learnkafka.messages.CategoryProductMapping;
import com.kv.learnkafka.repository.CategoryProductMappingRepository;
import com.kv.learnkafka.service.CategoryProductCacheService;
import com.kv.learnkafka.service.CategoryProductService;
import com.kv.learnkafka.utils.CacheNameSpace;
import com.kv.learnkafka.utils.Constants;
import com.kv.learnkafka.utils.SerializationUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CategoryProductService")
public class CategoryProductServiceImpl implements CategoryProductService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryProductServiceImpl.class);

    @Autowired
    private CategoryProductCacheService categoryProductCacheService;

    @Autowired
    private CategoryProductMappingRepository categoryProductRepository;

    public void updateCategoryProduct(BinlogMessage binlogMessage, CacheNameSpace cacheNameSpace) {
        String type = binlogMessage.getType();
        logger.debug("category_product table update type = {}", type);
        switch (type) {
            case Constants.INSERT_TYPE:
            case Constants.BOOTSTRAP_INSERT_TYPE: updateCategoryProductInAerospike(binlogMessage,
                cacheNameSpace);break;
            case Constants.REMOVE_TYPE: removeCategoryLinkedToProductInAerospike(binlogMessage,
                cacheNameSpace);break;
        }
    }

    private void updateCategoryProductInAerospike(BinlogMessage binlogMessage, CacheNameSpace cacheNameSpace) {
        CatalogCategoryProduct catalogCategoryProduct = SerializationUtil.mapToTypedObject(
                binlogMessage.getData(), CatalogCategoryProduct.class);
        logger.info("inserting category product in aerospike = {} type {}", catalogCategoryProduct, binlogMessage.getType());
        categoryProductCacheService.updateCategoryProductRecord( catalogCategoryProduct,
            cacheNameSpace);
    }

    private void removeCategoryLinkedToProductInAerospike(BinlogMessage binlogMessage, CacheNameSpace cacheNameSpace) {
        CatalogCategoryProduct catalogCategoryProduct = SerializationUtil.mapToTypedObject(
                binlogMessage.getData(), CatalogCategoryProduct.class);
        logger.info("remove category product relation from aerospike = {}", catalogCategoryProduct.getProductId());
        categoryProductCacheService.removeCategoryLinkedToPid( catalogCategoryProduct,
            cacheNameSpace);
    }

    public void insertCategoryProduct(long productId, CacheNameSpace cacheNameSpace) {

        List<CategoryProductMapping> categoryProducts = categoryProductRepository.findByProductId(productId);

        for(CategoryProductMapping cp: categoryProducts){

            CatalogCategoryProduct catalogCategoryProduct =
                    CatalogCategoryProduct.buildCatalogCategoryProduct(cp);

            categoryProductCacheService.updateCategoryProductRecord( catalogCategoryProduct,
                cacheNameSpace);
        }

    }
}
