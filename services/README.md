# Services

One independently deployable Quarkus microservice per bounded context. Every service
follows the same hexagonal layout, exposes `/openapi`, `/q/health` and `/q/metrics`,
returns RFC 9457 problem details, and is configured only through environment variables
(Twelve-Factor). Lifecycle transitions take no body; `@Consumes` appears only on
endpoints that read one.

**Implementation order** (see `../docs/implementation-roadmap.md`):
Storefronts → Inventory → Pricing Rules → Open APIs.

| Service | Port | Alignment |
|---|---|---|
| `storefront-service` | 8081 | Commerce platform topology |
| `inventory-service` | 8082 | Omnichannel inventory integrity (no oversell) |
| `pricing-rules-service` | 8083 | Commercial policy as auditable rules |
| `product-catalog-service` | 8084 | GS1 identifier posture; schema.org product shapes |
| `notification-service` | 8085 | Platform support (shared pattern) |

Run the pilot service:

```bash
mvn -pl services/storefront-service quarkus:dev
# OpenAPI UI: http://localhost:8081/docs — health: http://localhost:8081/q/health
```
