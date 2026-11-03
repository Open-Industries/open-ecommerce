# Fraud Signal Scorer (serverless function)

Quarkus Funqy function on Knative (scale to zero). Scores checkout risk signals (velocity, data mismatch, chargeback history) with an auditable rationale.

Invoke: `POST /fraudSignalScorer` with the event payload.
