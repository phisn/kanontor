package schmisn

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import schmisn.models.*
import kotlin.test.*

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