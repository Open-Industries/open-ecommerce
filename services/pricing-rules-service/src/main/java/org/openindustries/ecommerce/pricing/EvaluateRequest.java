package org.openindustries.ecommerce.pricing;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record EvaluateRequest(
        @NotBlank @Pattern(regexp = "STANDARD|PREMIUM|OUTLET") String segment,
        @Positive int requiredMaxReturnRatePct,
        @Positive long requiredMaxUnitsPerOrder) {
}
