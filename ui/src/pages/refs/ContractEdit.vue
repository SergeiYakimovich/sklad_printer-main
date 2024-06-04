<template>
    <div class="edit-form">
        <label v-if="showId()">ID:
            <input v-model="uuid" disabled />
        </label>
        <label>Тип:
            <select v-model="contract.kind">
                <option value="Buyer">Покупатель</option>
                <option value="Seller">Продавец</option>
            </select>
        </label>
        <label>Номер:
            <input v-model="contract.code" placeholder="Номер договора" />
        </label>
        <label>Название:
            <input v-model="contract.name" placeholder="Название договора" />
        </label>
        <label>Дата:
            <input v-model="contract.dateText" type="date" />
        </label>
        <label>Истекает:
            <input v-model="contract.expirationText" type="date" />
        </label>
        <label>Отсрочка:
            <input v-model="contract.deferment" type="number" step="1" />
        </label>
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
            owner: null,
            uuid: null,
            contract: { deferment: 0 }
        }
    },

    created() {
        this.owner = this.$route.params.ownerId
        this.uuid = this.$route.params.id

        if (this.uuid != 'new') {
            axios.get(store.getters.apiRoot + 'counterparty/' + this.owner + '/contract/' + this.uuid, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    this.contract = success
                    this.contract.dateText = new Date(this.contract.date).toISOString().slice(0, 10)
                    this.contract.expirationText = new Date(this.contract.expiration).toISOString().slice(0, 10)
                } else {
                    console.error(data.error)
                }
            }).catch(e => console.error(e))
        }
    },

    methods: {
        showId() {
            return this.uuid != "new";
        },

        okClick() {
            const data = this.contract
            delete data.id
            delete data.ownerId
            data.date = data.dateText
            data.expiration = data.expirationText
            delete data.dateText
            delete data.expirationText
            if (this.uuid == 'new') {
                axios.post(store.getters.apiRoot + 'counterparty/' + this.owner + '/contract', data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        console.log(success)
                        this.$router.push('/counterparty/' + this.owner)
                    } else {
                        console.error(data.error)
                    }
                }).catch(e => console.error(e))
            } else {
                axios.put(store.getters.apiRoot + 'counterparty/' + this.owner + '/contract/' + this.uuid, data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        console.log(success)
                        this.$router.push('/counterparty/' + this.owner)
                    } else {
                        console.error(data.error)
                    }
                }).catch(e => console.error(e))
            }
        },

        cancelClick() {
            this.$router.push('/counterparty/' + this.owner)
        }
    }
}
</script>
