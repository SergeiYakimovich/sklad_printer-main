<template>
    <div class="edit-form">
        <label v-if="showId()">ID:
            <input v-model="uuid" disabled placeholder="id" />
        </label>
        <label>Наименование:
            <input v-model="product.name" placeholder="Наименование" />
        </label>
        <label>Производитель:
            <select v-model="product.vendorId">
                <option v-for="vendor in vendors" v-bind:key="vendor.id" v-bind:value="vendor.id">{{ vendor.name }}</option>
            </select>
        </label>
        <label>OEM:
            <input v-model="product.oemCode" placeholder="OEM" />
        </label>
        <label>SKU:
            <input v-model="product.sku" placeholder="SKU" />
        </label>
        <label>Штрих-код:
            <input v-model="product.barcode" placeholder="Штрих-код" />
        </label>
        <div class="button-bar">
            <button class="btn-dialog-ok-apply" @click="okClick">OK</button>
            <button class="btn-dialog-cancel" @click="cancelClick">Cancel</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import store from '@/store';

export default {
    data() {
        return {
            uuid: null,
            product: {},
            vendors: [],
        }
    },

    created() {
        this.uuid = this.$route.params.id;
        if (this.uuid != 'new') {
            axios.get(store.getters.apiRoot + 'product/' + this.uuid, {
                headers: {
                    Authorization: `Bearer ${store.getters.user.token}`
                }
            }).then(resp => {
                const data = resp.data
                const success = data.success
                if (success) {
                    this.product = success
                    this.product.vendorId = this.product.vendor.id
                } else {
                    console.error(data.error)
                }
            }).catch(e => console.error(e))
        }

        axios.get(store.getters.apiRoot + 'vendor', {
            headers: {
                Authorization: `Bearer ${store.getters.user.token}`
            }
        }).then(resp => {
            const data = resp.data
            const success = data.success
            if (success) {
                this.vendors = success
            } else {
                console.error(data.error)
            }
        }).catch(e => console.error(e))
    },

    methods: {
        showId() {
            return this.uuid != 'new'
        },

        okClick() {
            const data = this.product
            delete data.id
            delete data.vendor
            if (this.uuid == 'new') {
                axios.post(store.getters.apiRoot + 'product', data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        console.log(success)
                        this.$router.push('/product')
                    } else {
                        console.error(data.error)
                    }
                }).catch(e => console.error(e))
            } else {
                axios.put(store.getters.apiRoot + 'product/' + this.uuid, data, {
                    headers: {
                        Authorization: `Bearer ${store.getters.user.token}`
                    }
                }).then(resp => {
                    const data = resp.data
                    const success = data.success
                    if (success) {
                        console.log(success)
                        this.$router.push('/product')
                    } else {
                        console.error(data.error)
                    }
                }).catch(e => console.error(e))
            }
        },

        cancelClick() {
            this.$router.push('/product')
        }
    }
}
</script>
