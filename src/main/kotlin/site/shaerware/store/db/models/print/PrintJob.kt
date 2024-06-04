package site.shaerware.store.db.models.print

import io.ebean.Model
import io.ebean.annotation.Index
import site.shaerware.store.db.models.refs.RefEntity
import java.util.Date
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
@Index(columnNames = ["host_id", "printer_id", "init"], unique = true)
@Index(columnNames = ["init"], unique = false)
@Index(columnNames = ["host_id", "printer_id", "done"], unique = false)
@Index(columnNames = ["done"], unique = false)
class PrintJob(
    @ManyToOne
    val host: Host,

    @ManyToOne
    val printer: Printer,

    val init: Date,
    var done: Date?,
    var data: String
) : RefEntity() {
}