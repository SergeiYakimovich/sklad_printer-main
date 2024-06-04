package site.shaerware.store.db.models.auth

import io.ebean.annotation.Index
import site.shaerware.store.db.models.auth.enums.ReportEnum
import site.shaerware.store.db.models.refs.RefEntity
import site.shaerware.store.db.models.refs.SubEntityCt
import javax.persistence.*

@Entity
@Table(name = "auth_reports")
@Index(columnNames = ["owner_id", "report"], unique = true)
class ReportPx(
    @ManyToOne(cascade = [CascadeType.ALL])
    override var owner: Role,

    @Enumerated(EnumType.STRING)
    var report: ReportEnum,

    var allowUse: Boolean?,
) : RefEntity(), SubEntityCt<Role>