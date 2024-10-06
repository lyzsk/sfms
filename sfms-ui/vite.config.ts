import { defineConfig, loadEnv } from "vite";
import { fileURLToPath, URL } from "node:url";
import vue from "@vitejs/plugin-vue";
import path from "path";
import { createSvgIconsPlugin } from "vite-plugin-svg-icons";
import createAutoImport from "unplugin-auto-import/vite";

// https://vitejs.dev/config/
export default defineConfig(({ mode, command }) => {
    const env = loadEnv(mode, process.cwd());
    const { VITE_APP_ENV } = env;

    return {
        base: VITE_APP_ENV === "production" ? "/" : "/",
        plugins: [
            vue(),
            createSvgIconsPlugin({
                iconDirs: [path.resolve(process.cwd(), "src/assets/icons")],
                symbolId: "icon-[dir]-[name]",
            }),
            createAutoImport({
                imports: ["vue", "vue-router", "pinia"],
                dts: false,
            }),
        ],
        server: {
            port: 8889,
            host: true,
            open: true,
            proxy: {
                // https://cn.vitejs.dev/config/#server-proxy
                "/dev-api": {
                    target: "http://localhost:8080",
                    changeOrigin: true,
                    rewrite: (p) => p.replace(/^\/dev-api/, ""),
                },
            },
        },
        resolve: {
            alias: {
                "@": fileURLToPath(new URL("./src", import.meta.url)),
            },
        },
        css: {
            preprocessorOptions: {
                scss: {
                    api: "modern",
                },
            },
        },
    };
});
