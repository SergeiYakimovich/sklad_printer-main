<template>
    <div>
        <h1>Документы распределения</h1>
        <button @click="newClick" class="btn-document-new">Создать распределение...</button>
        <table>
            <tr>
                <th>ID</th>
                <th>Дата</th>
                <th>Номер</th>
                <th><abbr title="Флаг активности">Акт.</abbr></th>
                <th>Приход</th>
                <th>Склад</th>
                <th colspan="2"></th>
            </tr>
            <tr v-for="document in documents" v-bind:key="document.id">
                <td>
                    <IdItem v-bind:uuid="document.id" v-bind:link_prefix="link_prefix" />
                </td>
                <td>{{ dateText(document.date) }}</td>
                <td>{{ document.code }}</td>
                <td>{{ activityText(document.active) }}</td>
                <td>{{ incomingTitle(document.incomingId) }}</td>
                <td>{{ storeName(document.storeId) }}</td>
                <td><button @click="editClick" v-bind:data-item="document.id" class="btn-document-edit" /></td>
                <td><button @click="deleteClick" v-bind:data-item="document.id" class="btn-edit-delete" /></td>
            </tr>
        </table>
    </div>
</template>

<script>
import IdItem from '@/components/IdItem.vue';
import store from '@/store';
import axios from 'axios';

export default {
    data() {
        return {
            documents: [],
            link_prefix: "/layouting",
            cache: {
                incomings: [],
                stores: []
            }
        };
    },

    created() {
        axios.get(store.getters.apiRoot + 'layouting', {
            headers: {
                Authorization: `Bearer ${store.getters.user.token}`
            }
        }).then(resp => {
            const data = resp.data
            const success = data.success
            if (success) {
                this.documents = success
            } else {
                console.error(data.error)
            }
        }).catch(e => console.error(e))
    },

    methods: {
        dateText(date) {
            return new Date(date).toISOString()
        },

        activityText(active) {
            if (active) {
                return '+'
            } else {
                return ''
            }
        },

        incomingTitle(incomingId) {
            if (incomingId == null) {
                return null
            }

            if (this.cache.incomings[incomingId] == null) {
                axios.get(store.getters.apiRoot + 'incoming/' + incomingId, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        this.cache.incomings[incomingId] = '№' + success.code + ' от ' + new Date(success.date).toISOString().slice(0, 10)
                    } else {
                        console.error(data.error)
                    }
                }).catch(e => console.error(e))
            }
            return this.cache.incomings[incomingId]
        },

        storeName(storeId) {
            if (storeId == null) {
                return null
            }

            if (this.cache.stores[storeId] == null) {
                axios.get(store.getters.apiRoot + 'store/' + storeId, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        this.cache.stores[storeId] = success.name
                    } else {
                        console.error(data.error)
                    }
                }).catch(e => console.error(e))
            }
            return this.cache.stores[storeId]
        },

        newClick() {
            this.$router.push('/layouting/new')
        },

        editClick(event) {
            const id = event.target.dataset.item
            this.$router.push('/layouting/' + id)
        },

        deleteClick(event) {
            const id = event.target.dataset.item
            axios.delete(store.getters.apiRoot + 'layouting/' + id, {
                headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    const filtered = []
                    this.documents.forEach(d => {
                        if (d.id != id) {
                            filtered.push(d)
                        }
                    })
                    this.documents = filtered
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
