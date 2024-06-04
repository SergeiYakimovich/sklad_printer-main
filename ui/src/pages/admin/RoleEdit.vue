<template>
    <div class="edit-form">
        <h1>Группа / роль</h1>
        <label v-if="showId()">ID:
            <input v-model="uuid" disabled placeholder="id" />
        </label>
        <label>Название:
            <input v-model="role.name" placeholder="Название" />
        </label>

        <table>
            <tr>
                <th>Справочник</th>
                <th>Создание</th>
                <th>Чтение</th>
                <th>Изменение</th>
                <th>Удаление</th>
                <th>Список</th>
            </tr>
            <tr v-for="reference in references" v-bind:key="reference">
                <td>{{ reference }}</td>
                <td>
                    <select v-model="rights.references[reference].allowCreate">
                        <option value="none">---</option>
                        <option value="allow">Allow</option>
                        <option value="deny">Deny</option>
                    </select>
                </td>
                <td>
                    <select v-model="rights.references[reference].allowRead">
                        <option value="none">---</option>
                        <option value="allow">Allow</option>
                        <option value="deny">Deny</option>
                    </select>
                </td>
                <td>
                    <select v-model="rights.references[reference].allowUpdate">
                        <option value="none">---</option>
                        <option value="allow">Allow</option>
                        <option value="deny">Deny</option>
                    </select>
                </td>
                <td>
                    <select v-model="rights.references[reference].allowDelete">
                        <option value="none">---</option>
                        <option value="allow">Allow</option>
                        <option value="deny">Deny</option>
                    </select>
                </td>
                <td>
                    <select v-model="rights.references[reference].allowList">
                        <option value="none">---</option>
                        <option value="allow">Allow</option>
                        <option value="deny">Deny</option>
                    </select>
                </td>
            </tr>
        </table>

        <table>
            <tr>
                <th>Документ</th>
                <th>Создание</th>
                <th>Чтение</th>
                <th>Изменение</th>
                <th>Удаление</th>
                <th>Список</th>
                <th>Активация</th>
            </tr>
            <tr v-for="document in documents" v-bind:key="document">
                <td>{{ document }}</td>
                <td>
                    <select v-model="rights.documents[document].allowCreate">
                        <option value="none">---</option>
                        <option value="allow">Allow</option>
                        <option value="deny">Deny</option>
                    </select>
                </td>
                <td>
                    <select v-model="rights.documents[document].allowRead">
                        <option value="none">---</option>
                        <option value="allow">Allow</option>
                        <option value="deny">Deny</option>
                    </select>
                </td>
                <td>
                    <select v-model="rights.documents[document].allowUpdate">
                        <option value="none">---</option>
                        <option value="allow">Allow</option>
                        <option value="deny">Deny</option>
                    </select>
                </td>
                <td>
                    <select v-model="rights.documents[document].allowDelete">
                        <option value="none">---</option>
                        <option value="allow">Allow</option>
                        <option value="deny">Deny</option>
                    </select>
                </td>
                <td>
                    <select v-model="rights.documents[document].allowList">
                        <option value="none">---</option>
                        <option value="allow">Allow</option>
                        <option value="deny">Deny</option>
                    </select>
                </td>
                <td>
                    <select v-model="rights.documents[document].allowActivate">
                        <option value="none">---</option>
                        <option value="allow">Allow</option>
                        <option value="deny">Deny</option>
                    </select>
                </td>
            </tr>
        </table>

        <table>
            <tr>
                <th>Отчет</th>
                <th>Использование</th>
            </tr>
            <tr v-for="report in reports" v-bind:key="report">
                <td>{{ report }}</td>
                <td>
                    <select v-model="rights.reports[report].allowUse">
                        <option value="none">---</option>
                        <option value="allow">Allow</option>
                        <option value="deny">Deny</option>
                    </select>
                </td>
            </tr>
        </table>

        <div class="button-bar">
            <button class="btn-dialog-ok-apply" @click="okClick">OK</button>
            <button class="btn-dialog-cancel" @click="cancelClick">Cancel</button>
        </div>
    </div>
</template>

<script>
import store from '@/store';
import axios from 'axios';

export default {
    data() {
        return {
            uuid: null,
            role: {},
            references: [],
            documents: [],
            reports: [],
            rights: {
                references: {},
                documents: {},
                reports: {},
            }
        };
    },

    created() {
        const b2v = (bVal) => { return bVal ? "allow" : (bVal === false ? "deny" : "none"); };
        this.uuid = this.$route.params.id;
        if (this.uuid != "new") {
            axios.get(store.getters.apiRoot + "role/" + this.uuid, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data;
                const success = data.success;
                if (success) {
                    this.role = success;
                    this.role.references.forEach(perms => {
                        const value = {
                            allowCreate: b2v(perms.allowCreate),
                            allowRead: b2v(perms.allowRead),
                            allowUpdate: b2v(perms.allowUpdate),
                            allowDelete: b2v(perms.allowDelete),
                            allowList: b2v(perms.allowList)
                        };
                        this.rights.references[perms.entity] = value;
                    });
                    this.role.documents.forEach(perms => {
                        const value = {
                            allowCreate: b2v(perms.allowCreate),
                            allowRead: b2v(perms.allowRead),
                            allowUpdate: b2v(perms.allowUpdate),
                            allowDelete: b2v(perms.allowDelete),
                            allowList: b2v(perms.allowList),
                            allowActivate: b2v(perms.allowActivate)
                        }
                        this.rights.documents[perms.entity] = value
                    })
                    this.role.reports.forEach(perms => {
                        const value = {
                            allowUse: b2v(perms.allowUse)
                        }
                        this.rights.reports[perms.report] = value
                    })
                }
                else {
                    console.error(data.error);
                }
            }).catch(e => console.error(e));
        }
        const cachedReferences = store.state.references;
        if (cachedReferences) {
            this.references = cachedReferences;
            this.references.forEach(entity => {
                if (!this.rights.references[entity]) {
                    this.rights.references[entity] = {
                        allowCreate: 'none',
                        allowRead: 'none',
                        allowUpdate: 'none',
                        allowDelete: 'none',
                        allowList: 'none'
                    }
                }
            })
        }
        else {
            axios.get(store.getters.apiRoot + "enums/permissions/references", {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data;
                const success = data.success;
                if (success) {
                    this.references = success
                    this.references.forEach(entity => {
                        if (!this.rights.references[entity]) {
                            this.rights.references[entity] = {
                                allowCreate: 'none',
                                allowRead: 'none',
                                allowUpdate: 'none',
                                allowDelete: 'none',
                                allowList: 'none'
                            }
                        }
                    })
                    store.commit("setReferences", success);
                }
                else {
                    console.error(data.error);
                }
            }).catch(e => console.error(e));
        }

        const cachedDocuments = store.state.documents;
        if (cachedDocuments) {
            this.documents = cachedDocuments;
            this.documents.forEach(entity => {
                if (!this.rights.documents[entity]) {
                    this.rights.documents[entity] = {
                        allowCreate: 'none',
                        allowRead: 'none',
                        allowUpdate: 'none',
                        allowDelete: 'none',
                        allowList: 'none',
                        allowActivate: 'none',
                    }
                }
            })
        }
        else {
            axios.get(store.getters.apiRoot + "enums/permissions/documents", {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data;
                const success = data.success;
                if (success) {
                    this.documents = success;
                    this.documents.forEach(entity => {
                        if (!this.rights.documents[entity]) {
                            this.rights.documents[entity] = {
                                allowCreate: 'none',
                                allowRead: 'none',
                                allowUpdate: 'none',
                                allowDelete: 'none',
                                allowList: 'none',
                                allowActivate: 'none',
                            }
                        }
                    })
                    store.commit("setDocuments", success);
                }
                else {
                    console.error(data.error);
                }
            }).catch(e => console.error(e));
        }
        const cachedReports = store.state.reports;
        if (cachedReports) {
            this.reports = cachedReports;
            this.reports.forEach(report => {
                if (!this.rights.reports[report]) {
                    this.rights.reports[report] = {
                        allowUse: 'none'
                    }
                }
            })
        }
        else {
            axios.get(store.getters.apiRoot + "enums/permissions/reports", {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data;
                const success = data.success;
                if (success) {
                    this.reports = success;
                    this.reports.forEach(report => {
                        if (!this.rights.reports[report]) {
                            this.rights.reports[report] = {
                                allowUse: 'none'
                            }
                        }
                    })
                    store.commit("setReports", success);
                }
                else {
                    console.error(data.error);
                }
            }).catch(e => console.error(e));
        }
    },

    methods: {
        showId() {
            return this.uuid != 'new'
        },

        okClick() {
            const v2b = (v) => { return v === 'allow' ? true : (v === 'deny' ? false : null) }

            const data = this.role
            delete data.id

            const refs = []
            Object.entries(this.rights.references).forEach(entry => {
                const [key, value] = entry
                if (value.allowCreate != 'none' || value.allowRead != 'none' || value.allowUpdate != 'none' || value.allowDelete != 'none' || value.allowList != 'none') {
                    refs.push({
                        entity: key, 
                        allowCreate: v2b(value.allowCreate),
                        allowRead: v2b(value.allowRead),
                        allowUpdate: v2b(value.allowUpdate),
                        allowDelete: v2b(value.allowDelete),
                        allowList: v2b(value.allowList)
                    })
                }
            })
            data.references = refs

            const docs = []
            Object.entries(this.rights.documents).forEach(entry => {
                const [key, value] = entry
                if (value.allowCreate != 'none' || value.allowRead != 'none' || value.allowUpdate != 'none' || value.allowDelete != 'none' || value.allowList != 'none' || value.allowActivate != 'none') {
                    docs.push({
                        entity: key, 
                        allowCreate: v2b(value.allowCreate),
                        allowRead: v2b(value.allowRead),
                        allowUpdate: v2b(value.allowUpdate),
                        allowDelete: v2b(value.allowDelete),
                        allowList: v2b(value.allowList),
                        allowActivate: v2b(value.allowActivate)
                    })
                }
            })
            data.documents = docs

            const reps = []
            Object.entries(this.rights.reports).forEach(entry => {
                const [key, value] = entry
                if (value.allowUse != 'none') {
                    reps.push({
                        report: key, 
                        allowUse: v2b(value.allowUse)
                    })
                }
            })
            data.reports = reps

            if (this.uuid == 'new') {
                axios.post(store.getters.apiRoot + "role", data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    this.$router.push(this.prevPage != null ? this.prevPage : '/role')
                    console.log(resp.data)
                }).catch(e => console.error(e))
            } else {
                axios.put(store.getters.apiRoot + "role/" + this.uuid, data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    this.$router.push(this.prevPage != null ? this.prevPage : '/role')
                    console.log(resp.data)
                }).catch(e => console.error(e))
            }
        },

        cancelClick() {
            this.$router.push(this.prevPage != null ? this.prevPage : '/role')
        }
    },
    components: {}
}
</script>

<style></style>
