package com.turbohesap.account.services

import com.turbohesap.account.User
import com.turbohesap.account.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): User? {
        return userRepository.findByUsername(username!!) ?: throw UsernameNotFoundException("Kullanıcı bulunamadı.$username")
    }
}