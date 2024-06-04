<template>
  <!-- <HelloWorld msg="Welcome to Your Vue.js App"/> -->
  <MainFrame v-if="isLogged()" />
  <LoginForm v-else />
</template>

<script>
import LoginForm from '@/components/LoginForm.vue';
import store from '@/store';
import MainFrame from '@/pages/MainFrame.vue';

export default {
  name: 'App',
  components: {
    LoginForm,
    MainFrame
},
  methods: {
    isLogged() {
      return store.getters.isLogged
    }
  }
}
</script>

<style>
* {
  box-sizing: border-box;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin: 0px;
  padding: 10px;
  position: absolute;
  left: 0px;
  min-width: 100%;
  top: 0px;
  min-height: 100%;
  display: flex;
  justify-content: center; /*Центрирование по горизонтали*/
  align-items: center;     /*Центрирование по вертикали */
}

html, body {
  margin: 0px;
  padding: 0px;
}

input, button {
  border: 1px teal solid;
  padding: 5px 10px;
}

button {
  background-color: #DEF;
  font-style: sans-serif;
  font-size: 16px;
}

.button {
  background-color: #DEF;
  border: 1px teal solid;
  padding: 7px 10px 3px;
  text-decoration: none;
  font-style: sans-serif;
  font-size: 16px;
}

.button:link, .button:visited {
  color: black;
}

table {
  margin: 10px auto;
}

td {
  border: 1px solid lightgray;
  margin: 0px;
  padding: 5px;
}

.btn-dialog-cancel::before {
  content: " ";
  width: 1em;
  height: 1em;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  display: inline-block;
  width: 1em;
  height: 1em;
  vertical-align: -12%;
  margin-right: 0.3em;
  background-image: url(./assets/img/dialog-cancel.svg);
}

.btn-dialog-ok-apply::before {
  content: " ";
  width: 1em;
  height: 1em;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  display: inline-block;
  width: 1em;
  height: 1em;
  vertical-align: -12%;
  margin-right: 0.3em;
  background-image: url(./assets/img/dialog-ok-apply.svg);
}

.btn-document-edit {
  /* padding: 0px; */
}

.btn-document-edit::before {
  content: " ";
  width: 1em;
  height: 1em;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  display: inline-block;
  width: 1em;
  height: 1em;
  vertical-align: -12%;
  /* margin-right: 0.3em; */
  background-image: url(./assets/img/document-edit.svg);
}

.btn-document-new::before {
  content: " ";
  width: 1em;
  height: 1em;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  display: inline-block;
  width: 1em;
  height: 1em;
  vertical-align: -12%;
  margin-right: 0.3em;
  background-image: url(./assets/img/document-new.svg);
}

.btn-document-preview::before {
  content: " ";
  width: 1em;
  height: 1em;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  display: inline-block;
  width: 1em;
  height: 1em;
  vertical-align: -12%;
  /* margin-right: 0.3em; */
  background-image: url(./assets/img/document-preview.svg);
}

.btn-document-revert::before {
  content: " ";
  width: 1em;
  height: 1em;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  display: inline-block;
  width: 1em;
  height: 1em;
  vertical-align: -12%;
  margin-right: 0.3em;
  background-image: url(./assets/img/document-revert.svg);
}

.btn-document-save::before {
  content: " ";
  width: 1em;
  height: 1em;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  display: inline-block;
  width: 1em;
  height: 1em;
  vertical-align: -12%;
  margin-right: 0.3em;
  background-image: url(./assets/img/document-save.svg);
}

.btn-edit-delete::before {
  content: " ";
  width: 1em;
  height: 1em;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  display: inline-block;
  width: 1em;
  height: 1em;
  vertical-align: -12%;
  /* margin-right: 0.3em; */
  background-image: url(./assets/img/edit-delete.svg);
}

.btn-list-add::before {
  content: " ";
  width: 1em;
  height: 1em;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  display: inline-block;
  width: 1em;
  height: 1em;
  vertical-align: -12%;
  /* margin-right: 0.3em; */
  background-image: url(./assets/img/list-add.svg);
}

.btn-list-remove::before {
  content: " ";
  width: 1em;
  height: 1em;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  display: inline-block;
  width: 1em;
  height: 1em;
  vertical-align: -12%;
  /* margin-right: 0.3em; */
  background-image: url(./assets/img/list-remove.svg);
}

.btn-system-lock-screen::before {
  content: " ";
  width: 1em;
  height: 1em;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  display: inline-block;
  width: 1em;
  height: 1em;
  vertical-align: -12%;
  margin-right: 0.3em;
  background-image: url(./assets/img/system-lock-screen.svg);
}

.edit-form {
    display: flex;
    flex-direction: column;
    gap: 5px;
}
</style>
