package site.shaerware.store.db.models.refs

import io.ebean.annotation.Index
import javax.persistence.Entity

@Entity
@Index(columnNames = ["name"], unique = true)
class Vendor(
    var name: String
) : RefEntity() {
}