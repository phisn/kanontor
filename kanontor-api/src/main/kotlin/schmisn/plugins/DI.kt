package schmisn.plugins

import io.ktor.server.application.*
import org.koin.ktor.ext.getKoin
import org.koin.ktor.plugin.Koin
import org.koin.ktor.plugin.koin
import org.koin.logger.slf4jLogger
import schmisn.DI.mainModule

fun Application.configureDI() {
    log.info("register di")

    install(Koin) {
        slf4jLogger()

        koin.declare(this@configureDI)
        koin.declare(this@configureDI.log)

        modules(mainModule)
    }
}