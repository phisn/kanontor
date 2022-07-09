package schmisn.DI

import com.schmisn.services.ChatService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import schmisn.controllers.ChatController

val mainModule = module(createdAtStart = true) {
    singleOf(::ChatController)
    singleOf(::ChatService)
}
