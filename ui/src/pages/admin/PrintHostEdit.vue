<template>
    <div class="edit-form">
        <label v-if="showId()">ID
            <input v-model="uuid" disabled />
        </label>
        <label>Название
            <input v-model="host.name" />
        </label>
        <div v-if="showId()">
            <label class="bordered">Принтеры
                <table>
                    <tr>
                        <th rowspan="2">ID</th>
                        <th rowspan="2">Название</th>
                        <th colspan="2">Страница</th>
                        <th colspan="2">Размещение</th>
                        <th colspan="2" rowspan="2"></th>
                    </tr>
                    <tr>
                        <th>Ширина</th>
                        <th>Высота</th>
                        <th>Колонки</th>
                        <th>Строки</th>
                    </tr>
                    <tr v-for="printer in host.printers" v-bind:key="printer.id">
                        <td><IdItem v-bind:uuid="printer.id" v-bind:link_prefix="printer_prefix" /></td>
                        <td>{{ printer.name }}</td>
                        <td>{{ printer.pageWidth }}</td>
                        <td>{{ printer.pageHeight }}</td>
                        <td>{{ printer.colCount }}</td>
                        <td>{{ printer.rowCount }}</td>
                        <td><button @click="editClick" v-bind:data-item="printer.id" class="btn-document-edit" /></td>
                        <td><button @click="deleteClick" v-bind:data-item="printer.id" class="btn-edit-delete" /></td>
                    </tr>
                </table>
                <button @click="newClick" class="btn-document-new">Добавить принтер...</button>
            </label>
        </div>
        <div class="button-bar">
            <button class="btn-dialog-ok-apply" @click="okClick">OK</button>
            <button class="btn-dialog-cancel" @click="cancelClick">Cancel</button>
        </div>
    </div>
</template>

<script>
import IdItem from '@/components/IdItem.vue';
import store from '@/store';
import axios from 'axios';

export default {
    data() {
        return {
            uuid: null,
            host: { printers: [] },
            printer_prefix: null
        };
    },
    created() {
        this.uuid = this.$route.params.id;
        this.printer_prefix = "/print/" + this.uuid + "/printer";
        if (this.uuid != "new") {
            axios.get(store.getters.apiRoot + "print/" + this.uuid, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data;
                const success = data.success;
                if (success) {
                    this.host = success;
                }
                else {
                    console.error(data.error);
                }
            }).catch(e => console.error(e));
        }
    },
    
    methods: {
        showId() {
            return this.uuid != "new";
        },

        okClick() {
            const data = this.host
            delete data.id
            delete data.ownerId
            delete data.printers
            if (this.uuid == 'new') {
                axios.post(store.getters.apiRoot + 'print', data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        console.log(success)
                        this.$router.push('/print')
                    } else {
                        console.error(data.error)
                    }
                }).catch(e => console.error(e))
            } else {
                axios.put(store.getters.apiRoot + 'print/' + this.uuid, data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        console.log(success)
                        this.$router.push('/print')
                    } else {
                        console.error(data.error)
                    }
                }).catch(e => console.error(e))
            }
        },

        cancelClick() {
            this.$router.push('/print')
        },

        newClick() {
            this.$router.push(this.printer_prefix + '/new')
        },

        editClick(event) {
            const id = event.target.dataset.item
            this.$router.push(this.printer_prefix + '/' + id)
        },

        deleteClick(event) {
            const id = event.target.dataset.item
            axios.delete(store.getters.apiRoot + 'print/' + this.uuid + '/printer/' + id, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    console.log(success)
                    const filtered = []
                    this.host.printers.forEach(p => {
                        if (p.id != id) {
                            filtered.push(p)
                        }
                    })
                    this.host.printers = filtered
                } else {
                    console.error(data.error)
                }
            }).catch(e => console.error(e))
        }
    },

    components: { IdItem }
}
</script>

<style>

</style>
