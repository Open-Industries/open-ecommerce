package org.openindustries.ecommerce.pricing;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreatePricingRuleRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "STANDARD|PREMIUM|OUTLET") String segment,
        @Positive long maxUnitsPerOrder,
        @Positive int maxReturnRatePct,
        @Min(1) @Max(100) int priority) {
}
