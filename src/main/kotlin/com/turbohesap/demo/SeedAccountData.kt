package com.turbohesap.demo

import com.turbohesap.account.*
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
@Profile("dev")
class SeedAccountData(
    private val tenantRepository: TenantRepository,
    private val roleRepository: RoleRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        var turboHesapTenant = createTenant("nethesap")
        var netHesapTenant = createTenant("turbohesap")

        var adminRole = createRole("ROLE_ADMIN", "Admin role")
        var userRole = createRole("ROLE_USER", "User role")

        createUser("tadmin", "tadmin", turboHesapTenant, setOf(adminRole, userRole))
        createUser("tuser", "tuser", turboHesapTenant, setOf(adminRole))
        createUser("nuser", "nuser", netHesapTenant, setOf(userRole))
        createUser("nadmin", "nadmin", netHesapTenant, setOf(adminRole, userRole))
    }

    private fun createTenant(name: String): Tenant =
        tenantRepository.findByName(name) ?: tenantRepository.save(Tenant(name))

    private fun createRole(name: String, description: String? = null): Role =
        roleRepository.findByName(name) ?: roleRepository.save(Role(name, description))
    private fun createUser(username: String, password: String, tenant: Tenant, roles: Set<Role>): User =
        userRepository.findByUsername(username) ?: userRepository.save(
            User(
                username,
                passwordEncoder.encode(password),
                tenant = tenant,
                authorities = roles.toMutableSet()
            ))
}