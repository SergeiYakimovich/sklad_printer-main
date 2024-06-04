package site.shaerware.store

import io.ktor.server.application.*
import io.ktor.server.netty.*
import site.shaerware.store.config.Config
import site.shaerware.store.setup.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    Config.attach(this)
    configureDbSecurity()
    configureSerialization()
    configureAuthentication()
    configureRouting()
    configureSwagger()
}
