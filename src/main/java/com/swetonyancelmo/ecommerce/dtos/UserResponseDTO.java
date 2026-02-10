package com.swetonyancelmo.ecommerce.dtos;

import com.swetonyancelmo.ecommerce.models.enums.UserRole;

public record UserResponseDTO(
    Long id,
    String username,
    String email,
    UserRole role
) {}
