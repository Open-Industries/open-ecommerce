package org.openindustries.ecommerce.product;

import java.util.UUID;

/** Product aggregate — Product catalogue with minor-unit prices. */
public class Product {
    public UUID id;
        public String name;
        public String category;
        public long priceMinor;
    public String status;

    public static Product create(String name, String category, long priceMinor) {
        Product e = new Product();
        e.id = UUID.randomUUID();
        e.name = name;
        e.category = category;
        e.priceMinor = priceMinor;
        e.status = "DRAFT";
        return e;
    }
}
