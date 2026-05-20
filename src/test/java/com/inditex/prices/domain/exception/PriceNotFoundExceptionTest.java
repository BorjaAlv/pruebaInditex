package com.inditex.prices.domain.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PriceNotFoundExceptionTest {

    @Test
    void shouldBuildExceptionMessage() {
        var applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        var productId = 35455L;
        var brandId = 1L;

        var exception = new PriceNotFoundException(35455L, 1L, applicationDate);

        assertThat(exception.getMessage())
                .isEqualTo("Price not found for productId=%s, brandId=%s and applicationDate=%s"
                        .formatted(productId, brandId, applicationDate));
    }
}