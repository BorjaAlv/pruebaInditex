Feature: Prices API E2E tests

  Background:
    * url baseUrl

  Scenario: Test 1 - price at 10:00 on 14th
    Given path '/api/v1/prices'
    And param applicationDate = '2020-06-14T10:00:00'
    And param productId = 35455
    And param brandId = 1
    When method GET
    Then status 200
    And match response.productId == 35455
    And match response.brandId == 1
    And match response.priceList == 1
    And match response.startDate == '2020-06-14T00:00:00'
    And match response.endDate == '2020-12-31T23:59:59'
    And match response.price == 35.5
    And match response.currency == 'EUR'

  Scenario: Test 2 - price at 16:00 on 14th
    Given path '/api/v1/prices'
    And param applicationDate = '2020-06-14T16:00:00'
    And param productId = 35455
    And param brandId = 1
    When method GET
    Then status 200
    And match response.productId == 35455
    And match response.brandId == 1
    And match response.priceList == 2
    And match response.startDate == '2020-06-14T15:00:00'
    And match response.endDate == '2020-06-14T18:30:00'
    And match response.price == 25.45
    And match response.currency == 'EUR'

  Scenario: Test 3 - price at 21:00 on 14th
    Given path '/api/v1/prices'
    And param applicationDate = '2020-06-14T21:00:00'
    And param productId = 35455
    And param brandId = 1
    When method GET
    Then status 200
    And match response.productId == 35455
    And match response.brandId == 1
    And match response.priceList == 1
    And match response.startDate == '2020-06-14T00:00:00'
    And match response.endDate == '2020-12-31T23:59:59'
    And match response.price == 35.5
    And match response.currency == 'EUR'

  Scenario: Test 4 - price at 10:00 on 15th
    Given path '/api/v1/prices'
    And param applicationDate = '2020-06-15T10:00:00'
    And param productId = 35455
    And param brandId = 1
    When method GET
    Then status 200
    And match response.productId == 35455
    And match response.brandId == 1
    And match response.priceList == 3
    And match response.startDate == '2020-06-15T00:00:00'
    And match response.endDate == '2020-06-15T11:00:00'
    And match response.price == 30.5
    And match response.currency == 'EUR'

  Scenario: Test 5 - price at 21:00 on 16th
    Given path '/api/v1/prices'
    And param applicationDate = '2020-06-16T21:00:00'
    And param productId = 35455
    And param brandId = 1
    When method GET
    Then status 200
    And match response.productId == 35455
    And match response.brandId == 1
    And match response.priceList == 4
    And match response.startDate == '2020-06-15T16:00:00'
    And match response.endDate == '2020-12-31T23:59:59'
    And match response.price == 38.95
    And match response.currency == 'EUR'