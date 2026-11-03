package org.openindustries.ecommerce.storefront;

import java.time.Instant;
import java.util.UUID;

/**
 * Storefront aggregate — Storefront registry: one front per market and currency.
 *
 * Privacy by design (ISO/IEC 27002 A.5.34): secret identifiers are validated at
 * creation and NEVER persisted or returned in clear — only a masked form is kept.
 * All identifiers in this reference implementation are synthetic.
 */
public class Storefront {
    public UUID id;
        public String name;
        public String marketCode;
        public String currencyCode;
    public String status;
    public Instant createdAt;

    public static Storefront create(String name, String marketCode, String currencyCode) {
        Storefront e = new Storefront();
        e.id = UUID.randomUUID();
        e.name = name;
        e.marketCode = marketCode;
        e.currencyCode = currencyCode;
        e.status = "ACTIVE";
        e.createdAt = Instant.now();
        return e;
    }
}
