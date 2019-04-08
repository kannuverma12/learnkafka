package com.kv.learnkafka.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class CategoryProductMessage implements Serializable{
    @JsonProperty("productId")
    private long productId;

    @JsonProperty("timestamp")
    private long timestamp;

    public long getProductId(){
        return productId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"timestamp\":" + timestamp +
                ", \"productId\":" + productId +
                '}';
    }

}
