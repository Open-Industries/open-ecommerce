# Architecture Description — Open Ecommerce — Reference Commerce Core Platform

**Conformance**: this document is the Architecture Description (AD) of the platform,
structured according to **ISO/IEC/IEEE 42010**: system, stakeholders and concerns,
viewpoints, views, decisions and rationale, and correspondences.

| Field | Value |
|---|---|
| System of interest | Open Ecommerce — Reference Commerce Core Platform (`Open-Industries/open-ecommerce`) |
| Version / status | 1.0 — Baseline |
| Framework | ISO/IEC/IEEE 42010; C4 conventions; Mermaid |
| Domain frameworks | PCI DSS (payment adjacency), GS1 identifiers, WCAG 2.2, schema.org |
| Stack | Java 21, Quarkus 3, Maven reactor; Kubernetes/OpenShift; Knative |

## 1. Purpose

A generic e-commerce core: the capabilities every organization in this vertical needs —
storefronts, inventory, pricing rules and products —
designed to **deploy as-is for a reference environment and adapt by configuration**
rather than by forking code. Architectural style: microservices (one deployable per
bounded context) plus serverless functions for event-driven work, Twelve-Factor aligned.

## 2. Stakeholders and concerns (42010 §5.3)

| Stakeholder | Role | Key concerns |
|---|---|---|
| Adopting organization | Owns and operates the platform | Adaptability, cost, time-to-market |
| Platform / SRE team | Runs Kubernetes/OpenShift | Reliability, observability, safe change |
| Developers & integrators | Build on the APIs | Contract stability, predictable errors (RFC 9457), synthetic sandboxes |
| CISO / privacy officer | ISMS (ISO 27001) | Access control, secrets, audit, PII handling |
| Auditors / regulators | Oversight | Traceability, evidence, retention |
| Merchants / storefront owners | Sell across channels | No oversell, predictable contracts |
| Operations | Run inventory and fulfilment | Append-only reservation evidence |
| Growth / pricing teams | Set commercial rules | Reasoned pricing decisions with audit trail |

## 3. Architecture viewpoints (42010 §5.4)

| Viewpoint | Frames concerns of | Model kinds |
|---|---|---|
| VP-1 Context | Adopters, auditors | C4 System Context |
| VP-2 Container | Architects, developers, SRE | C4 Container; sync REST vs async CloudEvents |
| VP-3 Component | Developers | Hexagonal (ports & adapters) per service |
| VP-4 Domain | Product, domain experts | Bounded contexts (`domain-model.md`) |
| VP-5 Data | Architects, privacy | Database-per-service (`../data/data-architecture.md`) |
| VP-6 Deployment | SRE | Kubernetes + Knative topology |
| VP-7 Security | CISO, auditors | Trust boundaries, OIDC, ISO 27002 mapping (`../security/`) |
| VP-8 Quality & operations | SRE, product | ISO 25010 model; SLOs (`../operations/slos.md`) |

## 4. Architecture views (summary)

- **Context / Container**: see `diagrams.md` (D-1, D-2) and the README table — five
  services (storefront-service, inventory-service, pricing-rules-service, product-catalog-service, notification-service) and two functions
  (fraud-signal-scorer, checkout-sla-evaluator), one database per service,
  CloudEvents backbone, API gateway at the edge.
- **Component**: identical hexagonal layout in every service (REST adapter → application
  service → domain → repository port). Learn one service, learn all of them.
- **Domain**: `domain-model.md` — contexts, aggregates and invariants, above all:
  sellable inventory is never oversold — a reservation that exceeds available units is rejected (422) and records nothing, on every channel.
- **Data**: database-per-service; records are append-only evidence; Shopper personal data is minimized: this core works with storefront, product and inventory data; payment card data never enters the platform (certified PSP boundary, PCI DSS adjacency).
- **Deployment**: same topology per environment; immutable images promoted, never rebuilt.
- **Security**: OIDC at the edge, mTLS inside, per-service least-privilege credentials,
  secrets only from the platform store, audit logging of every state change.
- **Quality**: ISO 25010 scenarios QS-01…QS-08 in `quality-attributes.md`; SLOs per service.

## 5. Architecture decisions (42010 §5.6)

Recorded as ADRs in `docs/adr/`: 0001 microservices + serverless; 0002 Java/Quarkus;
0003 database per service; **0004 the domain invariant** (sellable inventory is never oversold — a reservation that exceeds available units is rejected (422) and records nothing, on every channel.);
0005 privacy & synthetic data; 0006 domain-standards alignment (PCI DSS (payment adjacency), GS1 identifiers, WCAG 2.2, schema.org);
0007 API-first + RFC 9457 + CloudEvents; 0008 mandatory idempotency; 0009 security model
(OIDC/mTLS/platform secrets); 0010 Twelve-Factor binding.

## 6. Correspondences and consistency rules (42010 §5.7)

- **CR-1** Every container maps to exactly one Maven module and one manifest set in `deploy/`.
- **CR-2** Every public operation appears in `contracts/` or is generated from code and
  verified in CI; drift fails the build.
- **CR-3** Every event named in any view appears in `contracts/asyncapi.yaml` (CloudEvents).
- **CR-4** Each context owns exactly one database schema; cross-context database access
  is an architecture violation.
- **CR-5** Every domain-standard concept named in a view appears in
  `../domain/domain-standards.md` with its platform mapping.

## 7. Rationale and trade-offs

Bounded contexts keep the domain invariant enforceable in exactly one place and let each
capability evolve independently; serverless functions fit bursty scoring/evaluation work
at zero idle cost; eventual consistency between contexts is accepted and made explicit,
with each context's records as its own evidence of record. Reference persistence is
in-memory for zero-setup evaluation; production adopters select the JPA adapter by
configuration. Scope boundaries and exclusions are stated in
`../domain/domain-standards.md`.
