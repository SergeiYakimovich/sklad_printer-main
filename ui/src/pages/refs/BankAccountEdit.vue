<template>
    <div class="edit-form">
        <label v-if="showId()">ID:
            <input v-model="uuid" disabled />
        </label>
        <label>Название:
            <input v-model="account.name" />
        </label>
        <label>Банк:
            <input v-model="account.bank" />
        </label>
        <label>БИК:
            <input v-model="account.bik" />
        </label>
        <label>Корр. счет:
            <input v-model="account.correspondentAcc" />
        </label>
        <label>Номер счета:
            <input v-model="account.accountNumber" />
        </label>
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
            owner: null,
            account: {},
        }
    },

    created() {
        this.uuid = this.$route.params.id
        this.owner = this.$route.params.ownerId
        if (this.uuid != 'new') {
            axios.get(store.getters.apiRoot + 'counterparty/' + this.owner + '/bankaccount/' + this.uuid, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    this.account = success
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
            const data = this.account
            delete data.id
            delete data.ownerId
            if (this.uuid == 'new') {
                axios.post(store.getters.apiRoot + 'counterparty/' + this.owner + '/bankaccount', data, {
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
                axios.put(store.getters.apiRoot + 'counterparty/' + this.owner + '/bankaccount/' + this.uuid, data, {
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
