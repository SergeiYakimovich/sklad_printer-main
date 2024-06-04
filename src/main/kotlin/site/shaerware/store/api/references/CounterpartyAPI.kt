package site.shaerware.store.api.references

import io.ebean.DB
import io.ktor.resources.*
import site.shaerware.store.api.contracts.EntityAPI
import site.shaerware.store.api.contracts.EntityByIdAPI
import site.shaerware.store.api.contracts.SubEntityAPI
import site.shaerware.store.api.contracts.SubEntityByIdAPI
import site.shaerware.store.api.contracts.filters.Period
import site.shaerware.store.api.contracts.filters.Search
import site.shaerware.store.api.references.dto.*
import site.shaerware.store.db.models.refs.BankAccount
import site.shaerware.store.db.models.refs.Contract
import site.shaerware.store.db.models.refs.Counterparty
import java.util.UUID

@Resource("/counterparty")
class CounterpartyAPI(
    override val search: String? = null,
    val inn: String? = null,
) :EntityAPI<Counterparty, CounterpartyReadRs, CounterpartyListRs, CounterpartyCreateRq>, Search<Counterparty>
{

    override fun rs(dbo: Counterparty): CounterpartyReadRs {
        return CounterpartyReadRs(dbo)
    }

    override fun <ID> get(id: ID): Counterparty {
        val uuid = UUID.fromString(id.toString())
        return DB.find(Counterparty::class.java, uuid)!!
    }

    override fun list(): List<CounterpartyListRs> {
        var query = DB.find(Counterparty::class.java)
        if (!search.isNullOrEmpty()) {
            val pattern = "%${search}%"
            query = query.where().ilike("name", pattern)
                .or().ilike("full_name", pattern).query()
        }
        if (!inn.isNullOrEmpty())
            query = query.where().eq("inn", inn).query()
        return query.findList().map { c -> CounterpartyListRs(c) }
    }

    @Resource("{counterpartyId}")
    class Id(
        override val parent: CounterpartyAPI = CounterpartyAPI(),
        val counterpartyId: String
    ) : EntityByIdAPI<String, CounterpartyAPI, Counterparty,CounterpartyReadRs, CounterpartyListRs, CounterpartyCreateRq, CounterpartyUpdateRq> {

        override val id: String
            get() = counterpartyId

        @Resource("/contract")
        class ContractAPI(
            override val parent: CounterpartyAPI.Id,
            override val search: String? = null,
            override val from: String? = null,
            override val to: String? = null,
            val code: String? = null,
        ) : SubEntityAPI<String, CounterpartyAPI, CounterpartyAPI.Id, Counterparty, Contract, ContractRs, ContractRs, ContractRq>, Search<Contract>, Period<Contract> {

            override fun rs(dbo: Contract): ContractRs {
                return ContractRs(dbo)
            }

            override fun <ID> get(id: ID): Contract {
                val uuid = UUID.fromString(id.toString())
                return DB.find(Contract::class.java, uuid)!!
            }

            override fun list(): List<ContractRs> {
                var query = DB.find(Contract::class.java).where().eq("owner_id", parent.item().id).query()
                query = applySearch(query, "name", Search.Mode.Middle)
                query = applyPeriod(query, "date")
                if (!code.isNullOrEmpty())
                    query = query.where().ilike("code", "${code}%").query()
                return query.findList().map { c -> ContractRs(c) }
            }

            @Resource("{contractId}")
            class Id(
                override val parent: ContractAPI,
                val contractId: String
            ) : SubEntityByIdAPI<String, CounterpartyAPI, CounterpartyAPI.Id, Counterparty, String, ContractAPI, Contract, ContractRs, ContractRs, ContractRq, ContractRq> {
                override val id: String
                    get() = contractId
            }

        }

        @Resource("/bankaccount")
        class BankAccountAPI(
            override val parent: CounterpartyAPI.Id,
            override val search: String? = null,
            val bank: String? = null,
            val account: String? = null,
        ) : SubEntityAPI<String, CounterpartyAPI, CounterpartyAPI.Id, Counterparty, BankAccount, BankAccountRs, BankAccountRs, BankAccountRq>, Search<BankAccount> {

            override fun rs(dbo: BankAccount): BankAccountRs {
                return BankAccountRs(dbo)
            }

            override fun <ID> get(id: ID): BankAccount {
                val uuid = UUID.fromString(id.toString())
                return DB.find(BankAccount::class.java, uuid)!!
            }

            override fun list(): List<BankAccountRs> {
                var query = DB.find(BankAccount::class.java).where().eq("owner_id", parent.item().id).query()
                query = applySearch(query, "name", Search.Mode.Middle)
                if (!bank.isNullOrEmpty())
                    query = query.where().ilike("bank", "%${bank}%")
                        .or().eq("bik", bank).query()
                if (!account.isNullOrEmpty())
                    query = query.where().eq("account_number", account).query()
                return query.findList().map { a -> BankAccountRs(a) }
            }

            @Resource("{bankaccountId}")
            class Id(
                override val parent: BankAccountAPI,
                val bankaccountId: String
            ) : SubEntityByIdAPI<String, CounterpartyAPI, CounterpartyAPI.Id, Counterparty, String, BankAccountAPI, BankAccount, BankAccountRs, BankAccountRs, BankAccountRq, BankAccountRq> {
                override val id: String
                    get() = bankaccountId
            }

        }

    }

}