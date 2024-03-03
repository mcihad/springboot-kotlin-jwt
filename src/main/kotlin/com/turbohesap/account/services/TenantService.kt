package com.turbohesap.account.services

import com.turbohesap.account.TenantRepository
import com.turbohesap.account.models.CreateTenantRequest
import com.turbohesap.account.models.TenantResponse
import com.turbohesap.exceptions.RecordAlreadyExistsException
import com.turbohesap.exceptions.RecordNotFoundException
import com.turbohesap.exceptions.ValidationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface TenantService {
    fun create(request: CreateTenantRequest): TenantResponse
    fun update(id: Long, request: CreateTenantRequest): TenantResponse
    fun delete(id: Long)
    fun findById(id: Long): TenantResponse
    fun findAll(): List<TenantResponse>
    fun existsById(id: Long): Boolean
    fun existsByName(name: String): Boolean
}

@Service
@Transactional
class TenantServiceImpl(
    private val tenantRepository: TenantRepository
) : TenantService {
    override fun create(request: CreateTenantRequest): TenantResponse {
        val isExists = existsByName(request.name)
        if (isExists) {
            throw RecordAlreadyExistsException("Firma/Kurum",0)
        }

        val (isValid, message) = request.isValid()
        if (!isValid) {
            throw ValidationException(message, request)
        }

        val tenant = tenantRepository.save(request.toEntity())
        return TenantResponse(
            id = tenant.getId()!!,
            name = tenant.name,
            description = tenant.description,
            tenantId = tenant.getTenantId()
        )
    }

    override fun update(id: Long, request: CreateTenantRequest): TenantResponse {
        val foundTenant = tenantRepository.findById(id).orElseThrow { throw RecordNotFoundException("Firma/Kurum", id) }
        val isExists = existsByName(request.name)
        if (isExists && foundTenant.name != request.name) {
            throw RecordAlreadyExistsException("Firma/Kurum(${request.name})",0)
        }

        val (isValid, message) = request.isValid()

        if (!isValid) {
            throw ValidationException(message, request)
        }

        foundTenant.name = request.name
        foundTenant.description = request.description
        val tenant = tenantRepository.save(foundTenant)
        return TenantResponse(
            id = tenant.getId()!!,
            name = tenant.name,
            description = tenant.description,
            tenantId = tenant.getTenantId()
        )
    }

    override fun delete(id: Long) {
        if (!existsById(id)) {
            throw RecordNotFoundException("Firma/Kurum", id)
        }
        tenantRepository.deleteById(id)
    }

    override fun findById(id: Long): TenantResponse {
        val tenant = tenantRepository.findById(id).orElseThrow { throw RecordNotFoundException("Firma/Kurum", id) }
        return TenantResponse(
            id = tenant.getId()!!,
            name = tenant.name,
            description = tenant.description,
            tenantId = tenant.getTenantId()
        )
    }

    override fun findAll(): List<TenantResponse> {
        return tenantRepository.findAll().map {
            TenantResponse(
                id = it.getId()!!,
                name = it.name,
                description = it.description,
                tenantId = it.getTenantId()
            )
        }
    }

    override fun existsById(id: Long): Boolean {
        return tenantRepository.existsById(id)
    }

    override fun existsByName(name: String): Boolean {
        return tenantRepository.existsByName(name)
    }
}