package org.openindustries.ecommerce.pricing;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class PricingRuleResourceTest {

    @Test
    void activateThenEvaluateAllowed() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"premium-standard\",\"segment\":\"PREMIUM\",\"maxUnitsPerOrder\":50,\"maxReturnRatePct\":5,\"priority\":1}")
                .when().post("/api/v1/pricing-rules")
                .then().statusCode(201).body("status", is("DRAFT"))
                .extract().path("id");

        given().when().post("/api/v1/pricing-rules/" + id + "/activate")
                .then().statusCode(200).body("status", is("ACTIVE"));

        given().contentType("application/json")
                .body("{\"segment\":\"PREMIUM\",\"requiredMaxReturnRatePct\":8,\"requiredMaxUnitsPerOrder\":20}")
                .when().post("/api/v1/pricing-rules/evaluate")
                .then().statusCode(200).body("decision", is("ALLOWED"));
    }

    @Test
    void evaluateWithoutRuleIsDenied() {
        given().contentType("application/json")
                .body("{\"segment\":\"OUTLET\",\"requiredMaxReturnRatePct\":8,\"requiredMaxUnitsPerOrder\":20}")
                .when().post("/api/v1/pricing-rules/evaluate")
                .then().statusCode(200).body("decision", is("DENIED"));
    }
}
