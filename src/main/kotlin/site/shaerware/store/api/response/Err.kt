package site.shaerware.store.api.response

import io.ktor.http.*

data class Err(val error: Data) {

    data class Data(val code: HttpStatusCode, val message: String?, val data: Any?)

    constructor(code: HttpStatusCode, message: String? = null, data: Any? = null): this(Data(code, message, data))

}
