<template>
    <router-link class="role-item" v-bind:title="uuid" v-bind:to="link()">{{ role.name }}</router-link>
</template>

<script>
import store from '@/store'
import axios from 'axios'

export default {
    props: {
        uuid: {
            type: String,
            required: true
        }
    },

    data() {
        return {
            role: {}
        }
    }, 

    created() {
        const cached = store.state.roles[this.uuid]
        if (cached == null) {
            axios.get(store.getters.apiRoot + "role/" + this.uuid, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    store.state.roles[this.uuid] = success
                    this.role = success
                } else {
                    const error = data.error
                    console.error(error)
                }
            }).catch(e => console.error(e))
        } else {
            this.role = cached
        }
    },

    methods: {
        link() {
            return "/role/" + this.uuid
        },
    }
}
</script>
