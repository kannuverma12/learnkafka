package com.kv.learnkafka.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public class BinlogMessage {
    @JsonProperty("database")
    private String database;

    @JsonProperty("table")
    private String table;

    @JsonProperty("type")
    private String type;

    @JsonProperty("ts")
    private long timestamp;

    @JsonProperty("xid")
    private long transactionId;

    @JsonProperty("data")
    private Map<String, Object> data;

    @JsonProperty("old")
    private Map<String, Object> old;

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String,Object> data) {
        this.data = data;
    }

    public Map<String, Object> getOld() {
        return old;
    }

    public void setOld(Map<String, Object> old) {
        this.old = old;
    }

    @Override
    public String toString() {
        return "BinlogMessage{" +
                "database='" + database + '\'' +
                ", table='" + table + '\'' +
                ", type='" + type + '\'' +
                ", timestamp=" + timestamp +
                ", transactionId=" + transactionId +
                ", data=" + data +
                ", old=" + old +
                '}';
    }
}
