package org.openindustries.ecommerce.pricing;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class PricingRuleRepository {
    private final ConcurrentHashMap<UUID, PricingRule> store = new ConcurrentHashMap<>();

    public PricingRule save(PricingRule rule) {
        store.put(rule.id, rule);
        return rule;
    }

    public Optional<PricingRule> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<PricingRule> findAll() {
        return List.copyOf(store.values());
    }

    /** Active rules for a scope, highest priority (lowest number) first. */
    public List<PricingRule> activeFor(String segment) {
        return store.values().stream()
                .filter(r -> r.segment.equals(segment) && "ACTIVE".equals(r.status))
                .sorted(Comparator.comparingInt(r -> r.priority))
                .toList();
    }
}
