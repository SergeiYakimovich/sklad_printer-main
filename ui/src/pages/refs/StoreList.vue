<template>
    <div>
        <h1>Склады</h1>
        <table>
            <tr>
                <th>ID</th>
                <th>Название</th>
                <th colspan="2"></th>
            </tr>
            <tr v-for="store in stores" v-bind:key="store.id">
                <td><IdItem v-bind:uuid="store.id" v-bind:link_prefix="link_prefix" /></td>
                <td>{{ store.name }}</td>
                <td><button @click="editClick" v-bind:data-item="store.id" class="btn-document-edit" /></td>
                <td><button @click="deleteClick" v-bind:data-item="store.id" class="btn-edit-delete" /></td>
            </tr>
        </table>
        <button @click="newClick" class="btn-document-new">Добавить склад...</button>
    </div>
</template>

<script>
import IdItem from '@/components/IdItem.vue';
import store from '@/store';
import axios from 'axios';

export default {
    data() {
        return {
            stores: [],
            link_prefix: '/store'
        };
    },

    created() {
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
    },

    methods: {
        newClick() {
            this.$router.push('/store/new')
        },

        editClick(event) {
            const id = event.target.dataset.item
            this.$router.push('/store/' + id)
        },

        deleteClick(event) {
            const id = event.target.dataset.item
            axios.delete(store.getters.apiRoot + 'store/' + id, {
                headers: {
                Authorization: `Bearer ${store.getters.user.token}`
            }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    console.log(success)
                    const filtered = []
                    this.stores.forEach(s => {
                        if (s.id != id) {
                            filtered.push(s)
                        }
                    })
                    this.stores = filtered
                } else {
                    console.error(data.error)
                }
            }).catch(e => console.error(e))
        },
    },

    components: { IdItem }
}
</script>

<style>

</style>
