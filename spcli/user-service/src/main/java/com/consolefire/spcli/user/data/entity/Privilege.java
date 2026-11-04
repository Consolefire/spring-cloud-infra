package com.consolefire.spcli.user.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("privileges")
public record Privilege(
        @Id Long id,
        String name,
        String description
) {
}
