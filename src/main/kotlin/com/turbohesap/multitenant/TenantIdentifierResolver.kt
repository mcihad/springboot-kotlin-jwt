package com.turbohesap.multitenant

import org.hibernate.cfg.AvailableSettings
import org.hibernate.context.spi.CurrentTenantIdentifierResolver
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer
import org.springframework.stereotype.Component

@Component
class TenantIdentifierResolver : CurrentTenantIdentifierResolver<Any?>, HibernatePropertiesCustomizer {
    private var currentTenant = "public"

    fun setCurrentTenant(tenant: String) {
        currentTenant = tenant
    }

    override fun resolveCurrentTenantIdentifier(): String? {
        return currentTenant
    }

    override fun validateExistingCurrentSessions(): Boolean {
        return true
    }

    override fun customize(hibernateProperties: MutableMap<String, Any>) {
        hibernateProperties[AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER] = this
    } // empty overrides skipped for brevity
}