package schmisn

import com.schmisn.services.*
import org.koin.core.module.dsl.*
import org.koin.dsl.*
import schmisn.controllers.*

val mainModule = module(createdAtStart = true) {
    singleOf(::ChatController)
    singleOf(::ChatService)
}
