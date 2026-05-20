package com.inditex.prices.infrastructure.out.persistence.adapter;

import com.inditex.prices.domain.model.Price;
import com.inditex.prices.infrastructure.out.persistence.entity.PriceJpaEntity;
import com.inditex.prices.infrastructure.out.persistence.mapper.PricePersistenceMapper;
import com.inditex.prices.infrastructure.out.persistence.repository.PriceJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceRepositoryAdapterTest {

    @Mock
    private PriceJpaRepository priceJpaRepository;

    @Mock
    private PricePersistenceMapper pricePersistenceMapper;

    @InjectMocks
    private PriceRepositoryAdapter priceRepositoryAdapter;

    @Test
    void shouldReturnFirstApplicablePrice() {
        var applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        var productId = 35455L;
        var brandId = 1L;
        var startDate = LocalDateTime.of(2020, 6, 14, 15, 0);
        var endDate = LocalDateTime.of(2020, 6, 14, 18, 30);

        var entity = PriceJpaEntity.builder()
                .brandId(brandId)
                .productId(productId)
                .priceList(2)
                .priority(1)
                .startDate(startDate)
                .endDate(endDate)
                .price(BigDecimal.valueOf(25.45))
                .currency("EUR")
                .build();

        var domain = new Price(
                brandId,
                entity.getStartDate(),
                entity.getEndDate(),
                2,
                productId,
                1,
                BigDecimal.valueOf(25.45),
                "EUR"
        );

        when(priceJpaRepository.findApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(List.of(entity));
        when(pricePersistenceMapper.toDomain(entity)).thenReturn(domain);

        var result = priceRepositoryAdapter.findApplicablePrice(applicationDate, productId, brandId);

        assertThat(result).isPresent();
        assertThat(result.get().priceList()).isEqualTo(2);
        assertThat(result.get().price()).isEqualByComparingTo("25.45");

        verify(priceJpaRepository).findApplicablePrice(applicationDate, productId, brandId);
        verify(pricePersistenceMapper).toDomain(entity);
    }

    @Test
    void shouldReturnEmptyWhenNoApplicablePriceExists() {
        var applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        var productId = 35455L;
        var brandId = 1L;

        when(priceJpaRepository.findApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(Collections.emptyList());

        var result = priceRepositoryAdapter.findApplicablePrice(applicationDate, productId, brandId);

        assertThat(result).isEmpty();

        verify(priceJpaRepository).findApplicablePrice(applicationDate, productId, brandId);
        verifyNoInteractions(pricePersistenceMapper);
    }
}