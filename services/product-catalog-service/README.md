# Product Catalog Service

Bounded context: **Products** — Product catalogue with minor-unit prices.

Alignment: GS1 identifier posture; schema.org product shapes. Lifecycle transitions take no body. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8084` (`HTTP_PORT`) |
| Base path | `/api/v1/products` |
| Owns data | Product records |

Run locally: `mvn -pl services/product-catalog-service quarkus:dev`.
