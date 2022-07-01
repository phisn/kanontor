package com.schmisn

import com.schmisn.api.Api
import com.schmisn.api.ChatApi
import com.schmisn.models.Message
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import dagger.multibindings.IntoSet
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.logging.*
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
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.engine.*
import io.ktor.server.testing.*
import kotlinx.coroutines.flow.first
import javax.inject.Singleton
import kotlin.math.log2
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        var component: TestApplicationComponent

        application {
            installCommon()

            component = DaggerTestApplicationComponent
                .builder()
                .application(this)
                .build()

            component.apis.register()
        }

        val element = component.chatService.messages.first()

        val client = createClient {
            install(ContentNegotiation) {
                gson()
            }
        }

        val response = client.post("/chat/message") {
            contentType(ContentType.Application.Json)
            setBody(ChatApi.SendMessageBody("Hello world!"))
        }

        assertEquals(HttpStatusCode.NoContent, response.status)
    }
}
