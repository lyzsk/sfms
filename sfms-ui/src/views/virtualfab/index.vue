<template>
    <div class="main">
        <Layout />
    </div>
</template>

<script setup lang="ts">
import Layout from "@/views/virtualfab/layout/index.vue";

(function (doc, win) {
    const fn = () => {
        const docEl = doc.documentElement,
            clientWidth = docEl.clientWidth;
        if (!clientWidth) return;
        docEl.style.fontSize = 100 * (clientWidth / 1920) + "px";
    };
    if (!doc.addEventListener) return;
    win.addEventListener("resize", fn);
    doc.addEventListener("DOMContentLoaded", fn);
})(document, window);
</script>

<style scoped lang="scss">
body,
html {
    background-color: #000;
}
body {
    overflow: hidden;
}
#app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    /* margin-top: 10px; */
    /* box-sizing: border-box; */
    /* width: 100%; */
}

.main {
    /* width: 100vw; */
    /* height: 100%; */
    /* background-image: linear-gradient(90deg, rgba(1, 53, 80, 0.3490196078) 1px, transparent 1px), linear-gradient(0deg, rgba(1, 53, 80, 0.3490196078) 1px, transparent 1px);
  background-size: 30px 30px, 30px 30px; */
    /* min-width: 850px; */
    /* box-sizing: border-box; */
}
</style>
