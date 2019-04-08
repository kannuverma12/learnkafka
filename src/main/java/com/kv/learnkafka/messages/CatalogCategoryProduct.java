package com.kv.learnkafka.messages;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
public class CatalogCategoryProduct {
    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("priority")
    private Long priority;

    public CatalogCategoryProduct() {
    }

    public CatalogCategoryProduct(Long categoryId, Long productId, Long priority) {
        this.categoryId = categoryId;
        this.productId = productId;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "CatalogCategoryProduct{" +
                "categoryId=" + categoryId +
                ", productId=" + productId +
                ", priority=" + priority +
                '}';
    }

    public static CatalogCategoryProduct buildCatalogCategoryProduct(CategoryProductMapping cp){
        return CatalogCategoryProduct.builder()
                .categoryId(cp.getCategoryId())
                .productId(cp.getProductId())
                .priority(cp.getPriority())
                .build();
    }
}
