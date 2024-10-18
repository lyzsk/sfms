import { createApp } from "vue";
import App from "./App.vue";
import setupPlugins from "@/plugins";

// 本地SVG图标
import "virtual:svg-icons-register";

// 样式
import "element-plus/theme-chalk/dark/css-vars.css";
import "@/styles/index.scss";
import "uno.css";
import "animate.css";

// 粒子特效
import Particles from "@tsparticles/vue3";
import { loadFull } from "tsparticles";

const app = createApp(App);
// 注册插件
app.use(Particles, {
    init: async (engine) => {
        await loadFull(engine);
    },
});
app.use(setupPlugins);
app.mount("#app");
