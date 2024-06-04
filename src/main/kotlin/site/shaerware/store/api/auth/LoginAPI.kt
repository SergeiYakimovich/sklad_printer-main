package site.shaerware.store.api.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ebean.DB
import io.ktor.resources.*
import io.ktor.server.application.*
import site.shaerware.store.api.contracts.ApiItem
import site.shaerware.store.api.auth.dto.LoginRq
import site.shaerware.store.api.auth.dto.LoginRs
import site.shaerware.store.config.Config
import site.shaerware.store.db.models.auth.User
import site.shaerware.store.utils.Password

@Resource("/login")
class LoginAPI : ApiItem {
    fun post(rq: LoginRq): LoginRs {
        val user = DB.find(User::class.java).where().ieq("name", rq.username).findOne()
            ?: throw IllegalArgumentException("Invalid username or password")
        val hash = Password.hash(rq.password)
        if (user.passwordHash != hash)
            throw IllegalArgumentException("Invalid username or password")
        val token = JWT.create()
            .withAudience(Config.jwtAudience)
            .withIssuer(Config.jwtIssuer)
            .withClaim("username", user.name)
            .sign(Algorithm.HMAC256(Config.jwtSecret))
        return LoginRs(token)
    }
}
