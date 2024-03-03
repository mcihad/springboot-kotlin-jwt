package com.turbohesap.exceptions

class RecordNotFoundException(
    recordType: String,
    recordId: Long
) : RuntimeException("$recordType tipinde kayıt bulunamadı. ID: $recordId"
) {
    val recordType = recordType
    val recordId = recordId
}