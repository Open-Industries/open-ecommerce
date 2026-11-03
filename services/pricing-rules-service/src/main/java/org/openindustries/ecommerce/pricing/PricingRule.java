package org.openindustries.ecommerce.pricing;

import java.util.UUID;

/**
 * PricingRule aggregate — a decision rule of this core: for one segment, it guarantees a
 * capacity (maxUnitsPerOrder) and a threshold (maxReturnRatePct), with a precedence priority.
 * Only ACTIVE rules decide; every decision names its rule and its reason (audit).
 */
public class PricingRule {
    public UUID id;
    public String name;
    public String segment;
    public long maxUnitsPerOrder;
    public int maxReturnRatePct;
    public int priority;
    public String status;

    public static PricingRule create(String name, String segment, long maxUnitsPerOrder, int maxReturnRatePct, int priority) {
        PricingRule r = new PricingRule();
        r.id = UUID.randomUUID();
        r.name = name;
        r.segment = segment;
        r.maxUnitsPerOrder = maxUnitsPerOrder;
        r.maxReturnRatePct = maxReturnRatePct;
        r.priority = priority;
        r.status = "DRAFT";
        return r;
    }
}
