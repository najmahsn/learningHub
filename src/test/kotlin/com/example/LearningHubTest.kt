package com.example

import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import jakarta.inject.Inject
import io.micronaut.http.client.HttpClient
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows

@MicronautTest
class LearningHubTest {

    @Inject
    lateinit var application: EmbeddedApplication<*>

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun testItWorks() {
        Assertions.assertTrue(application.isRunning)
    }

    @Test
    fun testPOSTRequestCreatesTopic() {

        val request = HttpRequest.POST("/topics", CreateTopicRequest("Test Topic"))
        val response = client.toBlocking().exchange(request, Topic::class.java)

        assertEquals(response.body().name, "Test Topic")
        assertNotNull(response.body().id)
        assertEquals(HttpStatus.OK, response.status)
    }

    @Test
    fun testGETRequestReturnsTopics() {
        val request = HttpRequest.GET<List<Topic>>("/topics")

        val response = client.toBlocking().exchange(request, Array<Topic>::class.java)

        assertTrue(response.body().isNotEmpty())
        assertEquals(HttpStatus.OK, response.status)

    }

    @Test
    fun testGETRequestReturnsTopicByIdWith200Response() {
        val postRequest = HttpRequest.POST("/topics", CreateTopicRequest("Test Topic"))
        val postResponse = client.toBlocking().retrieve(postRequest, Topic::class.java)

        val getRequest = HttpRequest.GET<Topic>("/topics/${postResponse?.id}")
        val getResponse = client.toBlocking().exchange(getRequest, Topic::class.java)

        assertEquals(postResponse.id, getResponse.body().id)
        assertEquals(HttpStatus.OK, getResponse.status)
    }

    @Test
    fun testGETRequestReturns404ForUnknownTopicId() {
        val getRequest = HttpRequest.GET<Topic>("/topics/9999")
//        val getResponse = client.toBlocking().exchange(getRequest, Topic::class.java)

//        assertEquals(HttpStatus.NOT_FOUND, getResponse.status)
        assertThrows<HttpClientResponseException> {client.toBlocking().exchange(getRequest, Topic::class.java) }
    }
}

