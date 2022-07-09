package schmisn

import io.ktor.server.application.*
import schmisn.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureMonitoring()
    configureRouting()
    configureSockets()
    configureSerialization()
    configureDI()
}

@Suppress("unused")
fun Application.moduleTest() {
    configureMonitoring()
    configureRoutingTest()
    configureSockets()
    configureSerialization()
    configureDI()
}
