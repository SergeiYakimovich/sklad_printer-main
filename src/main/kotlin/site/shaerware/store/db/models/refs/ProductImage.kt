package site.shaerware.store.db.models.refs

import com.fasterxml.jackson.annotation.JsonIgnore
import io.ebean.annotation.DbDefault
import java.io.File
import javax.persistence.Entity
import javax.persistence.Lob
import javax.persistence.ManyToOne

@Entity
class ProductImage(
    @JsonIgnore
    @ManyToOne
    override var owner: Product,

    @JsonIgnore
    @Lob
    var file: File,

    var type: String?,

    @DbDefault("application/octet-stream")
    var mimeType: String,
) : RefEntity(), SubEntityCt<Product> {
    val url: String
        get() {
            return "/api/v1/product/${owner.id}/image/$id/file"
        }
}