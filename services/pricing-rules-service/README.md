# Pricing Rules Service

Bounded context: **Pricing Rules** — Decision rules binding customer segments to order-size and returns envelopes.

Alignment: Commercial policy as auditable rules. Decisions are `ALLOWED` / `DEGRADED` / `DENIED`, always
with the matched rule and a human-readable reason for audit. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8083` (`HTTP_PORT`) |
| Base path | `/api/v1/pricing-rules` |
| Owns data | PricingRule rules |
| Publishes | `pricing.activated` |

Run locally: `mvn -pl services/pricing-rules-service quarkus:dev`.
