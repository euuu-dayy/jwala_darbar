package com.jwalanavdurga.repository;

import com.jwalanavdurga.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRepository
        extends JpaRepository<UserRole, Long> {

    List<UserRole> findByUserId(Long userId);

    @Query(value = """
            SELECT DISTINCT p.name
            FROM user_roles ur
            JOIN role_permissions rp
                ON rp.role_id = ur.role_id
            JOIN permissions p
                ON p.id = rp.permission_id
            JOIN roles r
                ON r.id = rp.role_id
            WHERE ur.user_id = :userId
              AND p.deleted_at IS NULL
              AND r.deleted_at IS NULL
            ORDER BY p.name
            """, nativeQuery = true)
    List<String> findPermissionNamesByUserId(
            @Param("userId") Long userId
    );

    @Query(value = """
            SELECT DISTINCT r.name
            FROM user_roles ur
            JOIN roles r
                ON r.id = ur.role_id
            WHERE ur.user_id = :userId
              AND r.deleted_at IS NULL
            ORDER BY r.name
            """, nativeQuery = true)
    List<String> findRoleNamesByUserId(
            @Param("userId") Long userId
    );
}