package site.shaerware.store.db.models.docs

import java.util.*
import javax.persistence.Embeddable

@Embeddable
data class DocItemId(
    val ownerId: UUID,
    val orderNo: Int
)
