package com.inditex.prices.domain.port.out;

import com.inditex.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {

    Optional<Price> findApplicablePrice(
            LocalDateTime applicationDate,
            Long productId,
            Long brandId
    );
}