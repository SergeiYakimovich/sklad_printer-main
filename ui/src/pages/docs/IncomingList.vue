<template>
    <div>
        <h1>Приходные документы</h1>
        <button @click="newClick" class="btn-document-new">Создать приход...</button>
        <table>
            <tr>
                <th>ID</th>
                <th>Дата</th>
                <th>Номер</th>
                <th><abbr title="Флаг активности">Акт.</abbr></th>
                <th>Контрагент</th>
                <th>Договор</th>
                <th>Валюта</th>
                <th>Склад</th>
                <th colspan="2"></th>
            </tr>
            <tr v-for="document in documents" v-bind:key="document.id">
                <td>
                    <IdItem v-bind:uuid="document.id" v-bind:link_prefix="link_prefix" />
                </td>
                <td>{{ dateText(document.date) }}</td>
                <td>{{ document.code }}</td>
                <td>{{ activityText(document.active) }}</td>
                <td>{{ counterpartyName(document.counterpartyId) }}</td>
                <td>{{ contractName(document.counterpartyId, document.contractId) }}</td>
                <td>{{ document.currency }}</td>
                <td>{{ storeName(document.storeId) }}</td>
                <td><button @click="editClick" v-bind:data-item="document.id" class="btn-document-edit" /></td>
                <td><button @click="deleteClick" v-bind:data-item="document.id" class="btn-edit-delete" /></td>
            </tr>
        </table>
    </div>
</template>

<script>
import IdItem from '@/components/IdItem.vue';
import store from '@/store';
import axios from 'axios';

export default {
    data() {
        return {
            documents: [],
            link_prefix: "/incoming",
            cache: {
                counterparties: {},
                contracts: {},
                stores: {},
            }
        };
    },

    created() {
        axios.get(store.getters.apiRoot + 'incoming', {
            headers: {
                Authorization: `Bearer ${store.getters.user.token}`
            }
        }).then(resp => {
            const data = resp.data
            const success = data.success
            if (success) {
                this.documents = success
            } else {
                console.error(data.error)
            }
        }).catch(e => console.error(e))
    },

    methods: {
        dateText(date) {
            return new Date(date).toISOString()
        },

        activityText(active) {
            if (active) {
                return '+'
            } else {
                return ''
            }
        },

        counterpartyName(counterpartyId) {
            if (counterpartyId == null) {
                return null
            }

            if (this.cache.counterparties[counterpartyId] == null) {
                axios.get(store.getters.apiRoot + 'counterparty/' + counterpartyId, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        this.cache.counterparties[counterpartyId] = success.name
                    } else {
                        console.error(data.error)
                    }
                }).catch(e => console.error(e))
            }
            return this.cache.counterparties[counterpartyId]
        },

        contractName(counterpartyId, contractId) {
            if (counterpartyId == null || contractId == null) {
                return null
            }

            if (this.cache.contracts[contractId] == null) {
                axios.get(store.getters.apiRoot + 'counterparty/' + counterpartyId + '/contract/' + contractId, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        this.cache.contracts[contractId] = success.name
                    } else {
                        console.log(data.error)
                    }
                }).catch(e => console.error(e))
            }
            return this.cache.contracts[contractId]
        },

        storeName(storeId) {
            if (storeId == null) {
                return null
            }

            if (this.cache.stores[storeId] == null) {
                axios.get(store.getters.apiRoot + 'store/' + storeId, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        this.cache.stores[storeId] = success.name
                    } else {
                        console.error(data.error)
                    }
                }).catch(e => console.error(e))
            }
            return this.cache.stores[storeId]
        },

        newClick() {
            this.$router.push('/incoming/new')
        },

        editClick(event) {
            const id = event.target.dataset.item
            this.$router.push('/incoming/' + id)
        },

        deleteClick(event) {
            const id = event.target.dataset.item
            axios.delete(store.getters.apiRoot + 'incoming/' + id, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    const filtered = []
                    this.documents.forEach(d => {
                        if (d.id != id) {
                            filtered.push(d)
                        }
                    })
                    this.documents = filtered
                } else {
                    console.error(data.error)
                }
            }).catch(e => console.error(e))
        }
    },

    components: { IdItem }
}
</script>

<style></style>
