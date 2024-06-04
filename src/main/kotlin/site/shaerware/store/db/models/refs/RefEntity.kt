package site.shaerware.store.db.models.refs

import io.ebean.Model
import java.util.UUID
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class RefEntity: Model() {
    @Id
    open var id : UUID = UUID.randomUUID()
}