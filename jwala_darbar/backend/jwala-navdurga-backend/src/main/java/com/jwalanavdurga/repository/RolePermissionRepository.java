package com.jwalanavdurga.repository;

import com.jwalanavdurga.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository
        extends JpaRepository<RolePermission, Long> {

    List<RolePermission> findByRoleId(Long roleId);
}