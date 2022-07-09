package com.schmisn.services

import kotlinx.coroutines.flow.MutableSharedFlow
import org.slf4j.Logger
import schmisn.models.Message

class ChatService constructor(
    private val logger: Logger
) {
    val messages = MutableSharedFlow<Message>()

    suspend fun sendMessage(text: String, username: String) {
        logger.info("Sending message '$text' from '$username'")
        messages.emit(Message(text, username))
    }
}
