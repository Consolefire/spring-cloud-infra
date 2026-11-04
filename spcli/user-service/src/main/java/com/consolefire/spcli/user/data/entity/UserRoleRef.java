package com.consolefire.spcli.user.data.entity;

import org.springframework.data.relational.core.mapping.Table;

@Table("user_roles")
public record UserRoleRef(
        Long roleId
) {}