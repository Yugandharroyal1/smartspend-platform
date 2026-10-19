package com.yugandhar.smartspend.repository;
import com.yugandhar.smartspend.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByProductId(Long productId);
    List<Price> findByStoreId(Long storeId);
}