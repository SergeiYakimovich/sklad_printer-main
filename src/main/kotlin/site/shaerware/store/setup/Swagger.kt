package site.shaerware.store.setup

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureSwagger() {
    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
//        allowHost("localhost:8080")
//        allowHost("127.0.0.1:8080")
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
    }

    routing {
        swaggerUI("swagger-ui", "openapi/documentation.yaml") {
//            version = System.getProperty("project_version")
        }
    }
}