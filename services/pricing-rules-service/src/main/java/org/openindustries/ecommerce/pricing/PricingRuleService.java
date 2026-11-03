package org.openindustries.ecommerce.pricing;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PricingRuleService {

    @Inject
    PricingRuleRepository repository;

    public PricingRule create(CreatePricingRuleRequest request) {
        return repository.save(PricingRule.create(request.name(), request.segment(),
                request.maxUnitsPerOrder(), request.maxReturnRatePct(), request.priority()));
    }

    public PricingRule get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "PricingRule not found: " + id));
    }

    public List<PricingRule> list() {
        return repository.findAll();
    }

    public PricingRule activate(UUID id) {
        PricingRule rule = get(id);
        rule.status = "ACTIVE";
        return repository.save(rule);
    }

    public Evaluation evaluate(EvaluateRequest request) {
        var candidates = repository.activeFor(request.segment());
        if (candidates.isEmpty()) {
            return new Evaluation("DENIED", null,
                    "No active rule for " + request.segment());
        }
        for (PricingRule rule : candidates) {
            if (rule.maxReturnRatePct <= request.requiredMaxReturnRatePct()
                    && rule.maxUnitsPerOrder >= request.requiredMaxUnitsPerOrder()) {
                return new Evaluation("ALLOWED", rule.id,
                        "Rule '" + rule.name + "' covers the request");
            }
        }
        return new Evaluation("DEGRADED", candidates.get(0).id,
                "Active rules exist for " + request.segment() + " but none meets the requirement");
    }
}
