package schmisn.controller

import com.schmisn.services.ChatService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.get
import org.slf4j.Logger
import schmisn.model.MessageDTO

class ChatController(
    val application: Application,
    val logger: Logger,
    val chatService: ChatService
) {
    init {
        application.routing { registerRoutes() }
    }

    private fun Routing.registerRoutes() {
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