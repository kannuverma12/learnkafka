package com.kv.learnkafka.repository;

import com.kv.learnkafka.messages.CategoryProductMapping;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryProductMappingRepository extends
    JpaRepository<CategoryProductMapping, Long> {
   List<CategoryProductMapping> findByProductId(long productId);
}
