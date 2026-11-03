# ADR-0005: Privacy by design; all reference data is synthetic

| Field | Value |
|---|---|
| Status | Accepted |
| Date | 2026-11-03 |
| Standard traceability | ISO/IEC/IEEE 42010 (architecture decision record) |

## Context

A public reference core must be safe to clone, demo and audit. ISO 27002 A.5.34.

## Decision

Shopper personal data is minimized: this core works with storefront, product and inventory data; payment card data never enters the platform (certified PSP boundary, PCI DSS adjacency). Internal keys are UUIDs and no real personal or confidential data ever enters this repository.

## Consequences

Correlation with production records happens through the adopter's governed processes, not this API.
