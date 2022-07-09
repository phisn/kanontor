package schmisn.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import schmisn.models.*

fun Application.configureRoutingTest() {
    install(StatusPages) {
        exception<Exception> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, ErrorDTO(cause.localizedMessage))
        }
    }

    configureRouting()
}

fun Application.configureRouting() {
    /*
    install(Locations) {
    }
    */
}
