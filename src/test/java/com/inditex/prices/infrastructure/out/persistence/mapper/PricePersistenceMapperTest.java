package com.inditex.prices.infrastructure.out.persistence.mapper;

import com.inditex.prices.infrastructure.out.persistence.entity.PriceJpaEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PricePersistenceMapperTest {

    private final PricePersistenceMapper mapper = Mappers.getMapper(PricePersistenceMapper.class);

    @Test
    void shouldMapEntityToDomain() {
        var entity = PriceJpaEntity.builder()
                .id(1L)
                .brandId(1L)
                .productId(35455L)
                .priceList(2)
                .priority(1)
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .price(BigDecimal.valueOf(25.45))
                .currency("EUR")
                .build();

        var result = mapper.toDomain(entity);

        assertThat(result.brandId()).isEqualTo(1L);
        assertThat(result.productId()).isEqualTo(35455L);
        assertThat(result.priceList()).isEqualTo(2);
        assertThat(result.priority()).isEqualTo(1);
        assertThat(result.startDate()).isEqualTo(LocalDateTime.of(2020, 6, 14, 15, 0));
        assertThat(result.endDate()).isEqualTo(LocalDateTime.of(2020, 6, 14, 18, 30));
        assertThat(result.price()).isEqualByComparingTo("25.45");
        assertThat(result.currency()).isEqualTo("EUR");
    }
}