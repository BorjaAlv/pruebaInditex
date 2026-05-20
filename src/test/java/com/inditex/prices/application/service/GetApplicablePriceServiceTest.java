package com.inditex.prices.application.service;

import com.inditex.prices.domain.exception.PriceNotFoundException;
import com.inditex.prices.domain.port.out.PriceRepository;
import com.inditex.prices.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetApplicablePriceServiceTest {

    @Mock
    private PriceRepository priceRepositoryPort;

    @InjectMocks
    private GetApplicablePriceService getApplicablePriceService;

    @Test
    void shouldReturnApplicablePrice() {
        var applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        var productId = 35455L;
        var brandId = 1L;
        var startDate = LocalDateTime.of(2020, 6, 14, 15, 0);
        var endDate = LocalDateTime.of(2020, 6, 14, 18, 30);

        var price = new Price(
                brandId,
                startDate,
                endDate,
                2,
                productId,
                1,
                BigDecimal.valueOf(25.45),
                "EUR"
        );

        when(priceRepositoryPort.findApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(Optional.of(price));

        var result = getApplicablePriceService.getApplicablePrice(
                applicationDate,
                productId,
                brandId
        );

        assertThat(result.productId()).isEqualTo(productId);
        assertThat(result.brandId()).isEqualTo(brandId);
        assertThat(result.priceList()).isEqualTo(2);
        assertThat(result.price()).isEqualByComparingTo("25.45");
        assertThat(result.currency()).isEqualTo("EUR");
        assertThat(result.startDate()).isEqualTo(startDate);
        assertThat(result.endDate()).isEqualTo(endDate);

        verify(priceRepositoryPort).findApplicablePrice(applicationDate, productId, brandId);
    }

    @Test
    void shouldThrowPriceNotFoundExceptionWhenApplicablePriceDoesNotExist() {
        var applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        var productId = 35455L;
        var brandId = 1L;

        when(priceRepositoryPort.findApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> getApplicablePriceService.getApplicablePrice(
                applicationDate,
                productId,
                brandId
        ))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessage("Price not found for productId=%s, brandId=%s and applicationDate=%s"
                        .formatted(productId, brandId, applicationDate));

        verify(priceRepositoryPort).findApplicablePrice(applicationDate, productId, brandId);
    }
}