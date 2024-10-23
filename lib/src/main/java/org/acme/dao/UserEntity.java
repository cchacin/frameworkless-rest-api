package org.acme.dao;

import java.util.UUID;

public record UserEntity(
        UUID userId,
        String firstName,
        String lastName
) {
}
