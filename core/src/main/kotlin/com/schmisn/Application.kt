package com.schmisn

import com.schmisn.api.Api
import com.schmisn.api.ChatApi
import com.schmisn.services.ChatService
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
import org.slf4j.Logger
import java.time.Duration
import javax.inject.Inject
import javax.inject.Singleton

fun main() {
    embeddedServer(Netty, port = 8080) {
        installCommon()

        val component = DaggerApplicationComponent
            .builder()
            .application(this)
            .build()

        component.apis.register()
    }.start(wait = true)
}

fun Application.installCommon() {
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
}

@Singleton
class Apis @Inject constructor(
    private val logger: Logger,
    private val apis: Set<@JvmSuppressWildcards Api>,
    private val application: Application
) {
    fun register() {
        logger.info("Registering apis")

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

@Singleton
@Component(modules = [
    ApplicationModule::class,
    ApisModule::class
])
interface TestApplicationComponent : ApplicationComponent {
    val chatService: ChatService

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestApplicationComponent
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
