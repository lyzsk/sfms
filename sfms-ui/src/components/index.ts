import SvgIcon from "@/components/SvgIcon/index.vue";
import Pagination from "@/components/Pagination/index.vue";
import * as ElementPlusIconsVue from "@element-plus/icons-vue";

const allGlobalComponents = { SvgIcon, Pagination };
type GlobalComponents = typeof allGlobalComponents;

export default {
    install(app: any) {
        (
            Object.keys(allGlobalComponents) as Array<keyof GlobalComponents>
        ).forEach((key) => {
            app.component(key, allGlobalComponents[key]);
        });
        for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
            app.component(key, component);
        }
    },
};
