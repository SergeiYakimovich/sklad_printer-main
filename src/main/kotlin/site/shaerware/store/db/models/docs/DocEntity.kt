package site.shaerware.store.db.models.docs

import io.ebean.Model
import io.ebean.annotation.DbDefault
import io.ebean.config.dbplatform.DbDefaultValue
import site.shaerware.store.db.models.auth.enums.DocumentEnum
import site.shaerware.store.db.models.docs.Document
import site.shaerware.store.db.models.refs.RefEntity
import java.util.Date
import java.sql.Timestamp
import java.util.UUID
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class DocEntity (
    @DbDefault(DbDefaultValue.NOW)
    var date: Date,

    var code: String,

    @DbDefault(DbDefaultValue.FALSE)
    var active: Boolean,
) : Model() {
    @Id
    open var id : UUID = UUID.randomUUID()

    abstract fun type() : DocumentEnum
    abstract fun process()
}