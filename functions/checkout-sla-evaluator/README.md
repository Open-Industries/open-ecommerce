# Checkout SLA Evaluator (serverless function)

Quarkus Funqy function on Knative (scale to zero). Compares checkout latency and success rate against storefront targets; one finding per breach.

Invoke: `POST /checkoutSlaEvaluator` with the KPI payload.
