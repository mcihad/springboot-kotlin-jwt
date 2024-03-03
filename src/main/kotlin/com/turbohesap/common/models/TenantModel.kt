package com.turbohesap.common.models

import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.TenantId
import java.io.Serializable

@MappedSuperclass
abstract class TenantModel<T : Serializable>(
    @TenantId
    var tenantId: String? = null
) : AbstractJpaPersistable<T>()