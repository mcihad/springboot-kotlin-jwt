package com.turbohesap.exceptions

import com.turbohesap.dtos.ErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(RecordAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleRecordAlreadyExistsException(ex: RecordAlreadyExistsException): ErrorResponseDto {
        return ErrorResponseDto(
            message = ex.message!!,
            data = null
        )
    }

    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(ex: ValidationException): ErrorResponseDto {
        return ErrorResponseDto(
            message = ex.message!!,
            data = ex.data
        )
    }

    @ExceptionHandler(RecordNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleRecordNotFoundException(ex: RecordNotFoundException): ErrorResponseDto {
        return ErrorResponseDto(
            message = ex.message!!,
            data = null
        )
    }
}