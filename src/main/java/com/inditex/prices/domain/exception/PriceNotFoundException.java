package com.inditex.prices.domain.exception;

import java.time.LocalDateTime;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(Long productId, Long brandId, LocalDateTime applicationDate) {
        super("Price not found for productId=%s, brandId=%s and applicationDate=%s"
                .formatted(productId, brandId, applicationDate));
    }
}