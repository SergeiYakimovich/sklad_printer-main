<template>
    <div>
        <h1>Роли / группы</h1>
        <p class="subtitle">Управление правами доступа</p>


        <table class="wide-list">
            <tr>
                <th>id</th><th>Название</th><th>Объекты</th>
                <th colspan="2"></th>
            </tr>
            <tr v-for="role in roles" v-bind:key="role.id">
                <td><IdItem v-bind:uuid="role.id" v-bind:link_prefix="link_prefix"></IdItem></td>
                <td>{{ role.name }}</td>
                <td>
                    <table class="inner">
                        <tr>
                            <th>Справочник</th>
                            <th><abbr title="Добавление элемента">C</abbr></th>
                            <th><abbr title="Чтение элемента">R</abbr></th>
                            <th><abbr title="Изменение элемента">U</abbr></th>
                            <th><abbr title="Удаление элемента">D</abbr></th>
                            <th><abbr title="Чтение списка">L</abbr></th>
                        </tr>
                        <tr v-for="reference in references" v-bind:key="reference">
                            <td>{{ reference }}</td>
                            <td>{{ marker(refPerm(role, reference).allowCreate) }}</td>
                            <td>{{ marker(refPerm(role, reference).allowRead) }}</td>
                            <td>{{ marker(refPerm(role, reference).allowUpdate) }}</td>
                            <td>{{ marker(refPerm(role, reference).allowDelete) }}</td>
                            <td>{{ marker(refPerm(role, reference).allowList) }}</td>
                        </tr>
                    </table>

                    <table class="inner">
                        <tr>
                            <th>Документ</th>
                            <th><abbr title="Добавление документа">C</abbr></th>
                            <th><abbr title="Чтение документа">R</abbr></th>
                            <th><abbr title="Изменение документа">U</abbr></th>
                            <th><abbr title="Удаление документа">D</abbr></th>
                            <th><abbr title="Чтение списка">L</abbr></th>
                            <th><abbr title="Активация / проведение документа">A</abbr></th>
                        </tr>
                        <tr v-for="document in documents" v-bind:key="document">
                            <td>{{ document }}</td>
                            <td>{{ marker(docPerm(role, document).allowCreate) }}</td>
                            <td>{{ marker(docPerm(role, document).allowRead) }}</td>
                            <td>{{ marker(docPerm(role, document).allowUpdate) }}</td>
                            <td>{{ marker(docPerm(role, document).allowDelete) }}</td>
                            <td>{{ marker(docPerm(role, document).allowList) }}</td>
                            <td>{{ marker(docPerm(role, document).allowActivate) }}</td>
                        </tr>
                    </table>
                </td>
                <td><button @click="editClick" v-bind:data-item="role.id" class="btn-document-edit" /></td>
                <td><button @click="deleteClick" v-bind:data-item="role.id" class="btn-edit-delete" /></td>
            </tr>
        </table>
        <div class="button-bar">
            <button @click="newClick" class="btn-document-new">Добавить роль / группу...</button>
        </div>
    </div>
</template>

<script>
import store from '@/store';
import axios from 'axios';
import IdItem from '@/components/IdItem.vue';

export default {
    data() {
        return {
            references: [],
            documents: [],
            roles: [],
            link_prefix: '/role'
        };
    },
    created() {
        this.fetchEnums();
        this.fetchRoles();
    },
    methods: {
        refPerm(role, refname) {
            const result = role.references.find(i => i.entity == refname);
            if (result)
                return result;
            else
                return {};
        },
        docPerm(role, docname) {
            const result = role.documents.find(i => i.entity == docname);
            if (result)
                return result;
            else
                return {};
        },
        marker(value) {
            if (value)
                return "+";
            else if (value === false)
                return "-";
            else
                return " ";
        },
        fetchEnums() {
            axios.get(store.getters.apiRoot + "enums/permissions/references", {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data;
                const success = data.success;
                if (success) {
                    this.references = success;
                }
            }).catch(e => console.error(e));
            axios.get(store.getters.apiRoot + "enums/permissions/documents", {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data;
                const success = data.success;
                if (success) {
                    this.documents = success;
                }
            }).catch(e => console.error(e));
        },
        fetchRoles() {
            axios.get(store.getters.apiRoot + "role", {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data;
                const success = data.success;
                if (success) {
                    this.roles = success;
                }
            }).catch(e => console.error(e));
        },
        itemLink(uuid) {
            return '/role/' + uuid
        },
        deleteClick(event) {
            const deleteId = event.target.dataset.item
            axios.delete(store.getters.apiRoot + "role/" + deleteId, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    const filtered = []
                    this.roles.forEach(i => {
                        console.log({i})
                        if (i.id != deleteId)
                            filtered.push(i)
                    })
                    console.log({old: this.roles, filtered})
                    this.roles = filtered
                } else {
                    console.error(data.error)
                }
            }).catch(e => console.error(e))
        },
        editClick(event) {
            const id = event.target.dataset.item
            this.$router.push('/role/' + id)
        },
        newClick() {
            this.$router.push('/role/new')
        },
    },
    components: { IdItem }
}
</script>

<style>
.wide-list {
    margin: 5px auto;
}
td {
    padding: 5px;
    vertical-align: top;
}

.wide-list td {
    border: solid 1px;
}

.inner {
    width: 100%;
}
.inner td {
    border: dotted 1px;
}
</style>
