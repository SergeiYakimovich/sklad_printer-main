<template>
    <div>
        <div class="centered">
            <h1>Список доступных страниц</h1>

            <ul>
                <li>
                    <router-link to="/admin"><b>Администрирование</b></router-link>
                    <ul>
                        <li><router-link to="/user">Пользователи</router-link></li>
                        <li><router-link to="/role">Роли / группы</router-link></li>
                        <li><router-link to="/print">Печать</router-link></li>
                    </ul>
                </li>
                <li>
                    <b>Справочники</b>
                    <ul>
                        <li>
                            <router-link to="/product"><b>Товары</b></router-link>
                            <ul>
                                <li>
                                    <router-link to="/vendor">Производители</router-link>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <router-link to="/counterparty"><b>Котрагенты</b></router-link>
                        </li>
                        <li>
                            <router-link to="/store"><b>Склады</b></router-link>
                        </li>
                    </ul>
                </li>
                <li>
                    <b>Документы</b>
                    <ul>
                        <li><router-link to="/incoming"><b>Приход</b></router-link></li>
                        <li><router-link to="/layouting"><b>Распределение</b></router-link></li>
                        <li><router-link to="/ordering"><b>Заказ</b></router-link></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</template>

<style>
.centered {
    margin-left: auto;
    margin-right: auto;
    max-width: 800px;
}

.centered ul {
    text-align: left;
}
</style>