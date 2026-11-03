package org.openindustries.ecommerce.product;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ProductResourceTest {

    @Test
    void createAndTransition() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"Wireless Headphones\",\"category\":\"ELECTRONICS\",\"priceMinor\":4999}")
                .when().post("/api/v1/products")
                .then().statusCode(201)
                .body("status", is("DRAFT"))
                .body("category", is("ELECTRONICS"))
                .extract().path("id");

        given().when().post("/api/v1/products/" + id + "/publish")
                .then().statusCode(200).body("status", is("PUBLISHED"));
    }
}
