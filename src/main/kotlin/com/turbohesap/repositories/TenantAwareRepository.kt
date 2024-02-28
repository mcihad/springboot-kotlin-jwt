package com.turbohesap.repositories

import com.turbohesap.models.TenantModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import java.io.Serializable
import java.util.*

@NoRepositoryBean
interface TenantAwareRepository<T : TenantModel<ID>, ID : Serializable> : JpaRepository<T, ID> {
    @Query("select e from #{#entityName} e where e.id = ?1")
    override fun findById(id: ID): Optional<T>
}