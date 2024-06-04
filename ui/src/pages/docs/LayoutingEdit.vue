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
        <label>Приход:
            <select v-model="document.incomingId">
                <option v-for="incoming in incomings" v-bind:key="incoming.id" v-bind:value="incoming.id">{{
                    incomingText(incoming) }}</option>
            </select>
        </label>
        <label>Склад:
            <select v-model="document.storeId" @change="storeChange">
                <option v-for="store in stores" v-bind:key="store.id" v-bind:value="store.id">{{ store.name }}</option>
            </select>
        </label>
        <table>
            <tr>
                <th>#</th>
                <th>Откуда</th>
                <th>Куда</th>
                <th>Товар</th>
                <th>Кол-во</th>
                <th colspan="2"></th>
            </tr>
            <tr v-for="item in document.items" v-bind:key="item.orderNo">
                <td>{{ item.orderNo }}</td>
                <td>
                    <select v-model="item.fromId">
                        <option v-for="cell in cells" v-bind:key="cell.id" v-bind:value="cell.id">{{ cell.code }}</option>
                    </select>
                </td>
                <td>
                    <select v-model="item.toId">
                        <option v-for="cell in cells" v-bind:key="cell.id" v-bind:value="cell.id">{{ cell.code }}</option>
                    </select>
                </td>
                <td>
                    <select v-model="item.productId">
                        <option v-for="product in products" v-bind:key="product.id" v-bind:value="product.id">{{ product.name }}</option>
                    </select>
                </td>
                <td><input v-model="item.quantity" type="number" step="0.001" /></td>
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
import store from '@/store'
import axios from 'axios'

export default {
    data() {
        return {
            uuid: null,
            document: { items: [] },
            incomings: [],
            stores: [],
            cells: [
                {
                    id: null,
                    code: "Вначале выберите склад"
                }
            ],
            products: [],
        }
    },

    created() {
        this.uuid = this.$route.params.id
        if (this.uuid != 'new') {
            axios.get(store.getters.apiRoot + 'layouting/' + this.uuid, {
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

        axios.get(store.getters.apiRoot + 'incoming', {
            headers: {
                Authorization: `Bearer ${store.getters.user.token}`
            }
        }).then(resp => {
            const data = resp.data
            const success = data.success
            if (success) {
                this.incomings = success
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
                if (this.document.storeId != null) {
                    this.storeChange()
                }
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

        incomingText(doc) {
            return '№' + doc.code + ' от ' + new Date(doc.date).toISOString().slice(0, 16)
        },

        storeChange() {
            axios.get(store.getters.apiRoot + 'store/' + this.document.storeId + '/cell', {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    this.cells = success
                    this.cells.unshift({ id: null, code: 'Не выбрано' })
                } else {
                    console.error(data.error)
                }
            }).catch(e => console.error(e))
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
                axios.post(store.getters.apiRoot + 'layouting', data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        console.log(success)
                        this.$router.push('/layouting')
                    } else {
                        console.error(data.error)
                    }
                }).catch(e => console.error(e))
            } else {
                axios.put(store.getters.apiRoot + 'layouting/' + this.uuid, data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        console.log(success)
                        this.$router.push('/layouting')
                    } else {
                        console.error(data.error)
                    }
                }).catch(e => console.error(e))
            }
        },

        cancelClick() {
            this.$router.push('/layouting')
        }
    }
}
</script>

<style></style>
