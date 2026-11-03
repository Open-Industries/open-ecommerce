package org.openindustries.ecommerce.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateProductRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "ELECTRONICS|APPAREL|HOME|BEAUTY") String category,
        @Positive long priceMinor) {
}
