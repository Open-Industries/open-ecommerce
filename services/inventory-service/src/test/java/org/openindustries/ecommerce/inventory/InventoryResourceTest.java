package org.openindustries.ecommerce.inventory;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class InventoryResourceTest {

    @Test
    void consumptionDrawsDown() {
        String id = given().contentType("application/json")
                .body("{\"sku\":\"SKU-100\",\"availableUnits\":200}")
                .when().post("/api/v1/inventory")
                .then().statusCode(201).body("availableUnits", is(200))
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"channel\":\"WEB\",\"units\":30}")
                .when().post("/api/v1/inventory/" + id + "/reservations")
                .then().statusCode(200)
                .body("remainingAfter", is(170));
    }

    @Test
    void overdrawIsRejected() {
        String id = given().contentType("application/json")
                .body("{\"sku\":\"SKU-100\",\"availableUnits\":200}")
                .when().post("/api/v1/inventory")
                .then().statusCode(201)
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"channel\":\"WEB\",\"units\":500}")
                .when().post("/api/v1/inventory/" + id + "/reservations")
                .then().statusCode(422).body("status", is(422));

        given().when().get("/api/v1/inventory/" + id)
                .then().statusCode(200).body("availableUnits", is(200));
    }
}
