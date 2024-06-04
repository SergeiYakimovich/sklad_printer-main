package site.shaerware.store.db.models.refs

import io.ebean.annotation.DbDefault
import io.ebean.annotation.Length
import java.util.Date
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class Counterparty(
    var name: String,

    var fullName: String?,

    var legalForm: String,

    @Length(12)
    var inn: String?,

    @Length(9)
    var kpp: String?,

    @Length(14)
    var okpo: String?,

    @Length(13)
    var ogrn: String?,

    var registration: Date?,

    // contacts
    var addressLegal: String?,
    var addressActual: String?,
    var email: String?,
    var emailForDoc: String?,
    var site: String?,
    var phone: String?,
    var directorGeneral: String?,
    var accountantGeneral: String?,

    @OneToMany(mappedBy = "owner")
    var contracts: List<Contract> = emptyList(),

    @OneToMany(mappedBy = "owner")
    var bankAccounts: List<BankAccount> = emptyList(),
) :
    RefEntity()