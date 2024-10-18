<template>
    <div class="login-bg">
        <vue-particles id="tsparticles" :options="options" />
    </div>
</template>

<script setup lang="ts">
const options = ref({
    background: {
        color: {
            value: "#000000",
        },
        // color: {
        //     value: "#1f1f1f",
        // },
    },
    fpsLimit: 120,
    interactivity: {
        events: {
            onClick: {
                enable: true,
                mode: "push",
            },
            onHover: {
                enable: true,
                mode: "repulse",
            },
        },
        modes: {
            bubble: {
                distance: 400,
                duration: 2,
                opacity: 0.8,
                size: 40,
            },
            push: {
                quantity: 4,
            },
            repulse: {
                distance: 200,
                duration: 0.4,
            },
        },
    },
    particles: {
        color: {
            value: "#e03939",
        },
        links: {
            color: "#dd1f0b",
            distance: 150,
            enable: true,
            opacity: 0.5,
            width: 1,
        },
        move: {
            direction: "none",
            enable: true,
            outModes: "bounce",
            random: false,
            speed: 6,
            straight: false,
        },
        number: {
            density: {
                enable: true,
            },
            value: 80,
        },
        opacity: {
            value: 0.5,
        },
        shape: {
            type: "circle",
        },
        size: {
            value: { min: 1, max: 5 },
        },
    },
    detectRetina: true,
});
</script>

<style scoped lang="scss">
.login-bg {
    width: 100%;
    height: 100%;
    position: fixed;
    overflow: hidden;
    z-index: -1;
}
</style>
