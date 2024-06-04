package site.shaerware.store.api.references.dto

import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.references.CounterpartyAPI
import site.shaerware.store.db.models.refs.Counterparty
import java.util.Date
import java.util.*

data class CounterpartyListRs(
    val id: UUID,
    val name : String,
    val fullName: String?,
    val legalForm: String,
    val inn: String?,
    val kpp: String?,
    val okpo: String?,
    val ogrn: String?,
    val registration: Date?,
    val addressLegal: String?,
    val addressActual: String?,
    val email: String?,
    val emailForDoc: String?,
    val site: String?,
    val phone: String?,
    val directorGeneral: String?,
    val accountantGeneral: String?,
):RS<CounterpartyAPI>,ListDTO<Counterparty>{
    constructor(model: Counterparty):this(
        model.id,
        model.name,
        model.fullName,
        model.legalForm,
        model.inn,
        model.kpp,
        model.okpo,
        model.ogrn,
        model.registration,
        model.addressLegal,
        model.addressActual,
        model.email,
        model.emailForDoc,
        model.site,
        model.phone,
        model.directorGeneral,
        model.accountantGeneral,
    )
}
