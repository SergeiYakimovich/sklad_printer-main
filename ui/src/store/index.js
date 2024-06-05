import { createStore } from 'vuex';

export default createStore({
    state: {
        user: null,
        roles: {},
        references: null,
        documents: null,
        reports: null,
    },

    getters: {
        user(state) {
            if (state.user){
                return state.user
            }

            if (localStorage.currentLogin) {
                try {
                    const user = JSON.parse(localStorage.currentLogin)
                    if (user.username && user.token) {
                        state.user = user
                        return user
                    } else {
                        return null
                    }
                } catch (e) {
                    console.log(e)
                    return null
                }
            } else {
                return null
            }
        },

        isLogged(state, getters) {
            return getters.user != null
        },

        apiRoot() {
//            !!!!
            return "http://localhost:8090/api/v1/"
//            return "https://sklad-printer-main.onrender.com/api/v1/"
        }
    },

    mutations: {
        setUser(state, value) {
            localStorage.currentLogin = JSON.stringify(value)
            state.user = value
        },

        setReferences(state, value) {
            state.references = value
        },
        setDocuments(state, value) {
            state.documents = value
        },
        setReports(state, value) {
            state.reports = value
        }
    },

    actions: {

    }
})
