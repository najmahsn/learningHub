package com.example

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

@Serdeable
data class CreateTopicRequest(
    @field:NotBlank
    val name: String
)
