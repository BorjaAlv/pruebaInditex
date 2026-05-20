package com.inditex.prices.infrastructure.in.rest.mapper;

import com.inditex.prices.application.dto.ApplicablePriceDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PriceRestMapperTest {

    private final PriceRestMapper mapper = Mappers.getMapper(PriceRestMapper.class);

    @Test
    void shouldMapDtoToResponse() {
        var productId = 35455L;
        var brandId = 1L;
        var priceList = 2;
        var startDate = LocalDateTime.of(2020, 6, 14, 15, 0);
        var endDate = LocalDateTime.of(2020, 6, 14, 18, 30);
        var priceValue = BigDecimal.valueOf(25.45);
        var curr = "EUR";

        var dto = new ApplicablePriceDto(
                productId,
                brandId,
                priceList,
                startDate,
                endDate,
                priceValue,
                curr
        );

        var result = mapper.toResponse(dto);

        assertThat(result.getProductId()).isEqualTo(productId);
        assertThat(result.getBrandId()).isEqualTo(brandId);
        assertThat(result.getPriceList()).isEqualTo(priceList);
        assertThat(result.getStartDate()).isEqualTo(startDate);
        assertThat(result.getEndDate()).isEqualTo(endDate);
        assertThat(result.getPrice()).isEqualTo(priceValue.doubleValue());
        assertThat(result.getCurrency()).isEqualTo(curr);
    }
}