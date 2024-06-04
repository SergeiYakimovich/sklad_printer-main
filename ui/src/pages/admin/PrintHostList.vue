<template>
    <div>
        <h1>Принт-серверы</h1>

        <table class="wide-list">
            <tr>
                <th>ID</th>
                <th>Название</th>
                <th colspan="2"></th>
            </tr>
            <tr v-for="host in hosts" v-bind:key="host.id">
                <td>
                    <IdItem v-bind:uuid="host.id" v-bind:link_prefix="link_prefix" />
                </td>
                <td>{{ host.name }}</td>
                <td><button @click="editClick" v-bind:data-item="host.id" class="btn-document-edit" /></td>
                <td><button @click="deleteClick" v-bind:data-item="host.id" class="btn-edit-delete" /></td>
            </tr>
        </table>
        <button @click="newClick" class="btn-document-new">Добавить принт-сервер...</button>
    </div>
</template>

<script>
import IdItem from '@/components/IdItem.vue';
import store from '@/store';
import axios from 'axios';

export default {
    data() {
        return {
            hosts: [],
            link_prefix: '/print'
        };
    },
    created() {
        axios.get(store.getters.apiRoot + "print", {
            headers: {
                Authorization: `Bearer ${store.getters.user.token}`
            }
        }).then(resp => {
            const data = resp.data;
            const success = data.success;
            if (success) {
                this.hosts = success;
            }
            else {
                console.error(data.error);
            }
        }).catch(e => console.error(e));
    },
    methods: {
        newClick() {
            this.$router.push('/print/new')
        },
        editClick(event) {
            const id = event.target.dataset.item
            this.$router.push('/print/' + id)
        },
        deleteClick(event) {
            const id = event.target.dataset.item
            axios.delete(store.getters.apiRoot + 'print/' + id, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    console.log(success)
                    const filtered = []
                    this.hosts.forEach(h => {
                        if (h.id != id) {
                            filtered.push(h)
                        }
                    })
                    this.hosts = filtered
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
