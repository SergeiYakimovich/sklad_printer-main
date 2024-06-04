import { createRouter, createWebHistory } from "vue-router"
import StartPage from "@/pages/StartPage"
import AdminPage from "@/pages/admin/AdminPage"
import UserList from "@/pages/admin/UserList"
import UserEdit from "@/pages/admin/UserEdit"
import RoleList from "@/pages/admin/RoleList"
import RoleEdit from "@/pages/admin/RoleEdit"
import PrintHostList from "@/pages/admin/PrintHostList"
import PrintHostEdit from "@/pages/admin/PrintHostEdit"
import PrinterEdit from "@/pages/admin/PrinterEdit"
import VendorList from "@/pages/refs/VendorList"
import VendorEdit from "@/pages/refs/VendorEdit"
import ProductList from "@/pages/refs/ProductList"
import ProductEdit from "@/pages/refs/ProductEdit"
import StoreList from "@/pages/refs/StoreList"
import StoreEdit from "@/pages/refs/StoreEdit"
import CellEdit from "@/pages/refs/CellEdit"
import CounterpartyList from "@/pages/refs/CounterpartyList"
import CounterpartyEdit from "@/pages/refs/CounterpartyEdit"
import ContractEdit from "@/pages/refs/ContractEdit"
import BankAccountEdit from "@/pages/refs/BankAccountEdit"
import IncomingList from "@/pages/docs/IncomingList"
import IncomingEdit from "@/pages/docs/IncomingEdit"
import LayoutingList from "@/pages/docs/LayoutingList"
import LayoutingEdit from "@/pages/docs/LayoutingEdit"
import OrderingList from "@/pages/docs/OrderingList"
import OrderingEdit from "@/pages/docs/OrderingEdit"

const routes = [
    {
        path: "/",
        component: StartPage
    },
    {
        path: "/admin",
        component: AdminPage
    },
    {
        path: "/user",
        component: UserList
    },
    {
        path: "/user/:id",
        component: UserEdit
    },
    {
        path: "/role",
        component: RoleList
    },
    {
        path: "/role/:id",
        component: RoleEdit
    },
    {
        path: "/print",
        component: PrintHostList,
    },
    {
        path: "/print/:id",
        component: PrintHostEdit,
    },
    {
        path: "/print/:ownerId/printer/:id",
        component: PrinterEdit
    },
    {
        path: "/vendor",
        component: VendorList
    },
    {
        path: "/vendor/:id",
        component: VendorEdit
    },
    {
        path: "/product",
        component: ProductList
    },
    {
        path: "/product/:id",
        component: ProductEdit
    },
    {
        path: "/store",
        component: StoreList
    },
    {
        path: "/store/:id",
        component: StoreEdit
    },
    {
        path: "/store/:ownerId/cell/:id",
        component: CellEdit
    },
    {
        path: "/counterparty",
        component: CounterpartyList
    },
    {
        path: "/counterparty/:id",
        component: CounterpartyEdit
    },
    {
        path: "/counterparty/:ownerId/contract/:id",
        component: ContractEdit
    },
    {
        path: "/counterparty/:ownerId/bankaccount/:id",
        component: BankAccountEdit
    },
    {
        path: "/incoming",
        component: IncomingList
    },
    {
        path: "/incoming/:id",
        component: IncomingEdit
    },
    {
        path: "/layouting",
        component: LayoutingList
    }, 
    {
        path: "/layouting/:id",
        component: LayoutingEdit
    },
    {
        path: "/ordering",
        component: OrderingList
    }, 
    {
        path: "/ordering/:id",
        component: OrderingEdit
    },
];

const router = createRouter({
    routes,
    history: createWebHistory(process.env.BASE_URL)
});

export default router;
