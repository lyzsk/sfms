import { createApp } from "vue";
import App from "./App.vue";

import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import zhCn from "element-plus/es/locale/lang/zh-cn";
import "virtual:svg-icons-register";

import globalComponent from "./components/index";
import router from "./router/index";
import pinia from "./store/index";
// import "./permission";

const app = createApp(App);

app.use(ElementPlus, {
    locale: zhCn,
});
app.use(globalComponent);
app.use(pinia);
app.use(router);
app.mount("#app");