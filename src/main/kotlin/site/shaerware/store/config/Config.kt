package site.shaerware.store.config

import io.ktor.server.application.*

object Config {
    private lateinit var application: Application

    fun attach(app: Application) {
        application = app
    }

    private var _jwtSecret: String? = null
    val jwtSecret: String
        get() {
            if (_jwtSecret == null)
                _jwtSecret = application.environment.config.property("jwt.secret").getString()
            return _jwtSecret!!
        }

    private var _jwtIssuer: String? = null
    val jwtIssuer: String
        get() {
            if (_jwtIssuer == null)
                _jwtIssuer = application.environment.config.property("jwt.issuer").getString()
            return _jwtIssuer!!
        }

    private var _jwtAudience: String? = null
    val jwtAudience: String
        get() {
            if (_jwtAudience == null)
                _jwtAudience = application.environment.config.property("jwt.audience").getString()
            return _jwtAudience!!
        }

    private var _jwtRealm: String? = null
    val jwtRealm: String
        get() {
            if (_jwtRealm == null)
                _jwtRealm = application.environment.config.property("jwt.realm").getString()
            return _jwtRealm!!
        }

    private var _passwordSalt: String? = null
    val passwordSalt: String
        get() {
            if (_passwordSalt == null)
                _passwordSalt = application.environment.config.propertyOrNull("password.salt")?.getString() ?: ""
            return _passwordSalt!!
        }

    private var _passwordAlgorithm: String? = null
    val passwordAlgorithm: String
        get() {
            if (_passwordAlgorithm == null)
                _passwordAlgorithm = application.environment.config.property("password.algorithm").getString()
            return _passwordAlgorithm!!
        }
}
