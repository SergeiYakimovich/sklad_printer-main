<template>
    <div>
        <h1>Товары</h1>

        <table>
            <tr>
                <th>ID</th>
                <th>Наименование</th>
                <th>Производитель</th>
                <th>OEM</th>
                <th>SKU</th>
                <th>Штрих-код</th>
                <th colspan="2"></th>
            </tr>
            <tr v-for="product in products" v-bind:key="product.id">
                <td>
                    <IdItem v-bind:uuid="product.id" v-bind:link_prefix="link_prefix" />
                </td>
                <td>{{ product.name }}</td>
                <td>{{ product.vendor.name }}</td>
                <td>{{ product.oemCode }}</td>
                <td>{{ product.sku }}</td>
                <td>{{ product.barcode }}</td>
                <td><button @click="editClick" v-bind:data-item="product.id" class="btn-document-edit" /></td>
                <td><button @click="deleteClick" v-bind:data-item="product.id" class="btn-edit-delete" /></td>
            </tr>
        </table>

        <button @click="newClick" class="btn-document-new">Добавить товар...</button>
    </div>
</template>

<script>
import IdItem from '@/components/IdItem.vue';
import store from '@/store';
import axios from 'axios';

export default {
    data() {
        return {
            products: [],
            link_prefix: "/product"
        };
    },

    created() {
        axios.get(store.getters.apiRoot + "product", {
            headers: {
                Authorization: `Bearer ${store.getters.user.token}`
            }
        }).then(resp => {
            const data = resp.data;
            const success = data.success;
            if (success) {
                this.products = success;
            }
            else {
                console.error(data.error);
            }
        }).catch(e => console.error(e));
    },

    methods: {
        newClick() {
            this.$router.push('/product/new')
        },

        editClick(event) {
            const id = event.target.dataset.item
            this.$router.push('/product/' + id)
        },

        deleteClick(event) {
            const id = event.target.dataset.item
            axios.delete(store.getters.apiRoot + 'product/' + id, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    const filtered = []
                    this.products.forEach(v => {
                        if (v.id != id) {
                            filtered.push(v)
                        }
                    })
                    this.products = filtered
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
