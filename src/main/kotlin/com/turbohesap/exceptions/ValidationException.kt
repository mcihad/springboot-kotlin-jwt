package com.turbohesap.exceptions

class ValidationException(
    message: String,
    data : Any? = null
): RuntimeException(message) {
    val data = data
}