package com.turbohesap

import com.turbohesap.common.multitenant.TenantIdentifierResolver
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class NetloginApplicationTests {

    @Test
    fun contextLoads() {
        // Test context loading
        val tenantIdentifierResolver = TenantIdentifierResolver()
        tenantIdentifierResolver.resolveCurrentTenantIdentifier()


    }

    @Test
    fun test() {
        // Test context loading
        val tenantIdentifierResolver = TenantIdentifierResolver()
        tenantIdentifierResolver.resolveCurrentTenantIdentifier()
    }

}
