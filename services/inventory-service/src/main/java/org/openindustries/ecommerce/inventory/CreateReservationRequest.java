package org.openindustries.ecommerce.inventory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateReservationRequest(
        @NotBlank @Pattern(regexp = "WEB|MOBILE|MARKETPLACE") String channel,
        @Positive long units) {
}
