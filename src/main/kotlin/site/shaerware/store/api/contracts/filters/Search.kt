package site.shaerware.store.api.contracts.filters

import io.ebean.Query

interface Search<T> {

    enum class Mode {
        Begin,
        Middle,
        End,
        Equals,
    }

    val search: String?

    fun applySearch(query: Query<T>, field: String, mode: Mode = Mode.Middle, case: Boolean = false) :Query<T> {
        if (!search.isNullOrEmpty()) {
            val where = query.where()
            val quest = when (mode) {
                Mode.Begin -> if (case) where.like(field, "${search}%") else where.ilike(field, "${search}%")
                Mode.Middle -> if (case) where.like(field, "%${search}%") else where.ilike(field, "%${search}%")
                Mode.End -> if (case) where.like(field, "%${search}") else where.ilike(field, "%${search}")
                Mode.Equals -> if (case) where.eq(field, search!!) else where.ieq(field, search!!)
            }
            return quest.query()
        } else {
            return query
        }
    }
}