package org.openindustries.ecommerce.functions.sla;

import io.quarkus.funqy.Funq;

import java.util.ArrayList;
import java.util.List;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Compares checkout latency and success rate against storefront targets; one finding per breach.
 * Compares measured values with targets and returns a verdict with one finding per
 * breach — machine-checkable assurance evidence.
 */
public class Function {

    public record Kpi(String storefrontId, int measuredP95LatencyMs, int targetP95LatencyMs,
                      double measuredSuccessRatePct, double targetSuccessRatePct) {
    }

    public record Verdict(String storefrontId, boolean compliant, List<String> findings) {
    }

    @Funq
    public Verdict checkoutSlaEvaluator(Kpi kpi) {
        List<String> findings = new ArrayList<>();
        if (kpi.measuredP95LatencyMs() > kpi.targetP95LatencyMs()) {
            findings.add("p95LatencyMs " + kpi.measuredP95LatencyMs() + " exceeds target " + kpi.targetP95LatencyMs());
        }
        if (kpi.measuredSuccessRatePct() < kpi.targetSuccessRatePct()) {
            findings.add("successRatePct " + kpi.measuredSuccessRatePct() + " below target " + kpi.targetSuccessRatePct());
        }
        return new Verdict(kpi.storefrontId(), findings.isEmpty(), findings);
    }
}
