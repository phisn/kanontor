package com.schmisn

import com.schmisn.api.Api
import com.schmisn.api.ChatApi
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import dagger.multibindings.IntoSet
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.locations.post
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import java.time.Duration
import javax.inject.Inject
import javax.inject.Singleton

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(Locations) { }

        install(WebSockets) {
            contentConverter = GsonWebsocketContentConverter()
            pingPeriod = Duration.ofSeconds(15)
            timeout = Duration.ofSeconds(15)
            maxFrameSize = Long.MAX_VALUE
            masking = false
        }

        install(ContentNegotiation) {
            gson()
        }

        val component = DaggerApplicationComponent
            .builder()
            .application(this)
            .build()

        component.apis.register()
    }.start(wait = true)
}

@Singleton
class Apis @Inject constructor(
    private val apis: Set<@JvmSuppressWildcards Api>,
    private val application: Application
) {
    fun register() {
        application.routing {
            apis.forEach {
                it.apply { registerRoutes() }
            }
        }
    }
}

@Singleton
@Component(modules = [
    ApplicationModule::class,
    ApisModule::class
])
interface ApplicationComponent {
    val apis: Apis

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }
}

@dagger.Module
class ApplicationModule {
    @Provides
    fun provideLogger(application: Application) = application.log
}

@dagger.Module
interface ApisModule {
    @Binds
    @IntoSet
    fun bindApi(impl: ChatApi): Api
}
