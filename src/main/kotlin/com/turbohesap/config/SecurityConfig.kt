package com.turbohesap.config

import com.turbohesap.jwt.AuthEntryPointJwt
import com.turbohesap.jwt.AuthTokenFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.client.HttpClientErrorException.Unauthorized

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Configuration
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val unauthorizedHandler: AuthEntryPointJwt,
    private val authTokenFilter: AuthTokenFilter
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    fun AuthenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Bean
    @Profile("dev")
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.invoke {
            exceptionHandling {
                authenticationEntryPoint = unauthorizedHandler
            }

            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }

            //-------------------------------------
            authorizeHttpRequests {
                authorize("/api/auth/**",permitAll)
                authorize("/api/test/**",permitAll)
                authorize(anyRequest,authenticated)
            }
            //-------------------------------------

            csrf {
                disable()
            }


            headers {
                frameOptions {
                    disable()
                }
            }
        }

        http.authenticationProvider(authenticationProvider())
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}