package org.openindustries.ecommerce.inventory;

import java.time.Instant;
import java.util.UUID;

/** One guarded consumption record — the auditable evidence of a draw on a InventoryPool. */
public class Reservation {
    public UUID id;
    public UUID accountId;
    public String channel;
    public long units;
    public long remainingAfter;
    public Instant recordedAt;

    public static Reservation of(UUID accountId, String channel, long units, long remainingAfter) {
        Reservation r = new Reservation();
        r.id = UUID.randomUUID();
        r.accountId = accountId;
        r.channel = channel;
        r.units = units;
        r.remainingAfter = remainingAfter;
        r.recordedAt = Instant.now();
        return r;
    }
}
