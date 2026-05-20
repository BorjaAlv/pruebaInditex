package com.inditex.prices.infrastructure.out.persistence.adapter;

import com.inditex.prices.domain.port.out.PriceRepository;
import com.inditex.prices.domain.model.Price;
import com.inditex.prices.infrastructure.out.persistence.mapper.PricePersistenceMapper;
import com.inditex.prices.infrastructure.out.persistence.repository.PriceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepository {

    private final PriceJpaRepository priceJpaRepository;
    private final PricePersistenceMapper pricePersistenceMapper;

    @Override
    public Optional<Price> findApplicablePrice(
            LocalDateTime applicationDate,
            Long productId,
            Long brandId
    ) {
        return priceJpaRepository.findApplicablePrice(applicationDate, productId, brandId)
                .stream()
                .findFirst()
                .map(pricePersistenceMapper::toDomain);
    }
}