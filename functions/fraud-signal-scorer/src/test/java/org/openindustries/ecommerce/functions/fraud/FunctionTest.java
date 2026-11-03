package org.openindustries.ecommerce.functions.fraud;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void healthyIsNormal() {
        given().contentType("application/json")
                .body("{\"orderId\":\"o-1\",\"velocityIndex\":4,\"mismatchIndex\":2,\"chargebackIndex\":0}")
                .when().post("/fraudSignalScorer")
                .then().statusCode(200).body("decision", is("NORMAL")).body("score", is(0));
    }

    @Test
    void stackedSignalsAreCritical() {
        given().contentType("application/json")
                .body("{\"orderId\":\"o-2\",\"velocityIndex\":95,\"mismatchIndex\":80,\"chargebackIndex\":70}")
                .when().post("/fraudSignalScorer")
                .then().statusCode(200).body("decision", is("CRITICAL")).body("score", is(100));
    }
}
