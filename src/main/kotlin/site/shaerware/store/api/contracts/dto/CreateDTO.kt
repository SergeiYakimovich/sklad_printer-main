package site.shaerware.store.api.contracts.dto

import io.ebean.Model

interface CreateDTO<T:Model> {
    fun create(): T
}
