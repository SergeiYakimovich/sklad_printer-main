<template>
    <div class="edit-form">
        <h1>Пользователь</h1>
        <label v-if="showId()">ID:
            <input v-model="uuid" disabled placeholder="id">
        </label>
        <label>Имя:
            <input v-model="user.name">
        </label>
        <label>Пароль:
            <input v-model="password" type="password" placeholder="password">
        </label>
        <div>
            <label>Роли / группы</label>
            <table align="center">
                <tr v-for="role in user.roles" v-bind:key="role">
                    <td>
                        <RoleItem v-bind:uuid="role" />
                    </td>
                    <td><button @click="deleteRole" v-bind:data-role="role">Delete</button></td>
                </tr>
            </table>
            <select v-model="selectedRole">
                <option v-for="role in availableRoles" v-bind:value="role.id" v-bind:key="role.id">{{ role.name }}</option>
            </select>
            <button @click="add">Add</button>
        </div>
        <div class="button-bar">
            <button class="btn-dialog-ok-apply" @click="okClick">OK</button>
            <button class="btn-dialog-cancel" @click="cancelClick">Cancel</button>
        </div>
    </div>
</template>

<script>
import RoleItem from '@/components/RoleItem.vue';
import store from '@/store';
import axios from 'axios';

export default {
    data() {
        return {
            uuid: null,
            user: { roles: [] },
            password: null,
            availableRoles: [],
            selectedRole: null,
            prevPage: null,
        };
    },

    created() {
        this.uuid = this.$route.params.id;
        if (this.uuid != 'new') {
            axios.get(store.getters.apiRoot + "user/" + this.uuid, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data;
                const success = data.success;
                if (success) {
                    this.user = success;
                }
                else {
                    console.error(data.error);
                }
            }).catch(e => console.error(e));
        }

        axios.get(store.getters.apiRoot + "role", {
            headers: {
                Authorization: `Bearer ${store.getters.user.token}`
            }
        }).then(resp => {
            const data = resp.data
            const success = data.success
            if (success) {
                this.availableRoles = success
            } else {
                console.error(data.error)
            }
        }).catch(e => console.error(e))

        this.prevPage = this.$router.options.history.state.back
    },

    methods: {
        showId() {
            return this.uuid != 'new'
        },

        add() {
            if (this.selectedRole != null && !this.user.roles.includes(this.selectedRole))
                this.user.roles.push(this.selectedRole)
        },

        deleteRole(event) {
            this.user.roles = this.user.roles.filter(i => (i != event.target.dataset.role))
        },

        okClick() {
            const data = this.user
            delete data.id
            data.password = this.password
            if (this.uuid == 'new') {
                axios.post(store.getters.apiRoot + "user", data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    this.$router.push(this.prevPage != null ? this.prevPage : '/user')
                    console.log(resp.data)
                }).catch(e => console.error(e))
            } else {
                axios.put(store.getters.apiRoot + "user/" + this.uuid, data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    this.$router.push(this.prevPage != null ? this.prevPage : '/user')
                    console.log(resp.data)
                }).catch(e => console.error(e))
            }
        },

        cancelClick() {
            this.$router.push(this.prevPage != null ? this.prevPage : '/user')
        }
    },

    components: { RoleItem }
}
</script>

<style>
</style>