package com.example

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Id
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@MappedEntity
data class Topic(
    @field: Id
    @field: GeneratedValue
    val id: Long? = null,
    val name: String
)
