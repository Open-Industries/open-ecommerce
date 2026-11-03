# Storefront Service

Bounded context: **Storefronts** — Storefront registry: one front per market and currency.

Alignment: Commerce platform topology. Lifecycle transitions take no body; secret identifiers,
where the domain has them, are masked at creation and never stored or returned in clear.
All data is synthetic.

| Item | Value |
|---|---|
| Port | `8081` (`HTTP_PORT`) |
| Base path | `/api/v1/storefronts` |
| Owns data | Storefront records |
| Publishes | `storefront.created`, lifecycle events (see `contracts/asyncapi.yaml`) |

Run locally: `mvn -pl services/storefront-service quarkus:dev`.
