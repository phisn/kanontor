package schmisn.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.locations.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import schmisn.models.ErrorDTO

fun Application.configureRoutingTest() {
    install(StatusPages) {
        exception<Exception> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, ErrorDTO(cause.localizedMessage))
        }
    }

    configureRouting()
}

fun Application.configureRouting() {
    install(Locations) {
    }
}
