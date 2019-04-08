package com.kv.learnkafka.service;

import com.kv.learnkafka.messages.BinlogMessage;
import com.kv.learnkafka.utils.CacheNameSpace;
import com.kv.learnkafka.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("BinlogProcessorService")
public class BinlogProcessorService {
    private static final Logger logger = LoggerFactory.getLogger(BinlogProcessorService.class);

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryProductService categoryProductService;

    public void processBinlog(BinlogMessage binlogMessage, CacheNameSpace cacheNameSpace) {
        if (binlogMessage == null || binlogMessage.getData() == null) {
            logger.info("Not eligible for further processing");
            return;
        }
        logger.debug("binlog update table = {}", binlogMessage.getTable());

        switch (binlogMessage.getTable()) {
            case Constants.CATALOG_CATEGORY_TABLE: categoryService.updateCategory(binlogMessage,
                cacheNameSpace);break;
            case Constants.CATALOG_CATEGORY_PRODUCT_TABLE: categoryProductService.updateCategoryProduct(binlogMessage,
                cacheNameSpace);break;
        }
    }


    public String generateKey(BinlogMessage binlogMessage) {

        String binlogKey = binlogMessage.getTable() + Constants.UNDERSCORE;
        if (binlogMessage.getTable().equals(Constants.CATALOG_CATEGORY_PRODUCT_TABLE)) {
            binlogKey = binlogKey + binlogMessage.getData().get(Constants.CATEGORY_ID_FIELD).toString() +
                    Constants.UNDERSCORE + binlogMessage.getData().get(Constants.PRODUCT_ID_FIELD);
        }
        else binlogKey = binlogKey + binlogMessage.getData().get(Constants.ID_FIELD).toString();

        return binlogKey;
    }

    public void modifyUpdatebinlogToInsert(BinlogMessage updateBinlog) {
        updateBinlog.setType(Constants.INSERT_TYPE);
        updateBinlog.setOld(null);
    }
}
