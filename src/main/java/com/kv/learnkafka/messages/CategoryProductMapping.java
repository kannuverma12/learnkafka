package com.kv.learnkafka.messages;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

class CategoryProductMappingId implements Serializable{
     long productId;
     long categoryId;
}

@Entity
@Data
@IdClass(CategoryProductMappingId.class)
@Table(name = "catalog_category_product")
public class CategoryProductMapping implements Serializable{
    @Id
    @Column(name = "product_id")

    private long productId;

    @Id
    @Column(name = "category_id")
    private long categoryId;

    @Column(name = "priority")
    private long priority;

    @Override
    public String toString() {
        return "{" +
                "category_id:" + categoryId +
                ", product_id:" + productId +
                ", priority:" + priority +
                '}';
    }
}