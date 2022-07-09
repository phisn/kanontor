package schmisn.DI

import com.schmisn.services.ChatService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import schmisn.controller.ChatController

val mainModule = module {
    singleOf(::ChatController)
    singleOf(::ChatService)
}
