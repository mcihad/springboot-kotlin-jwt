package com.turbohesap.account

import com.turbohesap.common.models.AbstractJpaPersistable
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
@Table(name = "roles")
class Role(
    var name: String,
    var description: String? = null,
    @ManyToMany(mappedBy = "authorities")
    var users: MutableSet<User>? = mutableSetOf()
) : AbstractJpaPersistable<Long>(), GrantedAuthority {
    override fun getAuthority(): String {
        return name
    }
}

@Entity
@Table(name = "users")
class User(
    private var username: String,
    private var password: String,
    private var accountNonExpired: Boolean = true,
    private var accountNonLocked: Boolean = true,
    private var credentialsNonExpired: Boolean = true,
    private var enabled: Boolean = true,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_authorities",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "authority_id", referencedColumnName = "id")]
    )
    private var authorities: MutableSet<Role> = mutableSetOf(),
    @ManyToOne
    private var tenant: Tenant
) : UserDetails, AbstractJpaPersistable<Long>() {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities
    }

    override fun getPassword() = password
    override fun getUsername() = username
    override fun isAccountNonExpired() = accountNonExpired
    override fun isAccountNonLocked() = accountNonLocked
    override fun isCredentialsNonExpired() = credentialsNonExpired
    override fun isEnabled() = enabled
    fun getTenant() = tenant
}

@Entity
@Table(name = "tenants")
class Tenant(
    var name: String,
    var description: String? = null,
    private var tenantId: String = UUID.randomUUID().toString(),
    @OneToMany(mappedBy = "tenant")
    var users: MutableSet<User> = mutableSetOf()
) : AbstractJpaPersistable<Long>() {
    override fun toString(): String {
        return "Tenant(id=${getId()}, name='$name', description=$description)"
    }
    fun getTenantId() = tenantId
}