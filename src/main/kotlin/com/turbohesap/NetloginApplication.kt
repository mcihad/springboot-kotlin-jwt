package com.turbohesap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources

@SpringBootApplication
@PropertySources(
    PropertySource("classpath:application-\${spring.profiles.active:prod}.properties")
)
class NetloginApplication

fun main(args: Array<String>) {
    runApplication<NetloginApplication>(*args)
}
