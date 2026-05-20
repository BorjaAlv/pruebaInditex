package com.inditex.prices.infrastructure.in.rest.exception;

import com.inditex.prices.domain.exception.PriceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandlePriceNotFoundException() {
        var request = new MockHttpServletRequest();
        var productId = 35455L;
        var brandId = 1L;
        var applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        request.setRequestURI("/api/v1/prices");

        var exception = new PriceNotFoundException(
                productId,
                brandId,
                applicationDate
        );

        var response = handler.handlePriceNotFound(exception, request);

        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(404);
        assertThat(response.getBody().getError()).isEqualTo("Not Found");
        assertThat(response.getBody().getMessage()).contains("Price not found for productId=%s, brandId=%s and applicationDate=%s"
                .formatted(productId, brandId, applicationDate));
        assertThat(response.getBody().getPath()).isEqualTo("/api/v1/prices");
    }

    @Test
    void shouldHandleBadRequestException() {
        var request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/prices");

        var exception = new MissingServletRequestParameterException(
                "applicationDate",
                "LocalDateTime"
        );

        var response = handler.handleBadRequest(exception, request);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(400);
        assertThat(response.getBody().getError()).isEqualTo("Bad Request");
        assertThat(response.getBody().getMessage()).contains("applicationDate");
        assertThat(response.getBody().getPath()).isEqualTo("/api/v1/prices");
    }

    @Test
    void shouldHandleUnexpectedException() {
        var request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/prices");

        var response = handler.handleUnexpectedError(
                new RuntimeException("Boom"),
                request
        );

        assertThat(response.getStatusCode().value()).isEqualTo(500);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(500);
        assertThat(response.getBody().getError()).isEqualTo("Internal Server Error");
        assertThat(response.getBody().getMessage()).isEqualTo("Unexpected internal error");
        assertThat(response.getBody().getPath()).isEqualTo("/api/v1/prices");
    }
}