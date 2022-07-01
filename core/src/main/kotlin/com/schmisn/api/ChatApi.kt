package com.schmisn.api

import com.schmisn.services.ChatService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.locations.post
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.pipeline.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.isActive
import org.slf4j.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatApi @Inject constructor(
    private val logger: Logger,
    private val chatService: ChatService
): Api {
    data class SendMessageBody(val message: String)

    override fun Routing.registerRoutes() {
        logger.info("Registering ChatApi")

        post("/chat/message") {
            chatService.sendMessage(call.receive<SendMessageBody>().message, usernameFromApplicationCall(call))
            call.respond(HttpStatusCode.NoContent)
        }

        webSocket("/chat") {
            logger.info("User '${usernameFromApplicationCall(call)}' connected")

            chatService.messages.collect {
                sendSerialized(it)
            }
        }
    }

    private fun usernameFromApplicationCall(call: ApplicationCall)
        = "${call.request.local.remoteHost}:${call.request.local.port}";
}
