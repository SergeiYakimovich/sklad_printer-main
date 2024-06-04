<template>
    <div class="edit-form">
        <label v-if="showId()">ID
            <input v-model="uuid" disabled />
        </label>
        <label>Название
            <input v-model="store.name" />
        </label>
        <label class="bordered">Ячейки
        <table>
            <tr>
                <th>ID</th>
                <th>Код</th>
                <th>Глубина</th>
                <th>Ширина</th>
                <th>Высота</th>
                <th>Макс. вес</th>
                <th>Макс. объем</th>
                <th colspan="2"></th>
            </tr>
            <tr v-for="cell in store.cells" v-bind:key="cell.id">
                <td><IdItem v-bind:uuid="cell.id" v-bind:link_prefix="cell_prefix" /></td>
                <td>{{ cell.code }}</td>
                <td>{{ cell.depth }}</td>
                <td>{{ cell.width }}</td>
                <td>{{ cell.height }}</td>
                <td>{{ cell.maxWeight }}</td>
                <td>{{ cell.maxVolume }}</td>
                <td><button @click="editClick" v-bind:data-item="cell.id" class="btn-document-edit" /></td>
                <td><button @click="deleteClick" v-bind:data-item="cell.id" class="btn-edit-delete" /></td>
            </tr>
        </table>
        <button @click="newClick" class="btn-document-new">Добавить ячейку...</button>
        </label>
        <div class="button-bar">
            <button class="btn-dialog-ok-apply" @click="okClick">OK</button>
            <button class="btn-dialog-cancel" @click="cancelClick">Cancel</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import store from '@/store'
import IdItem from '@/components/IdItem.vue'

export default {
    data() {
        return {
            uuid: null,
            store: { cells: [] },
            cell_prefix: null,
        };
    },

    created() {
        this.uuid = this.$route.params.id;
        this.cell_prefix = '/store/' + this.uuid + '/cell'
        if (this.uuid != "new") {
            axios.get(store.getters.apiRoot + "store/" + this.uuid, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data;
                const success = data.success;
                if (success) {
                    this.store = success;
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
            const data = this.store;
            delete data.id;
            delete data.cells
            if (this.uuid == "new") {
                axios.post(store.getters.apiRoot + "store", data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data;
                    const success = data.success;
                    if (success) {
                        console.log(success);
                        this.$router.push("/store");
                    }
                    else {
                        console.error(data.error);
                    }
                }).catch(e => console.error(e));
            }
            else {
                axios.put(store.getters.apiRoot + "store/" + this.uuid, data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data;
                    const success = data.success;
                    if (success) {
                        console.log(success);
                        this.$router.push("/store");
                    }
                    else {
                        console.error(data.error);
                    }
                }).catch(e => console.error(e));
            }
        },

        cancelClick() {
            this.$router.push("/store");
        },

        newClick() {
            this.$router.push(this.cell_prefix + '/new')
        },

        editClick(event) {
            this.$router.push(this.cell_prefix + '/' + event.target.dataset.item)
        },

        deleteClick(event) {
            const cellId = event.target.dataset.item
            axios.delete(store.getters.apiRoot + 'store/' + this.uuid + '/cell/' + cellId, {
                headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    console.log(success)
                    const filtered = []
                    this.store.cells.forEach(c => {
                        if (c.id != cellId) {
                            filtered.push(c)
                        }
                    })
                    this.store.cells = filtered
                } else {
                    console.error(data.error)
                }
            }).catch(e=> console.error(e))
        }
    },
    components: { IdItem }
}
</script>

<style>

</style>

