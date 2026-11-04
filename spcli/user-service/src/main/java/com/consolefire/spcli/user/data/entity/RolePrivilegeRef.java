package com.consolefire.spcli.user.data.entity;

import org.springframework.data.relational.core.mapping.Table;

@Table("role_privileges")
public record RolePrivilegeRef(
        Long privilegeId
) {
}