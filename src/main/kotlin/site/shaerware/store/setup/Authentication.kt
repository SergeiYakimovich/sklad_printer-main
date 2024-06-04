package site.shaerware.store.setup

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import site.shaerware.store.config.Config

fun Application.configureAuthentication() {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = Config.jwtRealm
            verifier(
                JWT.require(Algorithm.HMAC256(Config.jwtSecret))
                    .withAudience(Config.jwtAudience)
                    .withIssuer(Config.jwtIssuer)
                    .build()
            )
            validate {
                val username = it.payload.getClaim("username").asString()
                if (username != "")
                    JWTPrincipal(it.payload)
                else
                    null
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}