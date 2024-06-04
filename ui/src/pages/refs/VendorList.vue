<template>
    <div>
        <h1>Производители</h1>

        <table class="wide-list">
            <tr>
                <th>ID</th>
                <th>Наименование</th>
                <th colspan="2"></th>
            </tr>
            <tr v-for="vendor in vendors" v-bind:key="vendor.id">
                <td>
                    <IdItem v-bind:uuid="vendor.id" v-bind:link_prefix="link_prefix" />
                </td>
                <td>{{ vendor.name }}</td>
                <td><button @click="editClick" v-bind:data-item="vendor.id" class="btn-document-edit" /></td>
                <td><button @click="deleteClick" v-bind:data-item="vendor.id" class="btn-edit-delete" /></td>
            </tr>
        </table>
        <button @click="newClick" class="btn-document-new">Добавить производителя...</button>
    </div>
</template>

<script>
import IdItem from '@/components/IdItem.vue';
import store from '@/store';
import axios from 'axios';

export default {
    data() {
        return {
            vendors: [],
            link_prefix: '/vendor',
        };
    },

    created() {
        axios.get(store.getters.apiRoot + "vendor", {
            headers: {
                Authorization: `Bearer ${store.getters.user.token}`
            }
        }).then(resp => {
            const data = resp.data;
            const success = data.success;
            if (success) {
                this.vendors = success;
            }
            else {
                console.error(data.error);
            }
        }).catch(e => console.error(e));
    },

    methods: {
        editClick(event) {
            const id = event.target.dataset.item
            this.$router.push('/vendor/' + id)
        },

        newClick() {
            this.$router.push('/vendor/new')
        },

        deleteClick(event) {
            const id = event.target.dataset.item
            axios.delete(store.getters.apiRoot + 'vendor/' + id, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    const filtered = []
                    this.vendors.forEach(v => {
                        if (v.id != id) {
                            filtered.push(v)
                        }
                    })
                    this.vendors = filtered
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
