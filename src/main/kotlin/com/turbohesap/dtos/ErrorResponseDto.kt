package com.turbohesap.dtos

import java.util.Date

data class ErrorResponseDto(
    val message: String,
    val data: Any? = null,
    val timestamp : Date = Date()
)