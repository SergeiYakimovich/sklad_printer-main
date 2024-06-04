<template>
    <router-link v-if="link_prefix" v-bind:title="uuid" v-bind:to="url()">{{ short() }}</router-link>
    <abbr v-else v-bind:title="uuid">{{ short() }}</abbr>
</template>

<script>
export default {
    props: {
        uuid: {
            type: String,
            required: true
        },
        link_prefix: {
            type: String,
            required: false
        }
    },
    methods: {
        short() {
            return "..." + this.uuid.substring(28)
        },
        url() {
            return this.link_prefix + '/' + this.uuid
        }
    }

}
</script>

<style>
    abbr {
        cursor: help;
    }
</style>
