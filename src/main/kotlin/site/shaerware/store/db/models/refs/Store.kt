package site.shaerware.store.db.models.refs

import io.ebean.annotation.Index
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
@Index(columnNames = ["name"], unique = true)
class Store(
    var name : String,

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL])
    var cells : List<Cell> = emptyList()
)
    : RefEntity() {
}