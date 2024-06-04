<template>
    <div class="edit-form">
        <label v-if="showId()">ID:
            <input v-model="uuid" disabled />
        </label>
        <label>Код:
            <input v-model="cell.code" />
        </label>
        <label>Глубина:
            <input v-model="cell.depth" type="number" step="0.001" />
        </label>
        <label>Ширина:
            <input v-model="cell.width" type="number" step="0.001" />
        </label>
        <label>Высота:
            <input v-model="cell.height" type="number" step="0.001" />
        </label>
        <label>Макс. вес:
            <input v-model="cell.maxWeight" type="number" step="0.001" />
        </label>
        <label>Макс. объем:
            <input v-model="cell.maxVolume" type="number" step="0.001" />
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
            uuid: null,
            owner: null,
            cell: {}
        }
    },

    created() {
        this.uuid = this.$route.params.id
        this.owner = this.$route.params.ownerId

        if (this.uuid != 'new') {
            axios.get(store.getters.apiRoot + 'store/' + this.owner + '/cell/' + this.uuid, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    this.cell = success
                } else {
                    console.error(data.error)
                }
            }).catch(e => console.error(e))
        }
    },

    methods: {
        showId() {
            return this.uuid != 'new'
        }, 

        okClick() {
            const data = this.cell
            delete data.id
            delete data.ownerId

            if (this.uuid == 'new') {
                axios.post(store.getters.apiRoot + 'store/' + this.owner + '/cell', data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        console.log(success)
                        this.$router.push('/store/' + this.owner)
                    }else {
                        console.error(data.error)
                    }
                }).catch(e=>console.error(e))
            } else {
                axios.put(store.getters.apiRoot + 'store/' + this.owner + '/cell/' + this.uuid, data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        console.log(success)
                        this.$router.push('/store/' + this.owner)
                    }else {
                        console.error(data.error)
                    }
                }).catch(e=>console.error(e))
            }
        },

        cancelClick() {
            this.$router.push('/store/' + this.owner)
        }
    }
}
</script>

<style>

</style>
