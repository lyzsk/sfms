import {
    createWebHistory,
    createRouter,
    createWebHashHistory,
} from "vue-router";
import Layout from "@/layout/index.vue";

export const constantRoutes = [
    {
        path: "/redirect",
        component: Layout,
        hidden: true,
        children: [
            {
                path: "/redirect/:path(.*)",
                component: () => import("@/views/redirect/index.vue"),
            },
        ],
    },
];

const router = createRouter({
    history: createWebHashHistory(),
    routes: constantRoutes,
    scrollBehavior() {
        return {
            left: 0,
            top: 0,
        };
    },
});

export default router;
