package schmisn.controllers

import com.schmisn.services.ChatService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.slf4j.Logger
import schmisn.models.MessageDTO

class ChatController(
    application: Application,
    private val logger: Logger,
    private val chatService: ChatService
) {
    init {
        application.routing { registerRoutes() }
    }

    private fun Routing.registerRoutes() {
        logger.info("Register chat controller")

        route("chat") {
            post("message") {
                val message = call.receive<MessageDTO>()
                chatService.sendMessage(message.text, usernameFromApplicationCall(call))
                call.respond(HttpStatusCode.NoContent)
            }

            webSocket {
                logger.info("User '${usernameFromApplicationCall(call)}' connected")

                chatService.messages.collect {
                    sendSerialized(it)
                }
            }
        }
    }

    private fun usernameFromApplicationCall(call: ApplicationCall)
            = "${call.request.local.remoteHost}:${call.request.local.port}";
}