package com.inditex.prices.e2e;

import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = "server.port=8081"
)
public class PricesKarateE2ETest {

    @Karate.Test
    Karate runPricesE2E() {
        return Karate.run("classpath:features");
    }
}