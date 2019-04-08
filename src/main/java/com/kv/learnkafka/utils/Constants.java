package com.kv.learnkafka.utils;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String COLON_SEPARATOR = ":";

    public static final String UNDERSCORE = "_";

    public static final String INSERT_TYPE = "insert";
    public static final String BOOTSTRAP_INSERT_TYPE = "bootstrap-insert";
    public static final String UPDATE_TYPE = "update";
    public static final String REMOVE_TYPE = "delete";
    public static final String ID_FIELD = "id";
    public static final String CATEGORY_ID_FIELD = "category_id";
    public static final String PRODUCT_ID_FIELD = "product_id";
    public static final int CONSUMER_CONCURRENCY = 5;
    public static final String BLANK = "";
    public static final List<String> ALLOWED_TABLE_LIST = Arrays.asList("catalog_category_product", "catalog_category");
    public static final String CATALOG_CATEGORY_TABLE = "catalog_category";
    public static final String CATALOG_CATEGORY_PRODUCT_TABLE = "catalog_category_product";
    public static final int NO_EXPIRE = -1;
    public static final String CATEGORY_PREFIX = "category_";
    public static final String PRODUCT_PREFIX = "product_";
    public static final String CCP_SET_KEY = "ccp_key";
    public static final String CATEGORY_SET_KEY = "catalog_category_key";
    public static final String CCP_AEROSPIKE_SETS = "catalog_category_product";
    public static final String CATEGORY_AEROSPIKE_SETS = "catalog_category";
    public static final String CCP_AEROSPIKE_OTHER_SETS = "catalog_category_product_other";
    public static final String CATEGORY_AEROSPIKE_OTHER_SETS = "catalog_category_other";
    public static final String CATEGORY_STATUS = "status";
    public static final String CATEGORY_INFO = "info";
    public static final String CATEGORIES = "categories";

}

