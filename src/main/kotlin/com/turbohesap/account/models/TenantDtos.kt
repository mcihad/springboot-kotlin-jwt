package com.turbohesap.account.models

import com.turbohesap.account.Tenant

data class CreateTenantRequest(
    val name: String,
    val description: String? = null,
) {
    /**
     * Rules for validation:
     * - name should not be empty
     * - name should not be longer than 100 characters
     * - description should not be longer than 500 characters
     * response (bool string) : true, "name should not be empty"
     */
    fun isValid(): Pair<Boolean, String> {
        if (name.isEmpty()) {
            return Pair(false, "ad alanı boş olamaz")
        }
        if (name.length > 100) {
            return Pair(false, "ad alanı 100 karakterden uzun olamaz")
        }
        if (description != null && description.length > 500) {
            return Pair(false, "açıklama alanı 500 karakterden uzun olamaz")
        }
        return Pair(true, "")
    }

    fun toEntity(): Tenant {
        return Tenant(
            name = name,
            description = description
        )
    }
}

data class TenantResponse(
    val id: Long,
    val name: String,
    val description: String? = null,
    val tenantId: String
)