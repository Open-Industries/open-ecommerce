package org.openindustries.ecommerce.functions.sla;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void compliant() {
        given().contentType("application/json")
                .body("{\"storefrontId\":\"s-1\",\"measuredP95LatencyMs\":320,\"targetP95LatencyMs\":500,\"measuredSuccessRatePct\":99.6,\"targetSuccessRatePct\":99.0}")
                .when().post("/checkoutSlaEvaluator")
                .then().statusCode(200).body("compliant", is(true)).body("findings.size()", is(0));
    }

    @Test
    void breachIsReported() {
        given().contentType("application/json")
                .body("{\"storefrontId\":\"s-2\",\"measuredP95LatencyMs\":1400,\"targetP95LatencyMs\":500,\"measuredSuccessRatePct\":99.6,\"targetSuccessRatePct\":99.0}")
                .when().post("/checkoutSlaEvaluator")
                .then().statusCode(200).body("compliant", is(false)).body("findings.size()", is(1));
    }
}
