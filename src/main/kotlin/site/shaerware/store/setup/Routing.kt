package site.shaerware.store.setup

import io.ebean.DB
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.response.*
import io.ktor.server.routing.*
import site.shaerware.store.api.auth.LoginAPI
import site.shaerware.store.api.auth.RoleAPI
import site.shaerware.store.api.auth.UserAPI
import site.shaerware.store.api.auth.dto.RoleRq
import site.shaerware.store.api.auth.dto.RoleRs
import site.shaerware.store.api.auth.dto.UserRq
import site.shaerware.store.api.auth.dto.UserRs
import site.shaerware.store.api.contracts.*
import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.*
import site.shaerware.store.api.documents.IncomingAPI
import site.shaerware.store.api.documents.LayoutingAPI
import site.shaerware.store.api.documents.OrderingAPI
import site.shaerware.store.api.documents.dto.*
import site.shaerware.store.api.print.PrintAPI
import site.shaerware.store.api.print.PrintAPI.Id.PrinterAPI
import site.shaerware.store.api.print.PrintAPI.PrintJobsAPI
import site.shaerware.store.api.print.dto.*
import site.shaerware.store.api.references.CounterpartyAPI
import site.shaerware.store.api.references.CounterpartyAPI.Id.BankAccountAPI
import site.shaerware.store.api.references.CounterpartyAPI.Id.ContractAPI
import site.shaerware.store.api.references.ProductAPI
import site.shaerware.store.api.references.ProductAPI.Id.LotAPI
import site.shaerware.store.api.references.StoreAPI
import site.shaerware.store.api.references.StoreAPI.Id.CellAPI
import site.shaerware.store.api.references.VendorAPI
import site.shaerware.store.api.references.dto.*
import site.shaerware.store.api.reports.EnumsAPI
import site.shaerware.store.api.reports.VersionAPI
import site.shaerware.store.api.response.Err
import site.shaerware.store.api.response.Ok
import site.shaerware.store.db.models.auth.Role
import site.shaerware.store.db.models.auth.User
import site.shaerware.store.db.models.auth.enums.DocumentEnum
import site.shaerware.store.db.models.auth.enums.ReferenceEnum
import site.shaerware.store.db.models.docs.Incoming
import site.shaerware.store.db.models.docs.Layouting
import site.shaerware.store.db.models.docs.Ordering
import site.shaerware.store.db.models.print.Host
import site.shaerware.store.db.models.print.PrintJob
import site.shaerware.store.db.models.print.Printer
import site.shaerware.store.db.models.refs.*
import site.shaerware.store.utils.MIME
import java.util.*
import kotlin.io.path.deleteIfExists
import kotlin.io.path.outputStream

fun ApplicationCall.current_user(): User? {
    val principal = this.principal<JWTPrincipal>()
    val username = principal?.payload?.getClaim("username")?.asString()
    if (username.isNullOrEmpty()) return null
    return DB.find(User::class.java).where().ieq("name", username).findOne()
}

inline fun <reified T> ApplicationCall.allowReferenceList(): Boolean {
    val entity = ReferenceEnum.values().find { it.cls == T::class } ?: return false
    val user = current_user() ?: return false
    var result = false
    for (role in user.roles) {
        for (px in role.references.filter { it.entity == entity }) {
            if (px.allowList == false) return false
            if (px.allowList == true) result = true
        }
    }
    return result
}

inline fun <reified T> ApplicationCall.allowReferenceCreate(): Boolean {
    val entity = ReferenceEnum.values().find { it.cls == T::class } ?: return false
    val user = current_user() ?: return false
    var result = false
    for (role in user.roles) {
        for (px in role.references.filter { it.entity == entity }) {
            if (px.allowCreate == false) return false
            if (px.allowCreate == true) result = true
        }
    }
    return result
}

inline fun <reified T> ApplicationCall.allowReferenceRead(): Boolean {
    val entity = ReferenceEnum.values().find { it.cls == T::class } ?: return false
    val user = current_user() ?: return false
    var result = false
    for (role in user.roles) {
        for (px in role.references.filter { it.entity == entity }) {
            if (px.allowRead == false) return false
            if (px.allowRead == true) result = true
        }
    }
    return result
}

inline fun <reified T> ApplicationCall.allowReferenceUpdate(): Boolean {
    val entity = ReferenceEnum.values().find { it.cls == T::class } ?: return false
    val user = current_user() ?: return false
    var result = false
    for (role in user.roles) {
        for (px in role.references.filter { it.entity == entity }) {
            if (px.allowUpdate == false) return false
            if (px.allowUpdate == true) result = true
        }
    }
    return result
}

inline fun <reified T> ApplicationCall.allowReferenceDelete(): Boolean {
    val entity = ReferenceEnum.values().find { it.cls == T::class } ?: return false
    val user = current_user() ?: return false
    var result = false
    for (role in user.roles) {
        for (px in role.references.filter { it.entity == entity }) {
            if (px.allowDelete == false) return false
            if (px.allowDelete == true) result = true
        }
    }
    return result
}

inline fun <reified T> ApplicationCall.allowDocumentList(): Boolean {
    val entity = DocumentEnum.values().find { it.cls == T::class } ?: return false
    val user = current_user() ?: return false
    var result = false
    for (role in user.roles) {
        for (px in role.documents.filter { it.entity == entity }) {
            if (px.allowList == false) return false
            if (px.allowList == true) result = true
        }
    }
    return result
}

inline fun <reified T> ApplicationCall.allowDocumentCreate(): Boolean {
    val entity = DocumentEnum.values().find { it.cls == T::class } ?: return false
    val user = current_user() ?: return false
    var result = false
    for (role in user.roles) {
        for (px in role.documents.filter { it.entity == entity }) {
            if (px.allowCreate == false) return false
            if (px.allowCreate == true) result = true
        }
    }
    return result
}

inline fun <reified T> ApplicationCall.allowDocumentRead(): Boolean {
    val entity = DocumentEnum.values().find { it.cls == T::class } ?: return false
    val user = current_user() ?: return false
    var result = false
    for (role in user.roles) {
        for (px in role.documents.filter { it.entity == entity }) {
            if (px.allowRead == false) return false
            if (px.allowRead == true) result = true
        }
    }
    return result
}

inline fun <reified T> ApplicationCall.allowDocumentUpdate(): Boolean {
    val entity = DocumentEnum.values().find { it.cls == T::class } ?: return false
    val user = current_user() ?: return false
    var result = false
    for (role in user.roles) {
        for (px in role.documents.filter { it.entity == entity }) {
            if (px.allowUpdate == false) return false
            if (px.allowUpdate == true) result = true
        }
    }
    return result
}

inline fun <reified T> ApplicationCall.allowDocumentDelete(): Boolean {
    val entity = DocumentEnum.values().find { it.cls == T::class } ?: return false
    val user = current_user() ?: return false
    var result = false
    for (role in user.roles) {
        for (px in role.documents.filter { it.entity == entity }) {
            if (px.allowDelete == false) return false
            if (px.allowDelete == true) result = true
        }
    }
    return result
}

inline fun <reified T> ApplicationCall.allowDocumentActivate(): Boolean {
    val entity = DocumentEnum.values().find { it.cls == T::class } ?: return false
    val user = current_user() ?: return false
    var result = false
    for (role in user.roles) {
        for (px in role.documents.filter { it.entity == entity }) {
            if (px.allowActivate == false) return false
            if (px.allowActivate == true) result = true
        }
    }
    return result
}

enum class CheckAction {
    Create, Read, Update, Activate, Delete, List;
}

suspend inline fun <reified API, reified R> ApplicationCall.do_respond(api: API, action : CheckAction, status: HttpStatusCode = HttpStatusCode.OK, what: (api: API) -> R)
where API : ApiItem,
      R : Any
{
    try {
        var allow = false
        if (api is EntityAPI<*, *, *, *> || api is SubEntityAPI<*, *, *, *, *, *, *, *>) {
            allow = when (action) {
                CheckAction.Create -> allowReferenceCreate<API>()
                CheckAction.Read -> allowReferenceRead<API>()
                CheckAction.Update -> allowReferenceUpdate<API>()
                CheckAction.Activate -> TODO("Недопустимо, нужно спец. исключение.")
                CheckAction.Delete -> allowReferenceDelete<API>()
                CheckAction.List -> allowReferenceList<API>()
            }
        }
        if (api is DocumentAPI<*, *, *, *>) {
            allow = when (action) {
                CheckAction.Create -> allowDocumentCreate<API>()
                CheckAction.Read -> allowDocumentRead<API>()
                CheckAction.Update -> allowDocumentUpdate<API>()
                CheckAction.Activate -> allowDocumentActivate<API>()
                CheckAction.Delete -> allowDocumentDelete<API>()
                CheckAction.List -> allowDocumentList<API>()
            }
        }
        if (!allow)
            respond(HttpStatusCode.Forbidden, Err(HttpStatusCode.Forbidden))
        else
            respond(status, Ok(what(api)))
    }
    catch (e: NullPointerException) {
        respond(HttpStatusCode.NotFound, Err(HttpStatusCode.NotFound, "Object not found"))
    }
    catch (e: IllegalArgumentException) {
        respond(HttpStatusCode.BadRequest, Err(HttpStatusCode.BadRequest, e.message))
    }
    catch (e: Exception) {
        // TODO: Убрать data в релизной версии
        respond(HttpStatusCode.InternalServerError, Err(HttpStatusCode.InternalServerError, message ="[${e::class.simpleName}] ${e.message}", data = e))
    }
}

inline fun <reified API, reified API_ID, DBO, reified R_DTO, reified L_DTO, reified C_DTO, reified U_DTO> Route.routeReference()
where API : EntityAPI<DBO, R_DTO, L_DTO, C_DTO>,
      API_ID : EntityByIdAPI<String, API, DBO, R_DTO, L_DTO, C_DTO, U_DTO>,
      R_DTO : RS<API>,
      R_DTO : ReadDTO<DBO>,
      L_DTO : RS<API>,
      L_DTO : ListDTO<DBO>,
      C_DTO : RQ<API>,
      C_DTO : CreateDTO<DBO>,
      U_DTO : RQ<API>,
      U_DTO : UpdateDTO<DBO>
{
    get<API> { api ->
        call.do_respond(api, CheckAction.List) { api.list() }
    }

    post<API> {api ->
        call.do_respond(api, CheckAction.Create, HttpStatusCode.Created) { api.create(call.receive()) }
    }

    get<API_ID> {apiId ->
        call.do_respond(apiId.parent, CheckAction.Read) { apiId.read() }
    }

    put<API_ID> {apiId ->
        call.do_respond(apiId.parent, CheckAction.Update) { apiId.update(call.receive<U_DTO>()) }
    }

    delete<API_ID> {apiId ->
        call.do_respond(apiId.parent, CheckAction.Delete) { apiId.delete() }
    }
}

inline fun <P_API, P_ID_API, P_DBO, reified API, reified API_ID, DBO, reified R_DTO, L_DTO, reified C_DTO, reified U_DTO> Route.routeSubReference()
        where API : SubEntityAPI<String, P_API, P_ID_API, P_DBO, DBO, R_DTO, L_DTO, C_DTO>,
              API_ID : SubEntityByIdAPI<String, P_API, P_ID_API, P_DBO, String, API, DBO, R_DTO, L_DTO, C_DTO, U_DTO>,
              R_DTO : RS<API>,
              R_DTO : ReadDTO<DBO>,
              C_DTO : RQ<API>,
              C_DTO : CreateSubDTO<DBO, P_DBO>
{
    get<API> {api ->
        call.do_respond(api, CheckAction.List) { api.list() }
    }

    post<API> {api ->
        call.do_respond(api, CheckAction.Create, HttpStatusCode.Created) { api.create(call.receive()) }
    }

    get<API_ID> {apiId ->
        call.do_respond(apiId.parent, CheckAction.Read) { apiId.read() }
    }

    put<API_ID> {apiId ->
        call.do_respond(apiId.parent, CheckAction.Update) { apiId.update(call.receive()) }
    }

    delete<API_ID> {apiId ->
        call.do_respond(apiId.parent, CheckAction.Delete) { apiId.delete() }
    }
}

inline fun <reified API, reified API_ID, DBO, reified R_DTO, reified L_DTO, reified C_DTO, reified U_DTO> Route.routeDocument()
where API : DocumentAPI<DBO, R_DTO, L_DTO, C_DTO>,
      API_ID : DocumentByIdAPI<String, API, DBO, R_DTO, L_DTO, C_DTO, U_DTO>,
      R_DTO : RS<API>,
      R_DTO : ReadDTO<DBO>,
      C_DTO : RQ<API>,
      C_DTO : CreateDTO<DBO>
{
    get<API> {api ->
        call.do_respond(api, CheckAction.List) { api.list() }
    }

    post<API> {api ->
        call.do_respond(api, CheckAction.Create, HttpStatusCode.Created) { api.create(call.receive()) }
    }

    get<API_ID> {apiId ->
        call.do_respond(apiId.parent, CheckAction.Read) { apiId.read() }
    }

    put<API_ID> {apiId ->
        call.do_respond(apiId.parent, CheckAction.Update) { apiId.update(call.receive()) }
    }

    delete<API_ID> {apiId ->
        call.do_respond(apiId.parent, CheckAction.Delete) { apiId.delete() }
    }
}

inline fun <reified API> Route.routeReport() where API : ReportAPI {
    get<API> {
        call.respond(status = HttpStatusCode.OK, Ok(it.get(call)))
    }
}

fun Route.routeProductImages() {

    get("/product/{productId}/image") {
        call.do_respond(ProductAPI(), CheckAction.Read) {
            val productIdParam = call.parameters["productId"]!!
            val productId = UUID.fromString(productIdParam)
            val product = DB.find(Product::class.java, productId)!!
            product.images.map { ProductImageTO(it) }
        }
    }

    post("/product/{productId}/image") {
        call.do_respond(ProductAPI(), CheckAction.Update, HttpStatusCode.Created) {
            val productIdParam = call.parameters["productId"]!!
            val productId = UUID.fromString(productIdParam)
            val product = DB.find(Product::class.java, productId)!!

            var dbo : ProductImage? = null

            val multipart = call.receiveMultipart()
            multipart.forEachPart { part ->
                run {
                    if (part is PartData.FileItem) {
                        var mime = part.contentType
                        val name = part.originalFileName
                        var type = name?.substringAfterLast("/")?.substringAfterLast('.', "")
                        if (type == null) {
                            if (mime != null) type = MIME.type_by_mime(mime)
                            else throw IllegalArgumentException("Type and Mime-Type both null!")
                        } else {
                            if (mime == null) mime = MIME.mime_by_type(type)
                        }
                        val file = kotlin.io.path.createTempFile()
                        part.streamProvider().use { its ->
                            file.outputStream().buffered().use {
                                its.copyTo(it)
                            }
                        }
                        dbo = ProductImage(product, file.toFile(), type, mime.toString())
                        dbo?.save()
                        file.deleteIfExists()
                    }
                }
            }
            if (dbo == null)
                throw InvalidBodyException("Image file not sended!")
            ProductImageTO(dbo!!)
        }
    }

    get("/product/{productId}/image/{id}") {
        call.do_respond(ProductAPI(), CheckAction.Read) {
            val productIdParam = call.parameters["productId"]!!
            val productId = UUID.fromString(productIdParam)
            val imageIdParam = call.parameters["id"]!!
            val imageId = UUID.fromString(imageIdParam)
            val image = DB.find(ProductImage::class.java).where().eq("id", imageId)
                .and().eq("owner_id", productId).findOne()!!
            ProductImageTO(image)
        }
    }

    delete("/product/{productId}/image/{id}") {
        call.do_respond(ProductAPI(), CheckAction.Update) {
            val productIdParam = call.parameters["productId"]!!
            val productId = UUID.fromString(productIdParam)
            val imageIdParam = call.parameters["id"]!!
            val imageId = UUID.fromString(imageIdParam)
            val image = DB.find(ProductImage::class.java).where().eq("id", imageId)
                .and().eq("owner_id", productId).findOne()!!
            image.delete()
            ProductImageTO(image)
        }
    }

    get("/product/{productId}/image/{id}/file") {
        // TODO: Загнать в шаблоны запись стрима
        if (!call.allowReferenceRead<ProductAPI>()) {
            call.respond(HttpStatusCode.Forbidden, Err(HttpStatusCode.Forbidden))
        } else {
            val productIdParam = call.parameters["productId"]!!
            val productId = UUID.fromString(productIdParam)
            val imageIdParam = call.parameters["id"]!!
            val imageId = UUID.fromString(imageIdParam)
            val image = DB.find(ProductImage::class.java).where().eq("id", imageId)
                .and().eq("owner_id", productId).findOne()!!
            call.respondOutputStream(ContentType.parse(image.mimeType), HttpStatusCode.OK) {
                image.file.inputStream().copyTo(this)
            }
        }
    }
}

fun Route.routePrinterJobs() {
    get("/print/{hostId}/jobs") {
        call.do_respond(PrintJobsAPI(), CheckAction.List) {
            val hostIdParam = call.parameters["hostId"]!!
            val hostId = UUID.fromString(hostIdParam)
            val items = DB.find(PrintJob::class.java).where()
                .eq("host_id", hostId).and().isNull("done").findList()
            items.map { JobRs(it) }
        }
    }

    post("/print/{hostId}/jobs") {
        call.do_respond(PrintJobsAPI(), CheckAction.Create) {
            val hostIdParam = call.parameters["hostId"]!!
            val hostId = UUID.fromString(hostIdParam)
            val rq : JobCreateRq = call.receive()
            val dbo = rq.create(hostId)
            dbo.save()
            JobRs(dbo)
        }
    }

    delete("/print/{hostId}/jobs/{id}") {
        call.do_respond(PrintJobsAPI(), CheckAction.Delete) {
//            val hostIdParam = call.parameters["hostId"]!!
//            val hostId = UUID.fromString(hostIdParam)
            val jobIdParam = call.parameters["id"]!!
            val jobId = UUID.fromString(jobIdParam)
            val dbo = DB.find(PrintJob::class.java, jobId)
            val result = (dbo != null && dbo.done == null)
            if (result) {
                dbo?.done = Date()
                dbo?.save()
            }
            result
        }
    }
}

fun Application.configureRouting() {
//    install(CORS) {
//        allowHost("localhost:8080")
//        allowHost("127.0.0.1:8080")
//    }
    install(Resources)
    install(AutoHeadResponse)
    routing {
        route("/api/v1") {

            routeReport<VersionAPI>()

            post<LoginAPI> {
                call.respond(status = HttpStatusCode.OK, Ok(it.post(call.receive())))
            }

            authenticate("auth-jwt") {

                routeReport<EnumsAPI.PermissionsAPI.DocumentsAPI>()
                routeReport<EnumsAPI.PermissionsAPI.ReferencesAPI>()
                routeReport<EnumsAPI.PermissionsAPI.ReportsAPI>()

                routeReference<UserAPI, UserAPI.Id, User, UserRs, UserRs, UserRq, UserRq>()
                routeReference<RoleAPI, RoleAPI.Id, Role, RoleRs, RoleRs, RoleRq, RoleRq>()

                routeReference<VendorAPI, VendorAPI.Id, Vendor, VendorRs, VendorRs, VendorRq, VendorRq>()
                routeReference<ProductAPI, ProductAPI.Id, Product, ProductReadRs, ProductListRs, ProductRq, ProductRq>()
                routeSubReference<ProductAPI, ProductAPI.Id, Product, LotAPI, LotAPI.Id, Lot, LotRs, LotRs, LotRq, LotRq>()
                routeProductImages()

                routeReference<CounterpartyAPI, CounterpartyAPI.Id, Counterparty, CounterpartyReadRs, CounterpartyListRs, CounterpartyCreateRq, CounterpartyUpdateRq>()
                routeSubReference<CounterpartyAPI, CounterpartyAPI.Id, Counterparty, ContractAPI, ContractAPI.Id, Contract, ContractRs, ContractRs, ContractRq, ContractRq>()
                routeSubReference<CounterpartyAPI, CounterpartyAPI.Id, Counterparty, BankAccountAPI, BankAccountAPI.Id, BankAccount, BankAccountRs, BankAccountRs, BankAccountRq, BankAccountRq>()

                routeReference<StoreAPI, StoreAPI.Id, Store, StoreRs, StoreRs, StoreCreateRq, StoreUpdateRq>()
                routeSubReference<StoreAPI, StoreAPI.Id, Store, CellAPI, CellAPI.Id, Cell, CellRs, CellRs, CellRq, CellRq>()

                routeDocument<IncomingAPI, IncomingAPI.Id, Incoming, IncomingReadRs, IncomingListRs, IncomingRq, IncomingRq>()
                routeDocument<LayoutingAPI, LayoutingAPI.Id, Layouting, LayoutingReadRs, LayoutingListRs, LayoutingRq, LayoutingRq>()
                routeDocument<OrderingAPI, OrderingAPI.Id, Ordering, OrderingReadRs, OrderingListRs, OrderingRq, OrderingRq>()

                routeReference<PrintAPI, PrintAPI.Id, Host, HostRs, HostRs, HostRq, HostRq>()
                routeSubReference<PrintAPI, PrintAPI.Id, Host, PrinterAPI, PrinterAPI.Id, Printer, PrinterRs, PrinterRs, PrinterRq, PrinterRq>()
            }

        }
    }
}
