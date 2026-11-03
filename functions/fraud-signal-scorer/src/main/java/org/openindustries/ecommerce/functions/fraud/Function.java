package org.openindustries.ecommerce.functions.fraud;

import io.quarkus.funqy.Funq;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Scores checkout risk signals (velocity, data mismatch, chargeback history) with an auditable rationale.
 * Transparent, auditable rules: NORMAL (< 35), WARNING (35-69), CRITICAL (>= 70);
 * the rationale is returned with the score for audit (ISO/IEC 27002 A.8.15).
 * Stateless by design (12-Factor VI): all state arrives in the request.
 */
public class Function {

    public record Event(String orderId, int velocityIndex, int mismatchIndex, int chargebackIndex) {
    }

    public record Score(String orderId, int score, String decision, String rationale) {
    }

    @Funq
    public Score fraudSignalScorer(Event event) {
        int score = 0;
        StringBuilder why = new StringBuilder();
        if (event.velocityIndex() >= 70) {
            score += 40;
            why.append("velocityIndex >= 70; ");
        }
        if (event.mismatchIndex() >= 60) {
            score += 30;
            why.append("mismatchIndex >= 60; ");
        }
        if (event.chargebackIndex() >= 50) {
            score += 30;
            why.append("chargebackIndex >= 50; ");
        }
        String decision = score >= 70 ? "CRITICAL" : score >= 35 ? "WARNING" : "NORMAL";
        return new Score(event.orderId(), score, decision,
                why.length() == 0 ? "no signals" : why.toString().trim());
    }
}
