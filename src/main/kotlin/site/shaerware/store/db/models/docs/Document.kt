package site.shaerware.store.db.models.docs

import io.ebean.annotation.DbDefault
import io.ebean.config.dbplatform.DbDefaultValue
import site.shaerware.store.db.models.auth.enums.DocumentEnum
import site.shaerware.store.db.models.refs.RefEntity
import java.util.Date
import java.sql.Timestamp
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Document(
    @DbDefault(DbDefaultValue.NOW)
    var date : Date,

    @Enumerated(EnumType.STRING)
    var type : DocumentEnum,

    var code: String,

    @DbDefault(DbDefaultValue.FALSE)
    var active: Boolean,
) : RefEntity()