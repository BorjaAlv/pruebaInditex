package com.inditex.prices.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ApplicablePriceDto(
        Long productId,
        Long brandId,
        Integer priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        String currency
) {
}
