package site.shaerware.printing

import io.ktor.server.application.*
import io.ktor.server.netty.*
import site.shaerware.printing.jobs.Fetching
import site.shaerware.printing.plugins.*
import site.shaerware.printing.plugins.configureRouting

fun main(args: Array<String>): Unit =
    EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    Config.attach(this)
    configureRouting()

    val fetching = Fetching()
    fetching.setup()
    fetching.start()
}
