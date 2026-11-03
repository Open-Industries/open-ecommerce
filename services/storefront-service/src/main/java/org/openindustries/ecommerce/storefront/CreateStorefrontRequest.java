package org.openindustries.ecommerce.storefront;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateStorefrontRequest(
        @NotBlank String name,
        @NotBlank String marketCode,
        @NotBlank @Pattern(regexp = "USD|EUR|MXN|GBP") String currencyCode) {
}
