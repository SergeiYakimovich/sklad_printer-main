package site.shaerware.store.db.models.print

import io.ebean.annotation.Index
import site.shaerware.store.db.models.refs.RefEntity
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
@Index(columnNames = ["name"], unique = true)
class Host(
    var name: String,

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL])
    var printers: List<Printer> = emptyList()
) : RefEntity()
