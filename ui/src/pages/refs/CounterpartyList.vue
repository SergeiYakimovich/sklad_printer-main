<template>
    <div>
        <h1>Контрагенты</h1>
        <table>
            <tr>
                <th>ID</th>
                <th>Название</th>
                <th>Полное название</th>
                <th>Правовая форма</th>
                <th>ИНН</th>
                <th>КПП</th>
                <th>ОКПО</th>
                <th>ОГРН</th>
                <th>Дата регистрации</th>
                <th>Юр. адрес</th>
                <th>Факт. адрес</th>
                <th>E-mail</th>
                <th>E-mail для документов</th>
                <th>Сайт</th>
                <th>Телефон</th>
                <th>Директор</th>
                <th>Главбух</th>
                <th colspan="2"></th>
            </tr>
            <tr v-for="counterparty in counterparties" v-bind:key="counterparty.id">
                <td>
                    <IdItem v-bind:uuid="counterparty.id" v-bind:link_prefix="link_prefix" />
                </td>
                <td>{{ counterparty.name }}</td>
                <td>{{ counterparty.fullName }}</td>
                <td>{{ counterparty.legalForm }}</td>
                <td>{{ counterparty.inn }}</td>
                <td>{{ counterparty.kpp }}</td>
                <td>{{ counterparty.okpo }}</td>
                <td>{{ counterparty.ogrn }}</td>
                <td>{{ dateText(counterparty.registration) }}</td>
                <td>{{ counterparty.addressLegal }}</td>
                <td>{{ counterparty.addressActual }}</td>
                <td>{{ counterparty.email }}</td>
                <td>{{ counterparty.emailForDoc }}</td>
                <td>{{ counterparty.site }}</td>
                <td>{{ counterparty.phone }}</td>
                <td>{{ counterparty.directorGeneral }}</td>
                <td>{{ counterparty.accountantGeneral }}</td>
                <td><button @click="editClick" v-bind:data-item="counterparty.id" class="btn-document-edit" /></td>
                <td><button @click="deleteClick" v-bind:data-item="counterparty.id" class="btn-edit-delete" /></td>
            </tr>
        </table>
        <button @click="newClick" class="btn-document-new">Добавить контагента...</button>
    </div>
</template>

<script>
import IdItem from '@/components/IdItem.vue';
import store from '@/store';
import axios from 'axios';

export default {
    data() {
        return {
            counterparties: [],
            link_prefix: "/counterparty"
        };
    },

    created() {
        axios.get(store.getters.apiRoot + "counterparty", {
            headers: {
                Authorization: `Bearer ${store.getters.user.token}`
            }
        }).then(resp => {
            const data = resp.data;
            const success = data.success;
            if (success) {
                this.counterparties = success;
                this.counterparties.forEach(c => {
                    c.registrationText = new Date(c.registration).toISOString().slice(0, 10)
                })
            }
            else {
                console.error(data.error);
            }
        }).catch(e => console.error(e));
    },

    methods: {
        newClick() {
            this.$router.push('/counterparty/new')
        },

        editClick(event) {
            const id = event.target.dataset.item
            this.$router.push('/counterparty/' + id)
        },

        deleteClick(event) {
            const id = event.target.dataset.item
            axios.delete(store.getters.apiRoot + 'counterparty/' + id, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    const filtered = []
                    this.counterparties.forEach(v => {
                        if (v.id != id) {
                            filtered.push(v)
                        }
                    })
                    this.counterparties = filtered
                } else {
                    console.error(data.error)
                }
            }).catch(e => console.error(e))
        },

        dateText(date) {
            return new Date(date).toISOString().slice(0, 10)
        }
    },

    components: { IdItem }
}
</script>

<style></style>
