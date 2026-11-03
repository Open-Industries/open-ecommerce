package org.openindustries.ecommerce.inventory;

import java.util.UUID;

/**
 * InventoryPool aggregate — the guarded resource account of this core: a balance/capacity that
 * consumption operations draw from. Core invariant (ADR-0004): it NEVER goes negative;
 * an operation that would overdraw it is rejected and records nothing.
 */
public class InventoryPool {
    public UUID id;
        public String sku;
    public long availableUnits;
    public String status;

    public static InventoryPool open(String sku, long availableUnits) {
        InventoryPool a = new InventoryPool();
        a.id = UUID.randomUUID();
        a.sku = sku;
        a.availableUnits = availableUnits;
        a.status = "ACTIVE";
        return a;
    }
}
