package com.inditex.prices.infrastructure.out.persistence.repository;

import com.inditex.prices.infrastructure.out.persistence.entity.PriceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceJpaRepository extends JpaRepository<PriceJpaEntity, Long> {

    @Query("""
        SELECT p
        FROM PriceJpaEntity p
        WHERE p.brandId = :brandId
          AND p.productId = :productId
          AND :applicationDate BETWEEN p.startDate AND p.endDate
        ORDER BY p.priority DESC
    """)
    List<PriceJpaEntity> findApplicablePrice(
            LocalDateTime applicationDate,
            Long productId,
            Long brandId
    );
}