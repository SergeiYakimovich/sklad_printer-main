<template>
    <div class="login-form">
        <input v-model="username" type="text" placeholder="login">
        <input v-model="password" type="password" placeholder="password">
        <button class="btn-dialog-ok-apply" @click="logIn">Войти</button>
    </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
    data() {
        return {
            username: "",
            password: ""
        }
    },
    methods: {
        async logIn(event) {
            console.log(event);
            const rq = {
                username: this.username,
                password: this.password,
            }
            axios.post(store.getters.apiRoot + "login", rq).then(resp => { 
                const data = resp.data
                const success = data.success
                const token = success.token
                const user = {
                    username: this.username,
                    token: token
                }
                store.commit('setUser', user)
            }).catch(e => console.log({e:e}))
        }
    },
}
</script>

<style>
.login-form {
    display: flex;
    flex-direction: column;
    gap: 5px;
}

.login-form input, .login-form button {
    font-size: 146%;
}
</style>