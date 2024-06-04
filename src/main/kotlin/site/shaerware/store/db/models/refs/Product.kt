package site.shaerware.store.db.models.refs

import io.ebean.annotation.Index
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
@Index(columnNames = ["barcode"])
@Index(columnNames = ["oem_code"])
@Index(columnNames = ["sku"])
@Index(columnNames = ["vendor_id", "oem_code"])
class Product(
    var name: String,

    @ManyToOne
    var vendor: Vendor,

    var oemCode: String?,

    var sku: String?,

    var barcode: String?,

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL])
    var lots: List<Lot> = emptyList(),

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL])
    val images: List<ProductImage> = emptyList(),
) : RefEntity()