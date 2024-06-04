package site.shaerware.store.db.models.auth

import io.ebean.DB
import io.ebean.annotation.Index
import site.shaerware.store.db.models.refs.RefEntity
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "auth_roles")
@Index(columnNames = ["name"], unique = true)
class Role(
    var name: String,

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL], orphanRemoval = true)
    var references: List<ReferencePx> = emptyList(),

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL], orphanRemoval = true)
    var documents: List<DocumentPx> = emptyList(),

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL], orphanRemoval = true)
    var reports: List<ReportPx> = emptyList(),
) : RefEntity() {

//    override fun save() {
//        super.save()
//        val refs = references.map { r -> r.id }
//        DB.find(ReferencePx::class.java)
//            .where().eq("owner_id", id)
//            .and().notIn("id", refs)
//            .delete()
//        val docs = documents.map { d -> d.id }
//        DB.find(DocumentPx::class.java)
//            .where().eq("owner_id", id)
//            .and().notIn("id", docs)
//            .delete()
//        val reps = reports.map { r -> r.id }
//        DB.find(ReportPx::class.java)
//            .where().eq("owner_id", id)
//            .and().notIn("id", reps)
//            .delete()
//    }
}