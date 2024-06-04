package site.shaerware.store.api.references.dto

import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.dto.UpdateDTO
import site.shaerware.store.api.references.CounterpartyAPI
import site.shaerware.store.db.models.refs.Counterparty
import java.util.Date

data class CounterpartyUpdateRq(
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
):RQ<CounterpartyAPI>, UpdateDTO<Counterparty> {
    override fun apply(model: Counterparty) {
        model.name = name
        model.fullName = fullName
        model.legalForm = legalForm
        model.inn = inn
        model.kpp = kpp
        model.okpo = okpo
        model.ogrn = ogrn
        model.registration = registration
        model.addressLegal = addressLegal
        model.addressActual = addressActual
        model.email = email
        model.emailForDoc = emailForDoc
        model.site = site
        model.phone = phone
        model.directorGeneral = directorGeneral
        model.accountantGeneral = accountantGeneral
    }
}
