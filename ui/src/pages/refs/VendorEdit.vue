<template>
    <div class="edit-form">
        <label v-if="showId()">ID
            <input v-model="uuid" disabled placeholder="id" />
        </label>
        <label>Название
            <input v-model="vendor.name" placeholder="Название" />
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
            vendor: {},
            prevPage: null
        }
    },

    created() {
        this.uuid = this.$route.params.id;
        if (this.uuid != 'new') {
            axios.get(store.getters.apiRoot + 'vendor/' + this.uuid, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    this.vendor = success
                } else {
                    console.error(data.error)
                }
            }).catch(e => console.error(e))
        }

        this.prevPage = this.$router.options.history.state.back
    },

    methods: {
        showId() {
            return this.uuid != 'new'
        },

        okClick() {
            const data = this.vendor
            delete data.id
            if (this.uuid != 'new') {
                axios.put(store.getters.apiRoot + 'vendor/' + this.uuid, data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        console.log(success)
                    } else {
                        console.error(data.error)
                    }
                    this.$router.push(this.prevPage != null ? this.prevPage : '/vendor')
                }).catch(e => console.error(e))
            } else {
                axios.post(store.getters.apiRoot + 'vendor', data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        console.log(success)
                    } else {
                        console.error(data.error)
                    }
                    this.$router.push(this.prevPage != null ? this.prevPage : '/vendor')
                }).catch(e => console.error(e))
            }
        },

        cancelClick() {
            this.$router.push(this.prevPage != null ? this.prevPage : '/vendor')
        }
    }
}
</script>