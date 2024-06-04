package site.shaerware.store.setup

import io.ebean.DB
import io.ebean.annotation.Transactional
import io.ktor.server.application.*
import site.shaerware.store.db.models.auth.*
import site.shaerware.store.db.models.auth.enums.DocumentEnum
import site.shaerware.store.db.models.auth.enums.ReferenceEnum
import site.shaerware.store.db.models.auth.enums.ReportEnum
import site.shaerware.store.utils.Password

const val ADMIN_ROLE_NAME = "Admins"
const val ADMIN_USER_NAME = "admin"
const val ADMIN_USER_PASSWORD = "password"

const val PRINT_ROLE_NAME = "Printers"
const val PRINT_USER_NAME = "printer"
const val PRINT_USER_PASSWORD = "printer"

@Transactional
fun Application.configureDbSecurity() {
    var role = DB.find(Role::class.java).where().eq("name", ADMIN_ROLE_NAME).findOne()
    if (role == null) {
        log.info("Create admin role and user...")
        role = Role(ADMIN_ROLE_NAME)
        role.save()
        for (reference in ReferenceEnum.values()) {
            val px = ReferencePx(role, reference, true, true, true, true, true)
            px.save()
        }
        for (document in DocumentEnum.values()) {
            val px = DocumentPx(role, document, true, true, true, true, true, true)
            px.save()
        }
        for (report in ReportEnum.values()) {
            val px = ReportPx(role, report, true)
            px.save()
        }
        val user = User(ADMIN_USER_NAME, Password.hash(ADMIN_USER_PASSWORD))
        user.save()
        val userRole = UserRole(user, role)
        userRole.save()
    }
}
