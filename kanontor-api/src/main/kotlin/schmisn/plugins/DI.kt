package schmisn.plugins

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import schmisn.DI.mainModule

fun Application.configureDI() {
    install(Koin) {
        slf4jLogger()
        modules(mainModule)
    }
}