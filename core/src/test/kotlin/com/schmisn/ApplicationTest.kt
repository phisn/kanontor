package com.schmisn

import com.schmisn.api.ChatApi
import com.schmisn.models.Message
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.locations.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.time.Duration
import io.ktor.serialization.gson.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation as ServerContentNegotiation
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            install(Locations) { }

            install(WebSockets) {
                contentConverter = GsonWebsocketContentConverter()
                pingPeriod = Duration.ofSeconds(15)
                timeout = Duration.ofSeconds(15)
                maxFrameSize = Long.MAX_VALUE
                masking = false
            }

            install(ServerContentNegotiation) {
                gson()
            }

            val component = DaggerApplicationComponent
                .builder()
                .application(this)
                .build()

            component.apis.register()
        }

        val client = createClient {
            install(ContentNegotiation) {
                gson()
            }
        }

        val response = client.post("/chat/message") {
            contentType(ContentType.Application.Json)
            setBody(ChatApi.SendMessageRequest("Hello world!"))
        }

        assertEquals(HttpStatusCode.NoContent, response.status)
    }
}

