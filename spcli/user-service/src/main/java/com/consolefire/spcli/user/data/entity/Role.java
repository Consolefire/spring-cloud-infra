package com.consolefire.spcli.user.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table("roles")
public record Role(
        @Id Long id,
        String name,
        String description,
        @MappedCollection(idColumn = "role_id")
        Set<RolePrivilegeRef> privileges
) {
}
