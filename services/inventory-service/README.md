# Inventory Service

Bounded context: **Inventory** — Guarded inventory pools per SKU and the channel reservations against them.

Alignment: Omnichannel inventory integrity (no oversell). Core invariant, enforced in code and tests: **the guarded
balance/capacity never goes negative** — over-drawing operations are rejected with `422`
and nothing is recorded. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8082` (`HTTP_PORT`) |
| Base path | `/api/v1/inventory` |
| Owns data | InventoryPool accounts, Reservation records |
| Publishes | `inventory.consumed`, `inventory.insufficient` |

Run locally: `mvn -pl services/inventory-service quarkus:dev`.
