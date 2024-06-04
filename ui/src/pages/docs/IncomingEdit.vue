<template>
    <div class="edit-form">
        <label v-if="showId()">ID:
            <input v-model="uuid" disabled />
        </label>
        <label>Дата:
            <input v-model="document.dateText" type="datetime-local" />
        </label>
        <label>Номер:
            <input v-model="document.code" placeholder="Номер документа" />
        </label>
        <label>Активный:
            <input v-model="document.active" type="checkbox" />
        </label>
        <label>Контрагент:
            <select v-model="document.counterpartyId" @change="counterpartyChange">
                <option v-for="counterparty in counterparties" v-bind:key="counterparty.id" v-bind:value="counterparty.id">
                    {{ counterparty.name }}</option>
            </select>
        </label>
        <label>Договор:
            <select v-model="document.contractId">
                <option v-for="contract in contracts" v-bind:key="contract.id" v-bind:value="contract.id">{{ contract.name }}</option>
            </select>
        </label>
        <label>Валюта:
            <select v-model="document.currency">
                <option value="RUB">RUB</option>
                <option value="USD">USD</option>
                <option value="EUR">EUR</option>
            </select>
        </label>
        <label>Склад:
            <select v-model="document.storeId">
                <option v-for="store in stores" v-bind:key="store.id" v-bind:value="store.id">{{ store.name }}</option>
            </select>
        </label>
        <table>
            <tr>
                <th>#</th>
                <th>OEM</th>
                <th>Товар</th>
                <th>Кол-во</th>
                <th>Цена</th>
                <th>Вес</th>
                <th>Стоимость</th>
                <th></th>
            </tr>
            <tr v-for="item in document.items" v-bind:key="item.orderNo">
                <td>{{ item.orderNo }}</td>
                <td><input v-model="item.oemCode" placeholder="OEM" /></td>
                <td>
                    <select v-model="item.productId">
                        <option v-for="product in products" v-bind:key="product.id" v-bind:value="product.id">{{ product.name }}</option>
                    </select>
                </td>
                <td><input v-model="item.quantity" type="number" step="0.001" /></td>
                <td><input v-model="item.price" type="number" step="0.01" /></td>
                <td><input v-model="item.weight" type="number" step="0.001" /></td>
                <td><input v-model="item.cost" type="number" step="0.01" /></td>
                <td><button @click="deleteClick" v-bind:data-item="item.orderNo" class="btn-list-remove" /></td>
            </tr>
            <tr>
                <td><button @click="addClick" class="btn-list-add" /></td>
                <td colspan="7"></td>
            </tr>
        </table>
        <div class="button-bar">
            <button class="btn-dialog-ok-apply" @click="okClick">OK</button>
            <button class="btn-dialog-cancel" @click="cancelClick">Cancel</button>
        </div>
    </div>
</template>

<script>
import store from '@/store';
import axios from 'axios';

export default {
    data() {
        return {
            uuid: null,
            document: { items: [] },
            counterparties: [],
            contracts: [
                {
                    id: null,
                    name: 'Выберите сначала контрагента'
                }
            ],
            stores: [],
            products: [],
        }
    },

    created() {
        this.uuid = this.$route.params.id
        if (this.uuid != 'new') {
            axios.get(store.getters.apiRoot + 'incoming/' + this.uuid, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    this.document = success
                    this.document.dateText = new Date(this.document.date).toISOString().slice(0, 16)
                } else {
                    console.error(data.error)
                }
            }).catch(e => console.error(e))
        } else {
            const utc = new Date()
            const now = new Date((utc.getTime() + (-utc.getTimezoneOffset() * 60000))).toISOString()
            this.document.dateText = now.slice(0, 16)
        }
        axios.get(store.getters.apiRoot + 'counterparty', {
            headers: {
                Authorization: `Bearer ${store.getters.user.token}`
            }
        }).then(resp => {
            const data = resp.data
            const success = data.success
            if (success) {
                this.counterparties = success
                if (this.document.counterpartyId != null) {
                    this.counterpartyChange()
                }
            } else {
                console.error(data.error)
            }
        }).catch(e => console.error(e))
        axios.get(store.getters.apiRoot + 'store', {
            headers: {
                Authorization: `Bearer ${store.getters.user.token}`
            }
        }).then(resp => {
            const data = resp.data
            const success = data.success
            if (success) {
                this.stores = success
            } else {
                console.error(data.error)
            }
        }).catch(e => console.error(e))
        axios.get(store.getters.apiRoot + 'product', {
            headers: {
                Authorization: `Bearer ${store.getters.user.token}`
            }
        }).then(resp => {
            const data = resp.data
            const success = data.success
            if (success) {
                this.products = success
            } else {
                console.error(data.error)
            }
        }).catch(e => console.error(e))
    },

    methods: {
        showId() {
            return this.uuid != 'new'
        },

        counterpartyChange() {
            if (this.document.counterpartyId == null) {
                this.contracts = [
                    {
                        id: null,
                        name: 'Выберите сначала контрагента'
                    }
                ]
            } else {
                axios.get(store.getters.apiRoot + 'counterparty/' + this.document.counterpartyId + '/contract', {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        this.contracts = success
                    } else {
                        console.error(data.error)
                    }
                }).catch(e => console.error(e))
            }
        },

        addClick() {
            const count = this.document.items.length
            this.document.items.push({ orderNo: count + 1 })
        }, 

        deleteClick(event) {
            const no = event.target.dataset.item
            const filtered = []
            this.document.items.forEach(i => {
                if (i.orderNo != no) {
                    if (i.orderNo > no) {
                        i.orderNo = i.orderNo - 1
                    }
                    filtered.push(i)
                }
            })
            this.document.items = filtered
        }, 

        okClick() {
            const data = this.document
            delete data.id
            data.date = new Date(data.dateText).toISOString()
            delete data.dateText
            console.log({
                document: this.document,
                data: data
            })
            if (this.uuid == 'new') {
                axios.post(store.getters.apiRoot + 'incoming', data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        console.log(success)
                        this.$router.push('/incoming')
                    } else {
                        console.error(data.error)
                    }
                }).catch(e => console.error(e))
            } else {
                axios.put(store.getters.apiRoot + 'incoming/' + this.uuid, data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        console.log(success)
                        this.$router.push('/incoming')
                    } else {
                        console.error(data.error)
                    }
                }).catch(e => console.error(e))
            }
        },

        cancelClick() {
            this.$router.push('/incoming')
        }
    }
}
</script>

<style></style>
