package com.inditex.prices.infrastructure.in.rest.controller;

import com.inditex.prices.application.dto.ApplicablePriceDto;
import com.inditex.prices.domain.port.in.GetApplicablePriceUseCase;
import com.inditex.prices.generated.model.PriceResponse;
import com.inditex.prices.infrastructure.in.rest.mapper.PriceRestMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

    @Mock
    private GetApplicablePriceUseCase getApplicablePriceUseCase;

    @Mock
    private PriceRestMapper priceRestMapper;

    @InjectMocks
    private PriceController priceController;

    @Test
    void shouldReturnApplicablePrice() {
        var applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        var productId = 35455L;
        var brandId = 1L;

        var dto = new ApplicablePriceDto(
                productId,
                brandId,
                2,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                BigDecimal.valueOf(25.45),
                "EUR"
        );

        var response = new PriceResponse()
                .productId(productId)
                .brandId(brandId)
                .priceList(2)
                .startDate(dto.startDate())
                .endDate(dto.endDate())
                .price(25.45)
                .currency("EUR");

        when(getApplicablePriceUseCase.getApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(dto);
        when(priceRestMapper.toResponse(dto)).thenReturn(response);

        var result = priceController.getApplicablePrice(applicationDate, productId, brandId);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.getBody()).isEqualTo(response);

        verify(getApplicablePriceUseCase).getApplicablePrice(applicationDate, productId, brandId);
        verify(priceRestMapper).toResponse(dto);
    }
}