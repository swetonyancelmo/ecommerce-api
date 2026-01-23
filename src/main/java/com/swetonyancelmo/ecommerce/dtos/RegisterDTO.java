package com.swetonyancelmo.ecommerce.dtos;

import com.swetonyancelmo.ecommerce.models.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(
        @NotBlank String username,
        @NotBlank String email,
        @NotBlank String password,
        @NotNull UserRole role
        ) {}
