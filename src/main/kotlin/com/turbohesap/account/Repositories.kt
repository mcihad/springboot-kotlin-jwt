package com.turbohesap.account

import com.turbohesap.common.repositories.TenantAwareRepository
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<Role, Long> {
    fun findByName(name: String): Role?
    fun existsByName(name: String): Boolean
}

interface UserRepository: JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun existsByUsername(username: String): Boolean
}

interface TenantRepository: JpaRepository<Tenant, Long> {
    fun findByName(name: String): Tenant?
    fun existsByName(name: String): Boolean
}
