<template>
    <div class="edit-form">
        <label v-if="showId()">ID:
            <input v-model="uuid" disabled />
        </label>
        <label>Название:
            <input v-model="printer.name" />
        </label>
        <label>Ширина страницы:
            <input v-model="printer.pageWidth" type="number" step="0.1" />
        </label>
        <label>Высота страницы:
            <input v-model="printer.pageHeight" type="number" step="0.1" />
        </label>
        <label>Колонки:
            <input v-model="printer.colCount" type="number" step="1" />
        </label>
        <label>Строки:
            <input v-model="printer.rowCount" type="number" step="1" />
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
            printer: {}
        }
    },

    created() {
        this.uuid = this.$route.params.id
        this.owner = this.$route.params.ownerId
        if (this.uuid != 'new') {
            axios.get(store.getters.apiRoot + 'print/' + this.owner + '/printer/' + this.uuid, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    this.printer = success
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
            const data = this.printer
            delete data.id
            delete data.ownerId
            if (this.uuid == 'new') {
                axios.post(store.getters.apiRoot + 'print/' + this.owner + '/printer', data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        console.log(success)
                        this.$router.push('/print/' + this.owner)
                    } else {
                        console.error(data.error)
                    }
                }).catch(e => console.error(e))
            } else {
                axios.put(store.getters.apiRoot + 'print/' + this.owner + '/printer/' + this.uuid, data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        console.log(success)
                        this.$router.push('/print/' + this.owner)
                    } else {
                        console.error(data.error)
                    }
                }).catch(e => console.error(e))
            }
        },

        cancelClick() {
            this.$router.push('/print/' + this.owner)
        }
    }
}
</script>

<style>

</style>
