# Domain Standards — E-commerce

Alignment mapping for this core. *Aligned to* throughout.

## Concept mapping

| Platform concept | Domain standard | Mapping |
|---|---|---|
| Product / SKU identity | GS1 (GTIN posture) | Catalogue identifiers are adopter-assigned GTINs/SKUs |
| Payments | PCI DSS | **Out of scope by design**: card data is handled only by certified payment service providers; this core receives payment outcomes as events |
| Storefront content contracts | schema.org / WCAG 2.2 | Product data shapes; accessible error contracts (RFC 9457) |
| Inventory integrity | Platform rule | The no-oversell invariant, enforced and tested |

## Scope boundaries

No cardholder data, no payment processing, no tax determination: those live with
certified providers and adopter systems integrated at the boundary.
