package com.turbohesap.exceptions

class RecordAlreadyExistsException(
    recordType: String,
    recordId: Long
) : RuntimeException("$recordType tipinde kayıt zaten mevcut. ID: $recordId")