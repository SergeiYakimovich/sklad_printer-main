package site.shaerware.store.db.models.auth.enums

import kotlin.reflect.KClass
import site.shaerware.store.api.documents.IncomingAPI
import site.shaerware.store.api.documents.LayoutingAPI
import site.shaerware.store.api.documents.OrderingAPI

enum class DocumentEnum(val cls: KClass<*>, val title: String) {
    Incoming(IncomingAPI::class, "Приход"),
    Layouting(LayoutingAPI::class, "Распределение"),
    Ordering(OrderingAPI::class, "Заказ");
}