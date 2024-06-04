package site.shaerware.printing

import io.ktor.server.application.*
import java.net.URI
import java.util.UUID

object Config {
    private lateinit var application: Application

    fun attach(app : Application) {
        application = app
    }

    private var _hostName: String? = null
    val hostName : String
        get() {
            if (_hostName == null)
                _hostName = application.environment.config.property("host.name").getString()
            return _hostName!!
        }

    private var _hostId: UUID? = null
    val hostId: UUID
        get() {
            if (_hostId == null)
                _hostId = UUID.fromString(application.environment.config.property("host.id").getString())
            return _hostId!!
        }

    private var _storeAPI: URI? = null
    val storeAPI: URI
        get() {
            if (_storeAPI == null)
                _storeAPI = URI.create(application.environment.config.property("store.api").getString())
            return _storeAPI!!
        }

    private var _storeLogin: String? = null
    val storeLogin: String
        get() {
            if (_storeLogin == null)
                _storeLogin = application.environment.config.property("store.login").getString()
            return _storeLogin!!
        }

    private var _storePassword: String? = null
    val storePassword: String
        get() {
            if (_storePassword == null)
                _storePassword = application.environment.config.property("store.password").getString()
            return _storePassword!!
        }

    private var _storeApiKey: String? = null
    val storeApiKey: String
        get() {
            if (_storeApiKey == null) {
                // login to server
            }
            return _storeApiKey!!
        }
}