package com.consolefire.spcli.user.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Table("users")
public record User(
        @Id Long id,
        String username,
        String email,
        String passwordHash,
        boolean enabled,
        Instant createdAt,
        @MappedCollection(idColumn = "user_id")
        Set<UserRoleRef> roles
) {}