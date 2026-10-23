package com.example

import io.micronaut.http.annotation.Body
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import jakarta.validation.Valid

@Controller("/topics")
open class TopicController(
    private val topicRepository: TopicRepository
) {

    @Get
    fun getTopics(): List<Topic> {
        return topicRepository.findAll()
    }

    @Get("/{id}")
    fun getTopicById(id: Long): HttpResponse<Topic> {
        return topicRepository.findById(id)
            .map { HttpResponse.ok(it) }
            .orElse(HttpResponse.notFound())
    }


    @Post
    open fun createTopic(@Valid @Body request: CreateTopicRequest): Topic {
        val topic = Topic (name = request.name)

        return topicRepository.save(topic)

    }

}