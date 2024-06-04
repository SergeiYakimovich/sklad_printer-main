package site.shaerware.store.db.models.print

import com.fasterxml.jackson.annotation.JsonIgnore
import io.ebean.annotation.Index
import site.shaerware.store.db.models.refs.RefEntity
import site.shaerware.store.db.models.refs.SubEntityCt
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
@Index(columnNames = ["owner_id", "name"], unique = true)
class Printer(
    @JsonIgnore
    @ManyToOne
    override var owner: Host,

    var name: String,

    var pageWidth: Float?,
    var pageHeight: Float?,

    var horizontalMargins: Float?,
    var verticalMargins: Float?,
    var gap: Float?,

    var colCount: Int?,
    var rowCount: Int?
) : RefEntity(), SubEntityCt<Host>
