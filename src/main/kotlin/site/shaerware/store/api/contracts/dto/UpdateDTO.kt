package site.shaerware.store.api.contracts.dto

import io.ebean.Model

interface UpdateDTO<T: Model> {
    fun apply(model:T)
}