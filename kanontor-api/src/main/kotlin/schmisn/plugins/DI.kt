package schmisn.plugins

import io.ktor.server.application.*
import org.koin.ktor.plugin.*
import org.koin.logger.*
import schmisn.*

fun Application.configureDI() {
    log.info("register di")

    install(Koin) {
        slf4jLogger()

        koin.declare(this@configureDI)
        koin.declare(this@configureDI.log)

        modules(mainModule)
    }
}