package com.schmisn.services

import com.schmisn.models.Message
import kotlinx.coroutines.flow.MutableSharedFlow
import org.slf4j.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatService @Inject constructor(
    private val logger: Logger
) {
    val messages = MutableSharedFlow<Message>()

    suspend fun sendMessage(text: String, username: String) {
        logger.info("Sending message '$text' from '$username'")
        messages.emit(Message(text, username))
    }
}
