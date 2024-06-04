package site.shaerware.store.setup

import io.ktor.serialization.jackson.*
import com.fasterxml.jackson.databind.*
import io.ktor.http.*
//import com.papsign.ktor.openapigen.route.apiRouting
import io.ktor.server.response.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
                registerModule(JavaTimeModule())
            }
    }
    routing {
        get("/json/jackson") {
                call.respond(mapOf("hello" to "world"))
            }
    }
}
