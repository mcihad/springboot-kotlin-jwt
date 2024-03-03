package com.turbohesap.account.controllers

import com.turbohesap.account.models.CreateTenantRequest
import com.turbohesap.account.models.TenantResponse
import com.turbohesap.account.services.TenantService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/account/tenants")
class TenantController(
    private val tenantService: TenantService
) {

    @GetMapping
    fun findAll(): List<TenantResponse> = tenantService.findAll()

    @GetMapping("{id}")
    fun findById(@PathVariable id: Long): TenantResponse = tenantService.findById(id)

    @PostMapping
    fun create(@RequestBody request: CreateTenantRequest): TenantResponse =
        tenantService.create(request)

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody request: CreateTenantRequest): TenantResponse =
        tenantService.update(id, request)


    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = tenantService.delete(id)
}