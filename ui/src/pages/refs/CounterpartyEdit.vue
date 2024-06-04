<template>
    <div class="edit-form">
        <label v-if="showId()">ID:
            <input v-model="uuid" disabled />
        </label>
        <label>Название:
            <input v-model="counterparty.name" />
        </label>
        <label>Полное название:
            <input v-model="counterparty.fullName" />
        </label>
        <label>Правовая форма:
            <input v-model="counterparty.legalForm" />
        </label>
        <label>ИНН:
            <input v-model="counterparty.inn" />
        </label>
        <label>КПП:
            <input v-model="counterparty.kpp" />
        </label>
        <label>ОКПО:
            <input v-model="counterparty.okpo" />
        </label>
        <label>ОГРН:
            <input v-model="counterparty.ogrn" />
        </label>
        <label>Дата регистрации:
            <input v-model="counterparty.registrationText" type="date" />
        </label>
        <label>Юр. адрес:
            <input v-model="counterparty.addressLegal" />
        </label>
        <label>Факт. адрес:
            <input v-model="counterparty.addressActual" />
        </label>
        <label>E-mail:
            <input v-model="counterparty.email" type="email" />
        </label>
        <label>E-mail для документов:
            <input v-model="counterparty.emailForDoc" type="email" />
        </label>
        <label>Сайт:
            <input v-model="counterparty.site" type="url" />
        </label>
        <label>Телефон:
            <input v-model="counterparty.phone" type="phone" />
        </label>
        <label>Директор:
            <input v-model="counterparty.directorGeneral" />
        </label>
        <label>Главбух:
            <input v-model="counterparty.accountantGeneral" />
        </label>

        <label class="bordered">Договоры:
            <table>
                <tr>
                    <th>ID</th>
                    <th>Тип</th>
                    <th>Номер</th>
                    <th>Название</th>
                    <th>Дата</th>
                    <th>Срок действия</th>
                    <th>Отсрочка</th>
                    <th colspan="2"></th>
                </tr>
                <tr v-for="contract in counterparty.contracts" v-bind:key="contract.id">
                    <td><IdItem v-bind:uuid="contract.id" v-bind:link_prefix="contract_prefix" /></td>
                    <td>{{ kindText(contract.kind) }}</td>
                    <td>{{ contract.code }}</td>
                    <td>{{ contract.name }}</td>
                    <td>{{ dateText(contract.date) }}</td>
                    <td>{{ dateText(contract.expiration) }}</td>
                    <td>{{ contract.deferment }}</td>
                    <td><button @click="editContractClick" v-bind:data-item="contract.id" class="btn-document-edit" /></td>
                    <td><button @click="deleteContractClick" v-bind:data-item="contract.id" class="btn-edit-delete" /></td>
                </tr>
            </table>
            <button @click="addContractClick" class="btn-document-new">Добавить договор...</button>
        </label>
        <label class="bordered">Банковские счета:
            <table>
                <tr>
                    <th>ID</th>
                    <th>Название</th>
                    <th>Банк</th>
                    <th>БИК</th>
                    <th>Корр. счет</th>
                    <th>Номер счета</th>
                    <th colspan="2"></th>
                </tr>
                <tr v-for="account in counterparty.bankAccounts" v-bind:key="account.id">
                    <td><IdItem v-bind:uuid="account.id" v-bind:link_prefix="account_prefix" /></td>
                    <td>{{ account.name }}</td>
                    <td>{{ account.bank }}</td>
                    <td>{{ account.bik }}</td>
                    <td>{{ account.correspondentAcc }}</td>
                    <td>{{ account.accountNumber }}</td>
                    <td><button @click="editAccountClick" v-bind:data-item="account.id" class="btn-document-edit" /></td>
                    <td><button @click="deleteAccountClick" v-bind:data-item="account.id" class="btn-edit-delete" /></td>
                </tr>
            </table>
            <button @click="addAccountClick" class="btn-document-new">Добавить счет...</button>
        </label>

        <div class="button-bar">
            <button class="btn-dialog-ok-apply" @click="okClick">OK</button>
            <button class="btn-dialog-cancel" @click="cancelClick">Cancel</button>
        </div>
    </div>
</template>

<script>
import IdItem from '@/components/IdItem.vue';
import store from '@/store';
import axios from 'axios';

export default {
    data() {
        return {
            uuid: null,
            counterparty: {
                contracts: [],
                bankAccounts: [],
            },
            prevPage: null,
            contract_prefix: null,
            account_prefix: null,
        };
    },

    created() {
        this.uuid = this.$route.params.id;
        if (this.uuid != "new") {
            axios.get(store.getters.apiRoot + "counterparty/" + this.uuid, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data;
                const success = data.success;
                if (success) {
                    this.counterparty = success;
                    this.counterparty.registrationText = new Date(this.counterparty.registration).toISOString().slice(0,10)
                }
                else {
                    console.error(data.error);
                }
            }).catch(e => console.error(e));
        }
        this.prevPage = this.$router.options.history.state.back;
        this.contract_prefix = '/counterparty/' + this.uuid + '/contract'
        this.account_prefix = '/counterparty/' + this.uuid + '/bankaccount'
    },

    methods: {
        showId() {
            return this.uuid != "new";
        },

        okClick() {
            const data = this.counterparty;
            delete data.id
            delete data.contracts
            delete data.bankAccounts
            data.registration = data.registrationText             
            delete data.registrationText
            if (this.uuid == "new") {
                axios.post(store.getters.apiRoot + "counterparty", data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data;
                    const success = data.success;
                    if (success) {
                        console.log(success);
                        this.$router.push("/counterparty");
                    }
                    else {
                        console.error(data.error);
                    }
                }).catch(e => console.error(e));
            }
            else {
                axios.put(store.getters.apiRoot + "counterparty/" + this.uuid, data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data;
                    const success = data.success;
                    if (success) {
                        console.log(success);
                        this.$router.push("/counterparty");
                    }
                    else {
                        console.error(data.error);
                    }
                }).catch(e => console.error(e));
            }
        },

        cancelClick() {
            this.$router.push("/counterparty");
        },

        addContractClick() {
            this.$router.push(this.contract_prefix + '/new')
        },

        editContractClick(event) {
            this.$router.push(this.contract_prefix + '/' + event.target.dataset.item)
        },

        deleteContractClick(event) {
            const id = event.target.dataset.item
            axios.delete(store.getters.apiRoot + 'counterparty/' + this.uuid + '/contract/' + id, {
                headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    console.log(success)
                    const filtered = []
                    this.counterparty.contracts.forEach(c => {                     // TODO: почекать на предмет .map
                        if (c.id != id) {
                            filtered.push(c)
                        }
                    })
                    this.counterparty.contracts = filtered
                } else {
                    console.error(data.error)
                }
            }).catch(e=> console.error(e))
        },

        addAccountClick() {
            this.$router.push(this.account_prefix + '/new')
        },

        editAccountClick(event) {
            this.$router.push(this.account_prefix + '/' + event.target.dataset.item)
        },

        deleteAccountClick() {
            const id = event.target.dataset.item
            axios.delete(store.getters.apiRoot + 'counterparty/' + this.uuid + '/bankaccount/' + id, {
                headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    console.log(success)
                    const filtered = []
                    this.counterparty.bankAccounts.forEach(a => {                     // TODO: почекать на предмет .map
                        if (a.id != id) {
                            filtered.push(a)
                        }
                    })
                    this.counterparty.bankAccounts = filtered
                } else {
                    console.error(data.error)
                }
            }).catch(e=> console.error(e))
        },

        kindText(kind) {
            if (kind === 'Buyer') {
                return 'Покупатель'
            } else if (kind === 'Seller') {
                return 'Продавец'
            } else if (kind == null) {
                return ''
            } else {
                return 'Неизвестный тип ('+ kind +')'
            }
        },

        dateText(date) {
            return new Date(date).toISOString().slice(0, 10)
        }
    },

    components: { IdItem }
}
</script>

<style>
.bordered {
    border: 1px dotted silver;
}
</style>
