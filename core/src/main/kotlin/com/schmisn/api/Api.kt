package com.schmisn.api

import io.ktor.server.routing.*

interface Api {
    fun Routing.registerRoutes()
}
