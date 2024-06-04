<template>
    <div>
        <h1>Список пользователей</h1>
        <table align="center">
            <tr><th>id</th><th>name</th><th>roles</th></tr><th colspan="2"></th>
            <tr v-for="item in rows" v-bind:key="item.id">
                <td><IdItem v-bind:uuid="item.id" v-bind:link_prefix="link_prefix" /></td>
                <td>{{ item.name }}</td>
                <td><RoleItem v-for="role in item.roles" v-bind:uuid="role" v-bind:key="role" /></td>
                <td><router-link class="button" v-bind:to="itemLink(item.id)">Edit...</router-link></td>
                <td><button @click="deleteItem" v-bind:data-item="item.id">Delete</button></td>
            </tr>
        </table>
        <div class="button-bar">
            <router-link class="button" to="/user/new">Добавить пользователя...</router-link>
        </div>
    </div>
</template>

<script>
import IdItem from '@/components/IdItem.vue';
import RoleItem from '@/components/RoleItem.vue';
import store from '@/store';
import axios from 'axios';

export default {
    data() {
        return {
            rows: [],
            link_prefix: "/user",
            role_prefix: "/role"
        };
    },
    created() {
        this.list();
    },
    methods: {
        list() {
            axios.get(store.getters.apiRoot + "user", {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data;
                const success = data.success;
                if (success) {
                    this.rows = success;
                }
            }).catch(e => console.log(e));
        },
        itemLink(uuid) {
            return "/user/" + uuid
        },
        deleteItem(event) {
            const deleteId = event.target.dataset.item
            axios.delete(store.getters.apiRoot + "user/" + deleteId, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    const filtered = []
                    this.rows.forEach(i => {
                        console.log({i})
                        if (i.id != deleteId)
                            filtered.push(i)
                    })
                    console.log({old: this.rows, filtered})
                    this.rows = filtered
                } else {
                    console.error(data.error)
                }
            }).catch(e => console.error(e))
        }
    },
    components: { IdItem, RoleItem }
}
</script>

<style>
.role-item {
    margin: 0px 3px;
}
</style>