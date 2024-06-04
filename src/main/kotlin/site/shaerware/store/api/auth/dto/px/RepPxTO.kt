package site.shaerware.store.api.auth.dto.px

import io.ebean.DB
import site.shaerware.store.db.models.auth.ReferencePx
import site.shaerware.store.db.models.auth.ReportPx
import site.shaerware.store.db.models.auth.Role
import site.shaerware.store.db.models.auth.enums.ReferenceEnum
import site.shaerware.store.db.models.auth.enums.ReportEnum
import site.shaerware.store.db.models.auth.ReportPx as DbReportPx
import site.shaerware.store.db.models.auth.Role as DbRole
import site.shaerware.store.db.models.auth.enums.ReportEnum as EnumReport

data class RepPxTO(
    val report: String,
    val allowUse: Boolean?
)
{
    constructor(model: DbReportPx):this(model.report.name, model.allowUse)

    fun create(owner: DbRole): DbReportPx {
        return DbReportPx(
            owner,
            EnumReport.valueOf(report),
            allowUse
        )
    }

    fun create_or_update(owner: Role) : ReportPx {
        var dbo = DB.find(ReportPx::class.java)
            .where().eq("owner_id", owner.id)
            .and().eq("report", report).findOne()
        if (dbo == null) {
            dbo = ReportPx(
                owner,
                ReportEnum.valueOf(report),
                allowUse
            )
        } else {
            dbo.allowUse = allowUse
        }
        return dbo
    }
}