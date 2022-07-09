package schmisn

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.server.plugins.callloging.*
import org.slf4j.event.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.locations.*
import io.ktor.websocket.*
import java.time.Duration
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.config.*
import kotlin.test.*
import io.ktor.server.testing.*
import io.ktor.server.websocket.*
import schmisn.models.Message
import schmisn.models.MessageDTO
import schmisn.plugins.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        environment {
            config = ApplicationConfig("application-test.conf")
        }

        val client = getClient()

        client.webSocket("/chat") {
            for (i in 1..16) {
                val messageToSend = "Message $i"

                val response = client.post("/chat/message") {
                    contentType(ContentType.Application.Json)
                    setBody(MessageDTO(messageToSend))
                }

                val message = receiveDeserialized<Message>()

                assertEquals(HttpStatusCode.NoContent, response.status)
                assertEquals(message.text, messageToSend)
            }

            for (i in 1..16) {
                val messageToSend = "Message $i"

                val response = client.post("/chat/message") {
                    contentType(ContentType.Application.Json)
                    setBody(MessageDTO(messageToSend))
                }

                assertEquals(HttpStatusCode.NoContent, response.status)
            }


            for (i in 1..16) {
                assertEquals(receiveDeserialized<Message>().text, "Message $i")
            }
        }
    }

    private fun ApplicationTestBuilder.getClient() = createClient {
        install(ContentNegotiation) {
            gson()
        }

        install(WebSockets) {
            contentConverter = GsonWebsocketContentConverter()
        }
    }
}