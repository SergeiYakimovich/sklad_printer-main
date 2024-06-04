<template>
    <div class="main-frame">
        <ul id="navbar">
            <li><router-link to="/">Home</router-link></li>
            <li><router-link to="/admin">Admin</router-link></li>
            <li><button class="btn-system-lock-screen" @click="logOut">Logout</button></li>
        </ul>
        <router-view class="router-view"></router-view>
        <div class="main-footer">&nbsp;</div>
    </div>
</template>

<script>
import router from '@/router/router';
import store from '@/store';

export default {
    data() {
        return {

        }
    },

    methods: {
        logOut() {
            localStorage.currentLogin = null
            store.commit('setUser', null)
            router.push('/')
        }
    }
}
</script>

<style>
#navbar {
    list-style-type: none;
    flex-shrink: 0;
}

#navbar > li {
    display: inline-block;
    padding: 5px;
    height: 50px;
}

.main-frame {
    min-width: 100%;
    height: 100%;
    flex-shrink: 1;
    flex-grow: 1;
}

.router-view {
    min-height: 100%;
    flex: 1 0 auto;
    flex-grow: 1;
}

.main-footer {
    flex-shrink: 0;
}
</style>